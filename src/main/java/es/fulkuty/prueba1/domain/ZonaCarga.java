package es.fulkuty.prueba1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ZonaCarga.
 */
@Entity
@Table(name = "zona_carga")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ZonaCarga implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "zona", nullable = false)
    private String zona;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZona() {
        return zona;
    }

    public ZonaCarga zona(String zona) {
        this.zona = zona;
        return this;
    }

    public void setZona(String zona) {
        this.zona = zona;
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
        ZonaCarga zonaCarga = (ZonaCarga) o;
        if (zonaCarga.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zonaCarga.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZonaCarga{" +
            "id=" + getId() +
            ", zona='" + getZona() + "'" +
            "}";
    }
}
