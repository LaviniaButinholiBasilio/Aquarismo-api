package com.aquarismo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reproducoes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reproducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mae_id", nullable = false)
    private Peixe mae;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pai_id", nullable = false)
    private Peixe pai;

    @Column(nullable = false)
    private LocalDate dataReproducao;

    @Column
    private LocalDate dataEclosao;

    @Column
    private Integer quantidadeOvos;

    @Column
    private Integer quantidadeFilhotes;

    @Column
    private Integer filhotesVivos;

    @Column(length = 500)
    private String observacoes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
