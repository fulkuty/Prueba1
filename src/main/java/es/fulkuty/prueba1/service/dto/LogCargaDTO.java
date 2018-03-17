package es.fulkuty.prueba1.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import es.fulkuty.prueba1.domain.enumeration.Estado;

/**
 * A DTO for the LogCarga entity.
 */
public class LogCargaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant time;

    private Estado estado;

    @NotNull
    private Instant programado;

    private Instant inicio;

    private Instant fin;

    @NotNull
    private String comentario;

    private Long zonaId;

    private Long procesoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Instant getProgramado() {
        return programado;
    }

    public void setProgramado(Instant programado) {
        this.programado = programado;
    }

    public Instant getInicio() {
        return inicio;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getFin() {
        return fin;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long zonaCargaId) {
        this.zonaId = zonaCargaId;
    }

    public Long getProcesoId() {
        return procesoId;
    }

    public void setProcesoId(Long procesoCargaId) {
        this.procesoId = procesoCargaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogCargaDTO logCargaDTO = (LogCargaDTO) o;
        if(logCargaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logCargaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogCargaDTO{" +
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
