package br.com.rogrs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.rogrs.domain.enumeration.TipoRegistro;

import br.com.rogrs.domain.enumeration.TipoOperacao;

/**
 * A LinhasArquivos.
 */
@Entity
@Table(name = "linhas_arquivos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "linhasarquivos")
public class LinhasArquivos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nsr")
    private String nsr;

    @Column(name = "tipo")
    private String tipo;

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

    @Column(name = "linha")
    private String linha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_registro")
    private TipoRegistro tipoRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao")
    private TipoOperacao tipoOperacao;

    @ManyToOne
    @JsonIgnoreProperties("linhas")
    private Arquivos arquivos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getTipo() {
        return tipo;
    }

    public LinhasArquivos tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinhasArquivos)) {
            return false;
        }
        return id != null && id.equals(((LinhasArquivos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LinhasArquivos{" +
            "id=" + getId() +
            ", nsr='" + getNsr() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", dataPonto='" + getDataPonto() + "'" +
            ", horaPonto='" + getHoraPonto() + "'" +
            ", dataAjustada='" + getDataAjustada() + "'" +
            ", horaAjustada='" + getHoraAjustada() + "'" +
            ", pis='" + getPis() + "'" +
            ", nomeEmpregado='" + getNomeEmpregado() + "'" +
            ", linha='" + getLinha() + "'" +
            ", tipoRegistro='" + getTipoRegistro() + "'" +
            ", tipoOperacao='" + getTipoOperacao() + "'" +
            "}";
    }
}
