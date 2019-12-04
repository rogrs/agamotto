package br.com.rogrs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Departamentos.
 */
@Entity
@Table(name = "departamentos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "departamentos")
public class Departamentos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "departamentos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Equipes> equipes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("departamentos")
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
        if (!(o instanceof Departamentos)) {
            return false;
        }
        return id != null && id.equals(((Departamentos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Departamentos{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
