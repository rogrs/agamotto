package br.com.rogrs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import br.com.rogrs.domain.enumeration.TipoBoolean;

/**
 * A UnidadeNegocios.
 */
@Entity
@Table(name = "unidade_negocios")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "unidadenegocios")
public class UnidadeNegocios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @NotNull
    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "empregadora", nullable = false)
    private TipoBoolean empregadora;

    @OneToMany(mappedBy = "unidadeNegocios")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Feriados> feriados = new HashSet<>();

    @OneToMany(mappedBy = "unidadeNegocios")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Departamentos> departamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public UnidadeNegocios razaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public UnidadeNegocios nomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
        return this;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public UnidadeNegocios cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public UnidadeNegocios inscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
        return this;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public TipoBoolean getEmpregadora() {
        return empregadora;
    }

    public UnidadeNegocios empregadora(TipoBoolean empregadora) {
        this.empregadora = empregadora;
        return this;
    }

    public void setEmpregadora(TipoBoolean empregadora) {
        this.empregadora = empregadora;
    }

    public Set<Feriados> getFeriados() {
        return feriados;
    }

    public UnidadeNegocios feriados(Set<Feriados> feriados) {
        this.feriados = feriados;
        return this;
    }

    public UnidadeNegocios addFeriados(Feriados feriados) {
        this.feriados.add(feriados);
        feriados.setUnidadeNegocios(this);
        return this;
    }

    public UnidadeNegocios removeFeriados(Feriados feriados) {
        this.feriados.remove(feriados);
        feriados.setUnidadeNegocios(null);
        return this;
    }

    public void setFeriados(Set<Feriados> feriados) {
        this.feriados = feriados;
    }

    public Set<Departamentos> getDepartamentos() {
        return departamentos;
    }

    public UnidadeNegocios departamentos(Set<Departamentos> departamentos) {
        this.departamentos = departamentos;
        return this;
    }

    public UnidadeNegocios addDepartamentos(Departamentos departamentos) {
        this.departamentos.add(departamentos);
        departamentos.setUnidadeNegocios(this);
        return this;
    }

    public UnidadeNegocios removeDepartamentos(Departamentos departamentos) {
        this.departamentos.remove(departamentos);
        departamentos.setUnidadeNegocios(null);
        return this;
    }

    public void setDepartamentos(Set<Departamentos> departamentos) {
        this.departamentos = departamentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadeNegocios)) {
            return false;
        }
        return id != null && id.equals(((UnidadeNegocios) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnidadeNegocios{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeEmpresa='" + getNomeEmpresa() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", inscricaoEstadual='" + getInscricaoEstadual() + "'" +
            ", empregadora='" + getEmpregadora() + "'" +
            "}";
    }
}
