package br.com.rogrs.agamotto.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import br.com.rogrs.agamotto.domain.enumeration.TipoBoolean;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.rogrs.framework.domain.AbstractEntity;

@Entity
@Table(name = "unidade_negocios",uniqueConstraints = { @UniqueConstraint(columnNames = "cnpj") })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnidadeNegocios extends AbstractEntity<Long> {

    @Column(name = "razao_social", length = 150, nullable = false)
    private String razaoSocial;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(name = "cnpj",length = 14,nullable = false)
    private String cnpj;
    
    @Column(name = "cei")
    private String cei;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

   
    @Enumerated(EnumType.STRING)
    @Column(name = "empregadora")
    private TipoBoolean empregadora;

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

   
   
    public String getCei() {
		return cei;
	}

	public void setCei(String cei) {
		this.cei = cei;
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
        UnidadeNegocios unidadeNegocios = (UnidadeNegocios) o;
        if (unidadeNegocios.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unidadeNegocios.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "UnidadeNegocios [razaoSocial="
	            + razaoSocial 
				+ ", nomeEmpresa=" + nomeEmpresa 
				+ ", cnpj=" + cnpj
				+ ", cei=" + cei 
				+ ", inscricaoEstadual=" + inscricaoEstadual
				+ ", empregadora=" + empregadora
				+ ", getCriado()=" + getCriado()
				+ ", getModificado()=" + getModificado() + "]";
	}

 
}
