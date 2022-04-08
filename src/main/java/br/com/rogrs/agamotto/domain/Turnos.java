package br.com.rogrs.agamotto.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.rogrs.framework.domain.AbstractEntity;

@Entity
@Table(name = "turnos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Turnos extends AbstractEntity<Long> {


    @Column(name = "descricao")
    private String descricao;

    @Column(name = "intervalo_flexivel", nullable = false)
    private Boolean intervaloFlexivel = false;


    public String getDescricao() {
        return descricao;
    }

    public Turnos descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIntervaloFlexivel() {
        return intervaloFlexivel;
    }

    public Turnos intervaloFlexivel(Boolean intervaloFlexivel) {
        this.intervaloFlexivel = intervaloFlexivel;
        return this;
    }

    public void setIntervaloFlexivel(Boolean intervaloFlexivel) {
        this.intervaloFlexivel = intervaloFlexivel;
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
        Turnos turnos = (Turnos) o;
        if (turnos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turnos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Turnos{" +
            "id=" + getId() +
            ", descricaoTurno='" + getDescricao() + "'" +
            ", intervaloFlexivel='" + getIntervaloFlexivel() + "'" +
            "}";
    }
}
