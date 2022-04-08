package br.com.rogrs.agamotto.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import br.com.rogrs.agamotto.domain.enumeration.TipoSexo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.rogrs.framework.domain.AbstractEntity;

/**
 * A Colaboradores.
 */
@Entity
@Table(name = "colaboradores" , uniqueConstraints = { @UniqueConstraint(columnNames = "pis") })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Colaboradores extends AbstractEntity<Long> {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "matricula", nullable = false)
    private String matricula;

    @Column(name = "pis", nullable = false)
    private String pis;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private TipoSexo sexo;

    @Column(name = "admissao")
    private LocalDate admissao;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "ci")
    private String ci;

    @Column(name = "demissao")
    private LocalDate demissao;
    
    @Column(name = "ativo")
    private boolean ativo;


    @OneToMany(mappedBy = "colaborador")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ControlePonto> controles = new HashSet<>();
    
    public Colaboradores() {
    	
    }
    
    public Colaboradores(String pis) {
		this.setNome("NOME NAO IDENTIFICADO PARA O PIS "+pis);
		this.setPis(pis);
		this.setMatricula(pis);
	}

    public Colaboradores(String nomeEmpregado, String pis, String nsr) {
		this.setNome(nomeEmpregado);
		this.setPis(pis);
		this.setMatricula(nsr);
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

    public Set<ControlePonto> getControles() {
        return controles;
    }

    public Colaboradores controles(Set<ControlePonto> controlePontos) {
        this.controles = controlePontos;
        return this;
    }

    public Colaboradores addControle(ControlePonto controlePonto) {
        this.controles.add(controlePonto);
        controlePonto.setColaborador(this);
        return this;
    }

    public Colaboradores removeControle(ControlePonto controlePonto) {
        this.controles.remove(controlePonto);
        controlePonto.setColaborador(null);
        return this;
    }

    public void setControles(Set<ControlePonto> controlePontos) {
        this.controles = controlePontos;
    }
    
    public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
        Colaboradores colaboradores = (Colaboradores) o;
        if (colaboradores.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), colaboradores.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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
            "}";
    }

}
