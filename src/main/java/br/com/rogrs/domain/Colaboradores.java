package br.com.rogrs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.rogrs.domain.enumeration.TipoSexo;

/**
 * A Colaboradores.
 */
@Entity
@Table(name = "colaboradores")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "colaboradores")
public class Colaboradores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "matricula", nullable = false)
    private String matricula;

    @NotNull
    @Column(name = "pis", nullable = false)
    private String pis;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private TipoSexo sexo;

    @NotNull
    @Column(name = "admissao", nullable = false)
    private LocalDate admissao;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "ci")
    private String ci;

    @Column(name = "demissao")
    private LocalDate demissao;

    @Column(name = "atualizado")
    private LocalDate atualizado;

    @Column(name = "criacao")
    private LocalDate criacao;

    @OneToMany(mappedBy = "colaborador")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cargos> cargos = new HashSet<>();

    @OneToMany(mappedBy = "colaborador")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ControlePonto> controlePontos = new HashSet<>();

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

    public Colaboradores nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public Colaboradores matricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getPis() {
        return pis;
    }

    public Colaboradores pis(String pis) {
        this.pis = pis;
        return this;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public Colaboradores sexo(TipoSexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getAdmissao() {
        return admissao;
    }

    public Colaboradores admissao(LocalDate admissao) {
        this.admissao = admissao;
        return this;
    }

    public void setAdmissao(LocalDate admissao) {
        this.admissao = admissao;
    }

    public String getCpf() {
        return cpf;
    }

    public Colaboradores cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCi() {
        return ci;
    }

    public Colaboradores ci(String ci) {
        this.ci = ci;
        return this;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public LocalDate getDemissao() {
        return demissao;
    }

    public Colaboradores demissao(LocalDate demissao) {
        this.demissao = demissao;
        return this;
    }

    public void setDemissao(LocalDate demissao) {
        this.demissao = demissao;
    }

    public LocalDate getAtualizado() {
        return atualizado;
    }

    public Colaboradores atualizado(LocalDate atualizado) {
        this.atualizado = atualizado;
        return this;
    }

    public void setAtualizado(LocalDate atualizado) {
        this.atualizado = atualizado;
    }

    public LocalDate getCriacao() {
        return criacao;
    }

    public Colaboradores criacao(LocalDate criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(LocalDate criacao) {
        this.criacao = criacao;
    }

    public Set<Cargos> getCargos() {
        return cargos;
    }

    public Colaboradores cargos(Set<Cargos> cargos) {
        this.cargos = cargos;
        return this;
    }

    public Colaboradores addCargo(Cargos cargos) {
        this.cargos.add(cargos);
        cargos.setColaborador(this);
        return this;
    }

    public Colaboradores removeCargo(Cargos cargos) {
        this.cargos.remove(cargos);
        cargos.setColaborador(null);
        return this;
    }

    public void setCargos(Set<Cargos> cargos) {
        this.cargos = cargos;
    }

    public Set<ControlePonto> getControlePontos() {
        return controlePontos;
    }

    public Colaboradores controlePontos(Set<ControlePonto> controlePontos) {
        this.controlePontos = controlePontos;
        return this;
    }

    public Colaboradores addControlePonto(ControlePonto controlePonto) {
        this.controlePontos.add(controlePonto);
        controlePonto.setColaborador(this);
        return this;
    }

    public Colaboradores removeControlePonto(ControlePonto controlePonto) {
        this.controlePontos.remove(controlePonto);
        controlePonto.setColaborador(null);
        return this;
    }

    public void setControlePontos(Set<ControlePonto> controlePontos) {
        this.controlePontos = controlePontos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colaboradores)) {
            return false;
        }
        return id != null && id.equals(((Colaboradores) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Colaboradores{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", matricula='" + getMatricula() + "'" +
            ", pis='" + getPis() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", admissao='" + getAdmissao() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", ci='" + getCi() + "'" +
            ", demissao='" + getDemissao() + "'" +
            ", atualizado='" + getAtualizado() + "'" +
            ", criacao='" + getCriacao() + "'" +
            "}";
    }
}
