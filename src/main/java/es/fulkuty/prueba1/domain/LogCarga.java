package es.fulkuty.prueba1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import es.fulkuty.prueba1.domain.enumeration.Estado;

/**
 * A LogCarga.
 */
@Entity
@Table(name = "log_carga")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LogCarga implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_time", nullable = false)
    private Instant time;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @NotNull
    @Column(name = "programado", nullable = false)
    private Instant programado;

    @Column(name = "inicio")
    private Instant inicio;

    @Column(name = "fin")
    private Instant fin;

    @NotNull
    @Column(name = "comentario", nullable = false)
    private String comentario;

    @OneToOne
    @JoinColumn(unique = true)
    private ZonaCarga zona;

    @ManyToOne
    private ProcesoCarga proceso;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public LogCarga time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Estado getEstado() {
        return estado;
    }

    public LogCarga estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Instant getProgramado() {
        return programado;
    }

    public LogCarga programado(Instant programado) {
        this.programado = programado;
        return this;
    }

    public void setProgramado(Instant programado) {
        this.programado = programado;
    }

    public Instant getInicio() {
        return inicio;
    }

    public LogCarga inicio(Instant inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFin() {
        return fin;
    }

    public LogCarga fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public String getComentario() {
        return comentario;
    }

    public LogCarga comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ZonaCarga getZona() {
        return zona;
    }

    public LogCarga zona(ZonaCarga zonaCarga) {
        this.zona = zonaCarga;
        return this;
    }

    public void setZona(ZonaCarga zonaCarga) {
        this.zona = zonaCarga;
    }

    public ProcesoCarga getProceso() {
        return proceso;
    }

    public LogCarga proceso(ProcesoCarga procesoCarga) {
        this.proceso = procesoCarga;
        return this;
    }

    public void setProceso(ProcesoCarga procesoCarga) {
        this.proceso = procesoCarga;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LogCarga logCarga = (LogCarga) o;
        if (logCarga.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logCarga.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogCarga{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", estado='" + getEstado() + "'" +
            ", programado='" + getProgramado() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
