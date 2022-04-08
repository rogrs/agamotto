package br.com.rogrs.agamotto.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.rogrs.agamotto.domain.enumeration.StatusSistema;
import br.com.rogrs.agamotto.domain.enumeration.TipoMarcacao;
import br.com.rogrs.agamotto.domain.enumeration.TipoOperacao;
import br.com.rogrs.agamotto.domain.enumeration.TipoRegistro;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.rogrs.framework.domain.AbstractEntity;

@Entity
@Table(name = "linhas_arquivos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LinhasArquivos extends AbstractEntity<Long> {

	@Column(name = "nsr")
	private String nsr;

	@Column(name = "data_ponto")
	private String dataPonto;

	@Column(name = "hora_ponto")
	private String horaPonto;

	@Column(name = "data_ajustada")
	private String dataAjustada;

	@Column(name = "hora_ajustada")
	private String horaAjustada;

	@Column(name = "pis")
	private String pis;

	@Column(name = "nome_empregado")
	private String nomeEmpregado;

	@Column(name = "linha", length = 4000)
	private String linha;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_registro")
	private TipoRegistro tipoRegistro;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_operacao")
	private TipoOperacao tipoOperacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_marcacao")
	private TipoMarcacao tipoMarcacao;

	@ManyToOne
	@JsonIgnoreProperties("linhas")
	private Arquivos arquivos;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusSistema status;
	
	 @ManyToOne
	 @JsonIgnoreProperties("controles")
	 private Colaboradores colaborador;

	public String getNsr() {
		return nsr;
	}

	public LinhasArquivos nsr(String nsr) {
		this.nsr = nsr;
		return this;
	}

	public void setNsr(String nsr) {
		this.nsr = nsr;
	}

	public String getDataPonto() {
		return dataPonto;
	}

	public LinhasArquivos dataPonto(String dataPonto) {
		this.dataPonto = dataPonto;
		return this;
	}

	public void setDataPonto(String dataPonto) {
		this.dataPonto = dataPonto;
	}

	public String getHoraPonto() {
		return horaPonto;
	}

	public LinhasArquivos horaPonto(String horaPonto) {
		this.horaPonto = horaPonto;
		return this;
	}

	public void setHoraPonto(String horaPonto) {
		this.horaPonto = horaPonto;
	}

	public String getDataAjustada() {
		return dataAjustada;
	}

	public LinhasArquivos dataAjustada(String dataAjustada) {
		this.dataAjustada = dataAjustada;
		return this;
	}

	public void setDataAjustada(String dataAjustada) {
		this.dataAjustada = dataAjustada;
	}

	public String getHoraAjustada() {
		return horaAjustada;
	}

	public LinhasArquivos horaAjustada(String horaAjustada) {
		this.horaAjustada = horaAjustada;
		return this;
	}

	public void setHoraAjustada(String horaAjustada) {
		this.horaAjustada = horaAjustada;
	}

	public String getPis() {
		return pis;
	}

	public LinhasArquivos pis(String pis) {
		this.pis = pis;
		return this;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public LinhasArquivos nomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
		return this;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getLinha() {
		return linha;
	}

	public LinhasArquivos linha(String linha) {
		this.linha = linha;
		return this;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public LinhasArquivos tipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
		return this;
	}

	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public LinhasArquivos tipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
		return this;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Arquivos getArquivos() {
		return arquivos;
	}

	public LinhasArquivos arquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
		return this;
	}

	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	}

	public StatusSistema getStatus() {
		return status;
	}

	public void setStatus(StatusSistema status) {
		this.status = status;
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
		LinhasArquivos linhasArquivos = (LinhasArquivos) o;
		if (linhasArquivos.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), linhasArquivos.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "LinhasArquivos{" + "id=" + getId() + ", nsr='" + getNsr() + "'" + ", dataPonto='" + getDataPonto() + "'"
				+ ", horaPonto='" + getHoraPonto() + "'" + ", dataAjustada='" + getDataAjustada() + "'"
				+ ", horaAjustada='" + getHoraAjustada() + "'" + ", pis='" + getPis() + "'" + ", nomeEmpregado='"
				+ getNomeEmpregado() + "'" + ", linha='" + getLinha() + "'" + ", tipoRegistro='" + getTipoRegistro()
				+ "'" + ", tipoOperacao='" + getTipoOperacao() + "'" + "}";
	}

	public LinhasArquivos() {
		setStatus(StatusSistema.CRIADO);
	}

	public TipoMarcacao getTipoMarcacao() {
		return tipoMarcacao;
	}

	public void setTipoMarcacao(TipoMarcacao tipoMarcacao) {
		this.tipoMarcacao = tipoMarcacao;
	}

	public Colaboradores getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaboradores colaborador) {
		this.colaborador = colaborador;
	}
}
