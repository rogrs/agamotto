package br.com.rogrs.agamotto.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.rogrs.framework.domain.AbstractEntity;

/**
 * A Feriados.
 */
@Entity
@Table(name = "feriados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feriados extends AbstractEntity<Long> {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "fixo_ou_muda_todo_ano", nullable = false)
    private Boolean fixoOuMudaTodoAno;

    @Column(name = "data_feriado", nullable = false)
    private LocalDate dataFeriado;

    @ManyToOne
    @JsonIgnoreProperties("feriados")
    private UnidadeNegocios unidadeNegocios;

    public String getNome() {
        return nome;
    }

    public Feriados nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isFixoOuMudaTodoAno() {
        return fixoOuMudaTodoAno;
    }

    public Feriados fixoOuMudaTodoAno(Boolean fixoOuMudaTodoAno) {
        this.fixoOuMudaTodoAno = fixoOuMudaTodoAno;
        return this;
    }

    public void setFixoOuMudaTodoAno(Boolean fixoOuMudaTodoAno) {
        this.fixoOuMudaTodoAno = fixoOuMudaTodoAno;
    }

    public LocalDate getDataFeriado() {
        return dataFeriado;
    }

    public Feriados dataFeriado(LocalDate dataFeriado) {
        this.dataFeriado = dataFeriado;
        return this;
    }

    public void setDataFeriado(LocalDate dataFeriado) {
        this.dataFeriado = dataFeriado;
    }

    public UnidadeNegocios getUnidadeNegocios() {
        return unidadeNegocios;
    }

    public Feriados unidadeNegocios(UnidadeNegocios unidadeNegocios) {
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
        Feriados feriados = (Feriados) o;
        if (feriados.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feriados.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Feriados{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", fixoOuMudaTodoAno='" + isFixoOuMudaTodoAno() + "'" +
            ", dataFeriado='" + getDataFeriado() + "'" +
            "}";
    }
}
