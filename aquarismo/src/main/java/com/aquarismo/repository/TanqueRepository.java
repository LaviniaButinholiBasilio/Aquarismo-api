package com.aquarismo.repository;

import com.aquarismo.model.StatusTanque;
import com.aquarismo.model.Tanque;
import com.aquarismo.model.TipoAgua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TanqueRepository extends JpaRepository<Tanque, Long> {
    List<Tanque> findByStatus(StatusTanque status);
    List<Tanque> findByTipoAgua(TipoAgua tipoAgua);
    boolean existsByNome(String nome);
}
