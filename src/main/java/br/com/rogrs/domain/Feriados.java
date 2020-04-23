package br.com.rogrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Feriados.
 */
@Entity
@Table(name = "feriados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "feriados")
public class Feriados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "fixo_ou_muda_todo_ano", nullable = false)
    private Boolean fixoOuMudaTodoAno;

    @NotNull
    @Column(name = "data_feriado", nullable = false)
    private LocalDate dataFeriado;

    @ManyToOne
    @JsonIgnoreProperties("feriados")
    private UnidadeNegocios unidadeNegocios;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        if (!(o instanceof Feriados)) {
            return false;
        }
        return id != null && id.equals(((Feriados) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
