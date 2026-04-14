package com.aquarismo.service;

import com.aquarismo.dto.request.PeixeRequest;
import com.aquarismo.dto.response.PeixeResponse;
import com.aquarismo.exception.RecursoNaoEncontradoException;
import com.aquarismo.exception.RegraDeNegocioException;
import com.aquarismo.model.Peixe;
import com.aquarismo.model.SexoPeixe;
import com.aquarismo.model.StatusPeixe;
import com.aquarismo.model.Tanque;
import com.aquarismo.repository.PeixeRepository;
import com.aquarismo.repository.TanqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeixeService {

    private final PeixeRepository peixeRepository;
    private final TanqueRepository tanqueRepository;

    @Transactional(readOnly = true)
    public List<PeixeResponse> listarTodos() {
        return peixeRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PeixeResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    @Transactional(readOnly = true)
    public List<PeixeResponse> listarPorTanque(Long tanqueId) {
        return peixeRepository.findAtivosNoTanque(tanqueId).stream()
                .map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PeixeResponse> buscar(String termo) {
        return peixeRepository.searchByNomeOuCientifico(termo).stream()
                .map(this::toResponse).toList();
    }

    @Transactional
    public PeixeResponse criar(PeixeRequest request) {
        Tanque tanque = tanqueRepository.findById(request.getTanqueId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tanque", request.getTanqueId()));

        Peixe mae = request.getMaeId() != null ? encontrarOuLancar(request.getMaeId()) : null;
        Peixe pai = request.getPaiId() != null ? encontrarOuLancar(request.getPaiId()) : null;

        Peixe peixe = Peixe.builder()
                .nome(request.getNome())
                .nomeCientifico(request.getNomeCientifico())
                .especie(request.getEspecie())
                .sexo(request.getSexo() != null ? request.getSexo() : SexoPeixe.INDEFINIDO)
                .dataAquisicao(request.getDataAquisicao())
                .dataNascimento(request.getDataNascimento())
                .precoAquisicao(request.getPrecoAquisicao())
                .observacoes(request.getObservacoes())
                .status(request.getStatus() != null ? request.getStatus() : StatusPeixe.ATIVO)
                .tanque(tanque)
                .mae(mae)
                .pai(pai)
                .build();

        return toResponse(peixeRepository.save(peixe));
    }

    @Transactional
    public PeixeResponse atualizar(Long id, PeixeRequest request) {
        Peixe peixe = encontrarOuLancar(id);
        Tanque tanque = tanqueRepository.findById(request.getTanqueId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tanque", request.getTanqueId()));

        peixe.setNome(request.getNome());
        peixe.setNomeCientifico(request.getNomeCientifico());
        peixe.setEspecie(request.getEspecie());
        if (request.getSexo() != null) peixe.setSexo(request.getSexo());
        peixe.setDataAquisicao(request.getDataAquisicao());
        peixe.setDataNascimento(request.getDataNascimento());
        peixe.setPrecoAquisicao(request.getPrecoAquisicao());
        peixe.setObservacoes(request.getObservacoes());
        if (request.getStatus() != null) peixe.setStatus(request.getStatus());
        peixe.setTanque(tanque);
        if (request.getMaeId() != null) peixe.setMae(encontrarOuLancar(request.getMaeId()));
        if (request.getPaiId() != null) peixe.setPai(encontrarOuLancar(request.getPaiId()));

        return toResponse(peixeRepository.save(peixe));
    }

    @Transactional
    public void deletar(Long id) {
        Peixe peixe = encontrarOuLancar(id);
        if (peixe.getStatus() == StatusPeixe.VENDIDO) {
            throw new RegraDeNegocioException("Não é possível excluir um peixe já vendido. Use a inativação.");
        }
        peixeRepository.delete(peixe);
    }

    private Peixe encontrarOuLancar(Long id) {
        return peixeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peixe", id));
    }

    private PeixeResponse toResponse(Peixe p) {
        return PeixeResponse.builder()
                .id(p.getId())
                .nome(p.getNome())
                .nomeCientifico(p.getNomeCientifico())
                .especie(p.getEspecie())
                .sexo(p.getSexo())
                .dataAquisicao(p.getDataAquisicao())
                .dataNascimento(p.getDataNascimento())
                .precoAquisicao(p.getPrecoAquisicao())
                .observacoes(p.getObservacoes())
                .status(p.getStatus())
                .tanqueId(p.getTanque() != null ? p.getTanque().getId() : null)
                .tanqueNome(p.getTanque() != null ? p.getTanque().getNome() : null)
                .maeId(p.getMae() != null ? p.getMae().getId() : null)
                .maeNome(p.getMae() != null ? p.getMae().getNome() : null)
                .paiId(p.getPai() != null ? p.getPai().getId() : null)
                .paiNome(p.getPai() != null ? p.getPai().getNome() : null)
                .criadoEm(p.getCriadoEm())
                .atualizadoEm(p.getAtualizadoEm())
                .build();
    }
}
