package com.aquarismo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peixes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Peixe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 150)
    private String nomeCientifico;

    @Column(nullable = false, length = 100)
    private String especie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private SexoPeixe sexo = SexoPeixe.INDEFINIDO;

    @Column(nullable = false)
    private LocalDate dataAquisicao;

    @Column
    private LocalDate dataNascimento;

    @Column(precision = 10, scale = 2)
    private BigDecimal precoAquisicao;

    @Column(length = 500)
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusPeixe status = StatusPeixe.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tanque_id", nullable = false)
    private Tanque tanque;

    /** Referência à mãe - controle de linhagem */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mae_id")
    private Peixe mae;

    /** Referência ao pai - controle de linhagem */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pai_id")
    private Peixe pai;

    @OneToMany(mappedBy = "peixe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Venda> vendas = new ArrayList<>();

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
