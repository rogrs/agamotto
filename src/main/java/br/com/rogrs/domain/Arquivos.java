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

import br.com.rogrs.domain.enumeration.StatusArquivo;

/**
 * A Arquivos.
 */
@Entity
@Table(name = "arquivos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "arquivos")
public class Arquivos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "caminho", nullable = false)
    private String caminho;

    @NotNull
    @Column(name = "md_5", nullable = false)
    private String md5;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusArquivo status;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "total_linhas")
    private Integer totalLinhas;

    @Column(name = "criacao")
    private LocalDate criacao;

    @Column(name = "processamento")
    private LocalDate processamento;

    @OneToMany(mappedBy = "arquivos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LinhasArquivos> linhas = new HashSet<>();

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

    public StatusArquivo getStatus() {
        return status;
    }

    public Arquivos status(StatusArquivo status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusArquivo status) {
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

    public LocalDate getCriacao() {
        return criacao;
    }

    public Arquivos criacao(LocalDate criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(LocalDate criacao) {
        this.criacao = criacao;
    }

    public LocalDate getProcessamento() {
        return processamento;
    }

    public Arquivos processamento(LocalDate processamento) {
        this.processamento = processamento;
        return this;
    }

    public void setProcessamento(LocalDate processamento) {
        this.processamento = processamento;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arquivos)) {
            return false;
        }
        return id != null && id.equals(((Arquivos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Arquivos{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", caminho='" + getCaminho() + "'" +
            ", md5='" + getMd5() + "'" +
            ", status='" + getStatus() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", totalLinhas=" + getTotalLinhas() +
            ", criacao='" + getCriacao() + "'" +
            ", processamento='" + getProcessamento() + "'" +
            "}";
    }
}
