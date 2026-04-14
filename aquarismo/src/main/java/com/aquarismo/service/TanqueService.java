package com.aquarismo.service;

import com.aquarismo.dto.request.TanqueRequest;
import com.aquarismo.dto.response.TanqueResponse;
import com.aquarismo.exception.RecursoNaoEncontradoException;
import com.aquarismo.exception.RegraDeNegocioException;
import com.aquarismo.model.StatusTanque;
import com.aquarismo.model.Tanque;
import com.aquarismo.repository.PeixeRepository;
import com.aquarismo.repository.TanqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TanqueService {

    private final TanqueRepository tanqueRepository;
    private final PeixeRepository peixeRepository;

    @Transactional(readOnly = true)
    public List<TanqueResponse> listarTodos() {
        return tanqueRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TanqueResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    @Transactional
    public TanqueResponse criar(TanqueRequest request) {
        if (tanqueRepository.existsByNome(request.getNome())) {
            throw new RegraDeNegocioException("Já existe um tanque com o nome '" + request.getNome() + "'.");
        }
        Tanque tanque = Tanque.builder()
                .nome(request.getNome())
                .capacidadeLitros(request.getCapacidadeLitros())
                .tipoAgua(request.getTipoAgua())
                .localizacao(request.getLocalizacao())
                .descricao(request.getDescricao())
                .status(request.getStatus() != null ? request.getStatus() : StatusTanque.ATIVO)
                .build();
        return toResponse(tanqueRepository.save(tanque));
    }

    @Transactional
    public TanqueResponse atualizar(Long id, TanqueRequest request) {
        Tanque tanque = encontrarOuLancar(id);
        tanque.setNome(request.getNome());
        tanque.setCapacidadeLitros(request.getCapacidadeLitros());
        tanque.setTipoAgua(request.getTipoAgua());
        tanque.setLocalizacao(request.getLocalizacao());
        tanque.setDescricao(request.getDescricao());
        if (request.getStatus() != null) tanque.setStatus(request.getStatus());
        return toResponse(tanqueRepository.save(tanque));
    }

    @Transactional
    public void deletar(Long id) {
        Tanque tanque = encontrarOuLancar(id);
        long peixesAtivos = peixeRepository.findAtivosNoTanque(id).size();
        if (peixesAtivos > 0) {
            throw new RegraDeNegocioException(
                "Não é possível excluir o tanque pois há " + peixesAtivos + " peixe(s) ativo(s) nele.");
        }
        tanqueRepository.delete(tanque);
    }

    private Tanque encontrarOuLancar(Long id) {
        return tanqueRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tanque", id));
    }

    private TanqueResponse toResponse(Tanque t) {
        int total = peixeRepository.findAtivosNoTanque(t.getId()).size();
        return TanqueResponse.builder()
                .id(t.getId())
                .nome(t.getNome())
                .capacidadeLitros(t.getCapacidadeLitros())
                .tipoAgua(t.getTipoAgua())
                .localizacao(t.getLocalizacao())
                .descricao(t.getDescricao())
                .status(t.getStatus())
                .totalPeixesAtivos(total)
                .criadoEm(t.getCriadoEm())
                .atualizadoEm(t.getAtualizadoEm())
                .build();
    }
}
