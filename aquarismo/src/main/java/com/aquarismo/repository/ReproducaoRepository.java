package com.aquarismo.repository;

import com.aquarismo.model.Reproducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReproducaoRepository extends JpaRepository<Reproducao, Long> {
    List<Reproducao> findByMaeId(Long maeId);
    List<Reproducao> findByPaiId(Long paiId);
    List<Reproducao> findByDataReproducaoBetween(LocalDate inicio, LocalDate fim);
}
