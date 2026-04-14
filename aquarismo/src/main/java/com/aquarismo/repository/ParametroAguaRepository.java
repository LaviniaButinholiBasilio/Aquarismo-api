package com.aquarismo.repository;

import com.aquarismo.model.ParametroAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParametroAguaRepository extends JpaRepository<ParametroAgua, Long> {
    List<ParametroAgua> findByTanqueIdOrderByDataHoraMedicaoDesc(Long tanqueId);

    List<ParametroAgua> findByTanqueIdAndDataHoraMedicaoBetween(
            Long tanqueId, LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT p FROM ParametroAgua p WHERE p.tanque.id = :tanqueId " +
           "ORDER BY p.dataHoraMedicao DESC LIMIT 1")
    Optional<ParametroAgua> findUltimoRegistroDoTanque(@Param("tanqueId") Long tanqueId);
}
