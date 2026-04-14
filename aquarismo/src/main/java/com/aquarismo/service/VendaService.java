package com.aquarismo.service;

import com.aquarismo.dto.request.VendaRequest;
import com.aquarismo.dto.response.VendaResponse;
import com.aquarismo.exception.RecursoNaoEncontradoException;
import com.aquarismo.exception.RegraDeNegocioException;
import com.aquarismo.model.Peixe;
import com.aquarismo.model.StatusPeixe;
import com.aquarismo.model.Venda;
import com.aquarismo.repository.PeixeRepository;
import com.aquarismo.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final PeixeRepository peixeRepository;

    @Transactional(readOnly = true)
    public List<VendaResponse> listarTodos() {
        return vendaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public VendaResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> listarPorPeixe(Long peixeId) {
        return vendaRepository.findByPeixeId(peixeId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BigDecimal totalNoPeriodo(LocalDate inicio, LocalDate fim) {
        return vendaRepository.totalVendasNoPeriodo(inicio, fim);
    }

    @Transactional
    public VendaResponse registrar(VendaRequest request) {
        Peixe peixe = peixeRepository.findById(request.getPeixeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peixe", request.getPeixeId()));

        if (peixe.getStatus() == StatusPeixe.VENDIDO) {
            throw new RegraDeNegocioException("O peixe '" + peixe.getNome() + "' já foi vendido anteriormente.");
        }
        if (peixe.getStatus() == StatusPeixe.MORTO) {
            throw new RegraDeNegocioException("Não é possível registrar venda de um peixe com status MORTO.");
        }

        Venda venda = Venda.builder()
                .peixe(peixe)
                .dataVenda(request.getDataVenda())
                .valorUnitario(request.getValorUnitario())
                .nomeComprador(request.getNomeComprador())
                .telefoneComprador(request.getTelefoneComprador())
                .emailComprador(request.getEmailComprador())
                .observacoes(request.getObservacoes())
                .build();

        // Atualiza status do peixe automaticamente
        peixe.setStatus(StatusPeixe.VENDIDO);
        peixeRepository.save(peixe);

        return toResponse(vendaRepository.save(venda));
    }

    @Transactional
    public void deletar(Long id) {
        Venda venda = encontrarOuLancar(id);
        // Reverte o status do peixe ao cancelar uma venda
        Peixe peixe = venda.getPeixe();
        if (peixe.getStatus() == StatusPeixe.VENDIDO) {
            peixe.setStatus(StatusPeixe.ATIVO);
            peixeRepository.save(peixe);
        }
        vendaRepository.delete(venda);
    }

    private Venda encontrarOuLancar(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Venda", id));
    }

    private VendaResponse toResponse(Venda v) {
        return VendaResponse.builder()
                .id(v.getId())
                .peixeId(v.getPeixe().getId())
                .peixeNome(v.getPeixe().getNome())
                .peixeEspecie(v.getPeixe().getEspecie())
                .dataVenda(v.getDataVenda())
                .valorUnitario(v.getValorUnitario())
                .nomeComprador(v.getNomeComprador())
                .telefoneComprador(v.getTelefoneComprador())
                .emailComprador(v.getEmailComprador())
                .observacoes(v.getObservacoes())
                .criadoEm(v.getCriadoEm())
                .atualizadoEm(v.getAtualizadoEm())
                .build();
    }
}
