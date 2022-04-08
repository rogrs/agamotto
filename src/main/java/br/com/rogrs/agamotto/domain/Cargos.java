package br.com.rogrs.agamotto.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.rogrs.framework.domain.AbstractEntity;

@Entity
@Table(name = "cargos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cargos extends AbstractEntity<Long> {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nome_feminino", nullable = false)
    private String nomeFeminino;

    public String getNome() {
        return nome;
    }

    public Cargos nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFeminino() {
        return nomeFeminino;
    }

    public Cargos nomeFeminino(String nomeFeminino) {
        this.nomeFeminino = nomeFeminino;
        return this;
    }

    public void setNomeFeminino(String nomeFeminino) {
        this.nomeFeminino = nomeFeminino;
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
        Cargos cargos = (Cargos) o;
        if (cargos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cargos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cargos{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeFeminino='" + getNomeFeminino() + "'" +
            "}";
    }
}
