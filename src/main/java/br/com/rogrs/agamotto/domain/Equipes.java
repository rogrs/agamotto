package br.com.rogrs.agamotto.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="equipes",uniqueConstraints={@UniqueConstraint(columnNames="nome")})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipes extends AbstractPersistable<Long> {

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne
    @JsonIgnoreProperties("equipes")
    private Departamentos departamentos;

    public String getNome() {
        return nome;
    }

    public Equipes nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Departamentos getDepartamentos() {
        return departamentos;
    }

    public Equipes departamentos(Departamentos departamentos) {
        this.departamentos = departamentos;
        return this;
    }

    public void setDepartamentos(Departamentos departamentos) {
        this.departamentos = departamentos;
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
        Equipes equipes = (Equipes) o;
        if (equipes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Equipes{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
