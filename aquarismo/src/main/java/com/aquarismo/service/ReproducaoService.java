package com.aquarismo.service;

import com.aquarismo.dto.request.ReproducaoRequest;
import com.aquarismo.dto.response.ReproducaoResponse;
import com.aquarismo.exception.RecursoNaoEncontradoException;
import com.aquarismo.exception.RegraDeNegocioException;
import com.aquarismo.model.Peixe;
import com.aquarismo.model.Reproducao;
import com.aquarismo.model.SexoPeixe;
import com.aquarismo.repository.PeixeRepository;
import com.aquarismo.repository.ReproducaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReproducaoService {

    private final ReproducaoRepository reproducaoRepository;
    private final PeixeRepository peixeRepository;

    @Transactional(readOnly = true)
    public List<ReproducaoResponse> listarTodos() {
        return reproducaoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ReproducaoResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    @Transactional(readOnly = true)
    public List<ReproducaoResponse> listarPorMae(Long maeId) {
        return reproducaoRepository.findByMaeId(maeId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ReproducaoResponse> listarPorPai(Long paiId) {
        return reproducaoRepository.findByPaiId(paiId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public ReproducaoResponse registrar(ReproducaoRequest request) {
        Peixe mae = peixeRepository.findById(request.getMaeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peixe (mãe)", request.getMaeId()));
        Peixe pai = peixeRepository.findById(request.getPaiId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peixe (pai)", request.getPaiId()));

        if (mae.getSexo() == SexoPeixe.MACHO) {
            throw new RegraDeNegocioException("O peixe informado como mãe (ID " + mae.getId() + ") está cadastrado como MACHO.");
        }
        if (pai.getSexo() == SexoPeixe.FEMEA) {
            throw new RegraDeNegocioException("O peixe informado como pai (ID " + pai.getId() + ") está cadastrado como FÊMEA.");
        }
        if (mae.getId().equals(pai.getId())) {
            throw new RegraDeNegocioException("Mãe e pai não podem ser o mesmo peixe.");
        }
        if (request.getFilhotesVivos() != null && request.getQuantidadeFilhotes() != null
                && request.getFilhotesVivos() > request.getQuantidadeFilhotes()) {
            throw new RegraDeNegocioException("Filhotes vivos não pode ser maior que o total de filhotes.");
        }

        Reproducao reproducao = Reproducao.builder()
                .mae(mae)
                .pai(pai)
                .dataReproducao(request.getDataReproducao())
                .dataEclosao(request.getDataEclosao())
                .quantidadeOvos(request.getQuantidadeOvos())
                .quantidadeFilhotes(request.getQuantidadeFilhotes())
                .filhotesVivos(request.getFilhotesVivos())
                .observacoes(request.getObservacoes())
                .build();

        return toResponse(reproducaoRepository.save(reproducao));
    }

    @Transactional
    public ReproducaoResponse atualizar(Long id, ReproducaoRequest request) {
        Reproducao reproducao = encontrarOuLancar(id);
        Peixe mae = peixeRepository.findById(request.getMaeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peixe (mãe)", request.getMaeId()));
        Peixe pai = peixeRepository.findById(request.getPaiId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peixe (pai)", request.getPaiId()));

        reproducao.setMae(mae);
        reproducao.setPai(pai);
        reproducao.setDataReproducao(request.getDataReproducao());
        reproducao.setDataEclosao(request.getDataEclosao());
        reproducao.setQuantidadeOvos(request.getQuantidadeOvos());
        reproducao.setQuantidadeFilhotes(request.getQuantidadeFilhotes());
        reproducao.setFilhotesVivos(request.getFilhotesVivos());
        reproducao.setObservacoes(request.getObservacoes());

        return toResponse(reproducaoRepository.save(reproducao));
    }

    @Transactional
    public void deletar(Long id) {
        reproducaoRepository.delete(encontrarOuLancar(id));
    }

    private Reproducao encontrarOuLancar(Long id) {
        return reproducaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reprodução", id));
    }

    private ReproducaoResponse toResponse(Reproducao r) {
        return ReproducaoResponse.builder()
                .id(r.getId())
                .maeId(r.getMae().getId())
                .maeNome(r.getMae().getNome())
                .paiId(r.getPai().getId())
                .paiNome(r.getPai().getNome())
                .dataReproducao(r.getDataReproducao())
                .dataEclosao(r.getDataEclosao())
                .quantidadeOvos(r.getQuantidadeOvos())
                .quantidadeFilhotes(r.getQuantidadeFilhotes())
                .filhotesVivos(r.getFilhotesVivos())
                .observacoes(r.getObservacoes())
                .criadoEm(r.getCriadoEm())
                .atualizadoEm(r.getAtualizadoEm())
                .build();
    }
}
