package br.com.rogrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * A ControlePonto.
 */
@Entity
@Table(name = "controle_ponto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "controleponto")
public class ControlePonto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private Duration data;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "controlePonto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ponto> pontos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("controlePontos")
    private Colaboradores colaborador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getData() {
        return data;
    }

    public ControlePonto data(Duration data) {
        this.data = data;
        return this;
    }

    public void setData(Duration data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public ControlePonto status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Ponto> getPontos() {
        return pontos;
    }

    public ControlePonto pontos(Set<Ponto> pontos) {
        this.pontos = pontos;
        return this;
    }

    public ControlePonto addPonto(Ponto ponto) {
        this.pontos.add(ponto);
        ponto.setControlePonto(this);
        return this;
    }

    public ControlePonto removePonto(Ponto ponto) {
        this.pontos.remove(ponto);
        ponto.setControlePonto(null);
        return this;
    }

    public void setPontos(Set<Ponto> pontos) {
        this.pontos = pontos;
    }

    public Colaboradores getColaborador() {
        return colaborador;
    }

    public ControlePonto colaborador(Colaboradores colaboradores) {
        this.colaborador = colaboradores;
        return this;
    }

    public void setColaborador(Colaboradores colaboradores) {
        this.colaborador = colaboradores;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControlePonto)) {
            return false;
        }
        return id != null && id.equals(((ControlePonto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ControlePonto{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
