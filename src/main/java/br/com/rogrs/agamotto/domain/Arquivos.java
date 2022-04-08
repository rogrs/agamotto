package br.com.rogrs.agamotto.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.rogrs.agamotto.domain.enumeration.StatusSistema;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.rogrs.framework.domain.AbstractEntity;


@Entity
@Table(name = "arquivos", uniqueConstraints = { @UniqueConstraint(columnNames = "nome") })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@SequenceGenerator(initialValue = 1, name = AbstractEntity.GENERATOR, sequenceName = "SQ_ARQUIVO")
@AttributeOverride(name = "id", column = @Column(name = "ARQUIVO_ID"))
public class Arquivos extends AbstractEntity<Long> {

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "caminho", nullable = false)
	private String caminho;

	@Column(name = "hashmd5", length = 100, nullable = false)
	private String md5;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusSistema status;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "total_linhas")
	private Integer totalLinhas;	

	@OneToMany(mappedBy = "arquivos")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<LinhasArquivos> linhas = new HashSet<>();

	public Arquivos() {

	}

	public Arquivos(String nomeArquivo) {
		this.nome = nomeArquivo;
		this.setStatus(StatusSistema.CRIADO);
	}

	public String getNome() {
		return nome;
	}

	public Arquivos nome(String nome) {
		this.nome = nome;
		return this;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaminho() {
		return caminho;
	}

	public Arquivos caminho(String caminho) {
		this.caminho = caminho;
		return this;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getMd5() {
		return md5;
	}

	public Arquivos md5(String md5) {
		this.md5 = md5;
		return this;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public StatusSistema getStatus() {
		return status;
	}

	public Arquivos status(StatusSistema status) {
		this.status = status;
		return this;
	}

	public void setStatus(StatusSistema status) {
		this.status = status;
	}

	public String getTipo() {
		return tipo;
	}

	public Arquivos tipo(String tipo) {
		this.tipo = tipo;
		return this;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getTotalLinhas() {
		return totalLinhas;
	}

	public Arquivos totalLinhas(Integer totalLinhas) {
		this.totalLinhas = totalLinhas;
		return this;
	}

	public void setTotalLinhas(Integer totalLinhas) {
		this.totalLinhas = totalLinhas;
	}

	public Set<LinhasArquivos> getLinhas() {
		return linhas;
	}

	public Arquivos linhas(Set<LinhasArquivos> linhasArquivos) {
		this.linhas = linhasArquivos;
		return this;
	}

	public Arquivos addLinhas(LinhasArquivos linhasArquivos) {
		this.linhas.add(linhasArquivos);
		linhasArquivos.setArquivos(this);
		return this;
	}

	public Arquivos removeLinhas(LinhasArquivos linhasArquivos) {
		this.linhas.remove(linhasArquivos);
		linhasArquivos.setArquivos(null);
		return this;
	}

	public void setLinhas(Set<LinhasArquivos> linhasArquivos) {
		this.linhas = linhasArquivos;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Arquivos arquivos = (Arquivos) o;
		if (arquivos.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), arquivos.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}


	public void setStatusCriado() {
		this.setStatus(StatusSistema.CRIADO);
	}

	public void setStatusAndamento() {
		this.setStatus(StatusSistema.ANDAMENTO);
	}

	public void setStatusProcessado() {
		this.setStatus(StatusSistema.PROCESSADO);
	}

	public void setStatusErro() {
		this.setStatus(StatusSistema.ERRO);
	}

	@Override
	public String toString() {
		return "Arquivos [getNome()=" + getNome() + ", getCaminho()=" + getCaminho() + ", getMd5()=" + getMd5()
				+ ", getStatus()=" + getStatus() + ", getTipo()=" + getTipo() + ", getTotalLinhas()=" + getTotalLinhas()
				+ ", getCriado()=" + getCriado() + ", getModificado()=" + getModificado() + "]";
	}

}
