package com.aquarismo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tanques")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tanque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Double capacidadeLitros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoAgua tipoAgua;

    @Column(length = 255)
    private String localizacao;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusTanque status = StatusTanque.ATIVO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "tanque", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Peixe> peixes = new ArrayList<>();

    @OneToMany(mappedBy = "tanque", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ParametroAgua> parametros = new ArrayList<>();

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
