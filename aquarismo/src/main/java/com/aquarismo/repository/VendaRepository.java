package com.aquarismo.repository;

import com.aquarismo.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByPeixeId(Long peixeId);
    List<Venda> findByDataVendaBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT COALESCE(SUM(v.valorUnitario), 0) FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim")
    BigDecimal totalVendasNoPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
