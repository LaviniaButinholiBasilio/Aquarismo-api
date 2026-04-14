package com.aquarismo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parametros_agua")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParametroAgua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tanque_id", nullable = false)
    private Tanque tanque;

    @Column(nullable = false)
    private LocalDateTime dataHoraMedicao;

    /** pH: escala de 0 a 14 */
    @Column(nullable = false)
    private Double ph;

    /** Temperatura em graus Celsius */
    @Column(nullable = false)
    private Double temperatura;

    /** Amônia (NH3) em mg/L */
    @Column
    private Double amonia;

    /** Nitrito (NO2) em mg/L */
    @Column
    private Double nitrito;

    /** Nitrato (NO3) em mg/L */
    @Column
    private Double nitrato;

    /** Dureza total em graus alemães (dH) */
    @Column
    private Double durezaTotal;

    /** Dureza carbonática (KH) */
    @Column
    private Double durezaCarbonato;

    /** Oxigênio dissolvido em mg/L */
    @Column
    private Double oxigenioDissolv;

    /** Salinidade em ppt — relevante para água salgada/salobra */
    @Column
    private Double salinidade;

    @Column(length = 500)
    private String observacoes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        if (dataHoraMedicao == null) dataHoraMedicao = LocalDateTime.now();
    }
}
