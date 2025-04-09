package com.WellSpace.modules.reservas.domain;

import com.WellSpace.modules.reservas.domain.ENUM.StatusEnum;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reservas")
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "salas_id", nullable = false)
    private Salas salas;

    @ManyToOne
    @JoinColumn(name = "locatario_id", nullable = false)
    private Usuario locatario;

    @ManyToOne
    @JoinColumn(name = "locador_id", nullable = false)
    private Usuario locador;

    @Column(name = "tempo_inicio", nullable = false)
    private LocalTime tempoInicio;

    @Column(name = "tempo_fim", nullable = false)
    private LocalTime tempoFim;

    @Column(name = "data_reserva", nullable = false)
    private Date dataReserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    public Reserva(LocalTime tempoFim, LocalTime tempoInicio, Date dataReserva, StatusEnum status) {
        this.tempoFim = tempoFim;
        this.tempoInicio = tempoInicio;
        this.dataReserva = dataReserva;
        this.status = status;
    }

    public static Reserva newReserva(LocalTime tempoFim, LocalTime tempoInicio, Date dataReserva, StatusEnum status){
        return new Reserva(tempoFim,tempoInicio,dataReserva,status);
    }
}
