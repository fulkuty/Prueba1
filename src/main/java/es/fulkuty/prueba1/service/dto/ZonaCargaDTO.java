package es.fulkuty.prueba1.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZonaCarga entity.
 */
public class ZonaCargaDTO implements Serializable {

    private Long id;

    @NotNull
    private String zona;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZonaCargaDTO zonaCargaDTO = (ZonaCargaDTO) o;
        if(zonaCargaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zonaCargaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZonaCargaDTO{" +
            "id=" + getId() +
            ", zona='" + getZona() + "'" +
            "}";
    }
}
