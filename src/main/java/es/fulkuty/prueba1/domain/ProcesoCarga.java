package es.fulkuty.prueba1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import es.fulkuty.prueba1.domain.enumeration.Estado;

/**
 * A ProcesoCarga.
 */
@Entity
@Table(name = "proceso_carga")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcesoCarga implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @OneToMany(mappedBy = "proceso")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LogCarga> logs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public ProcesoCarga estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Instant getProgramado() {
        return programado;
    }

    public ProcesoCarga programado(Instant programado) {
        this.programado = programado;
        return this;
    }

    public void setProgramado(Instant programado) {
        this.programado = programado;
    }

    public Instant getInicio() {
        return inicio;
    }

    public ProcesoCarga inicio(Instant inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFin() {
        return fin;
    }

    public ProcesoCarga fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public String getComentario() {
        return comentario;
    }

    public ProcesoCarga comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ZonaCarga getZona() {
        return zona;
    }

    public ProcesoCarga zona(ZonaCarga zonaCarga) {
        this.zona = zonaCarga;
        return this;
    }

    public void setZona(ZonaCarga zonaCarga) {
        this.zona = zonaCarga;
    }

    public Set<LogCarga> getLogs() {
        return logs;
    }

    public ProcesoCarga logs(Set<LogCarga> logCargas) {
        this.logs = logCargas;
        return this;
    }

    public ProcesoCarga addLog(LogCarga logCarga) {
        this.logs.add(logCarga);
        logCarga.setProceso(this);
        return this;
    }

    public ProcesoCarga removeLog(LogCarga logCarga) {
        this.logs.remove(logCarga);
        logCarga.setProceso(null);
        return this;
    }

    public void setLogs(Set<LogCarga> logCargas) {
        this.logs = logCargas;
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
        ProcesoCarga procesoCarga = (ProcesoCarga) o;
        if (procesoCarga.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procesoCarga.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcesoCarga{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", programado='" + getProgramado() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
