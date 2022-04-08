package br.com.rogrs.agamotto.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.rogrs.framework.domain.AbstractEntity;

/**
 * A Departamentos.
 */
@Entity
@Table(name = "departamentos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Departamentos extends AbstractEntity<Long> {

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "departamentos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Equipes> equipes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("departamentos")
    private UnidadeNegocios unidadeNegocios;

    public String getNome() {
        return nome;
    }

    public Departamentos nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Equipes> getEquipes() {
        return equipes;
    }

    public Departamentos equipes(Set<Equipes> equipes) {
        this.equipes = equipes;
        return this;
    }

    public Departamentos addEquipes(Equipes equipes) {
        this.equipes.add(equipes);
        equipes.setDepartamentos(this);
        return this;
    }

    public Departamentos removeEquipes(Equipes equipes) {
        this.equipes.remove(equipes);
        equipes.setDepartamentos(null);
        return this;
    }

    public void setEquipes(Set<Equipes> equipes) {
        this.equipes = equipes;
    }

    public UnidadeNegocios getUnidadeNegocios() {
        return unidadeNegocios;
    }

    public Departamentos unidadeNegocios(UnidadeNegocios unidadeNegocios) {
        this.unidadeNegocios = unidadeNegocios;
        return this;
    }

    public void setUnidadeNegocios(UnidadeNegocios unidadeNegocios) {
        this.unidadeNegocios = unidadeNegocios;
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
        Departamentos departamentos = (Departamentos) o;
        if (departamentos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), departamentos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Departamentos{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
