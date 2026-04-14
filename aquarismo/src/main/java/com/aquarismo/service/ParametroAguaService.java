package com.aquarismo.service;

import com.aquarismo.dto.request.ParametroAguaRequest;
import com.aquarismo.dto.response.ParametroAguaResponse;
import com.aquarismo.exception.RecursoNaoEncontradoException;
import com.aquarismo.exception.RegraDeNegocioException;
import com.aquarismo.model.ParametroAgua;
import com.aquarismo.model.Tanque;
import com.aquarismo.repository.ParametroAguaRepository;
import com.aquarismo.repository.TanqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParametroAguaService {

    private final ParametroAguaRepository parametroRepository;
    private final TanqueRepository tanqueRepository;

    @Transactional(readOnly = true)
    public List<ParametroAguaResponse> listarPorTanque(Long tanqueId) {
        return parametroRepository.findByTanqueIdOrderByDataHoraMedicaoDesc(tanqueId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ParametroAguaResponse buscarPorId(Long id) {
        return toResponse(encontrarOuLancar(id));
    }

    @Transactional(readOnly = true)
    public ParametroAguaResponse ultimaMedicao(Long tanqueId) {
        return parametroRepository.findUltimoRegistroDoTanque(tanqueId)
                .map(this::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhuma medição encontrada para o tanque ID " + tanqueId + "."));
    }

    @Transactional
    public ParametroAguaResponse registrar(ParametroAguaRequest request) {
        Tanque tanque = tanqueRepository.findById(request.getTanqueId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tanque", request.getTanqueId()));

        validarParametros(request);

        ParametroAgua parametro = ParametroAgua.builder()
                .tanque(tanque)
                .dataHoraMedicao(request.getDataHoraMedicao())
                .ph(request.getPh())
                .temperatura(request.getTemperatura())
                .amonia(request.getAmonia())
                .nitrito(request.getNitrito())
                .nitrato(request.getNitrato())
                .durezaTotal(request.getDurezaTotal())
                .durezaCarbonato(request.getDurezaCarbonato())
                .oxigenioDissolv(request.getOxigenioDissolv())
                .salinidade(request.getSalinidade())
                .observacoes(request.getObservacoes())
                .build();

        return toResponse(parametroRepository.save(parametro));
    }

    @Transactional
    public void deletar(Long id) {
        parametroRepository.delete(encontrarOuLancar(id));
    }

    private void validarParametros(ParametroAguaRequest r) {
        if (r.getTemperatura() < 0 || r.getTemperatura() > 50) {
            throw new RegraDeNegocioException("Temperatura fora do intervalo aceitável (0°C – 50°C).");
        }
        if (r.getAmonia() != null && r.getAmonia() < 0) {
            throw new RegraDeNegocioException("Amônia não pode ser negativa.");
        }
        if (r.getNitrito() != null && r.getNitrito() < 0) {
            throw new RegraDeNegocioException("Nitrito não pode ser negativo.");
        }
        if (r.getNitrato() != null && r.getNitrato() < 0) {
            throw new RegraDeNegocioException("Nitrato não pode ser negativo.");
        }
    }

    private ParametroAgua encontrarOuLancar(Long id) {
        return parametroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medição de parâmetro", id));
    }

    private ParametroAguaResponse toResponse(ParametroAgua p) {
        return ParametroAguaResponse.builder()
                .id(p.getId())
                .tanqueId(p.getTanque().getId())
                .tanqueNome(p.getTanque().getNome())
                .dataHoraMedicao(p.getDataHoraMedicao())
                .ph(p.getPh())
                .temperatura(p.getTemperatura())
                .amonia(p.getAmonia())
                .nitrito(p.getNitrito())
                .nitrato(p.getNitrato())
                .durezaTotal(p.getDurezaTotal())
                .durezaCarbonato(p.getDurezaCarbonato())
                .oxigenioDissolv(p.getOxigenioDissolv())
                .salinidade(p.getSalinidade())
                .observacoes(p.getObservacoes())
                .criadoEm(p.getCriadoEm())
                .build();
    }
}
