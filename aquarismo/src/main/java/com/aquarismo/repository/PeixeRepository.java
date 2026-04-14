package com.aquarismo.repository;

import com.aquarismo.model.Peixe;
import com.aquarismo.model.StatusPeixe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PeixeRepository extends JpaRepository<Peixe, Long> {
    List<Peixe> findByTanqueId(Long tanqueId);
    List<Peixe> findByStatus(StatusPeixe status);
    List<Peixe> findByEspecie(String especie);

    @Query("SELECT p FROM Peixe p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))" +
           " OR LOWER(p.nomeCientifico) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Peixe> searchByNomeOuCientifico(@Param("termo") String termo);

    @Query("SELECT p FROM Peixe p WHERE p.tanque.id = :tanqueId AND p.status = 'ATIVO'")
    List<Peixe> findAtivosNoTanque(@Param("tanqueId") Long tanqueId);
}
