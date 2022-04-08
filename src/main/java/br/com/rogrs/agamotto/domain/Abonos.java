package br.com.rogrs.agamotto.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.rogrs.agamotto.domain.enumeration.TipoBoolean;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.rogrs.framework.domain.AbstractEntity;


@Entity
@Table(name = "abonos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Abonos extends AbstractEntity<Long> {

    @Column(name = "observacao", nullable = false)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_todo", nullable = false)
    private TipoBoolean diaTodo;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JsonIgnoreProperties("abonos")
    private MotivoAjustes motivos;


    public String getObservacao() {
        return observacao;
    }

    public Abonos observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoBoolean getDiaTodo() {
        return diaTodo;
    }

    public Abonos diaTodo(TipoBoolean diaTodo) {
        this.diaTodo = diaTodo;
        return this;
    }

    public void setDiaTodo(TipoBoolean diaTodo) {
        this.diaTodo = diaTodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Abonos descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MotivoAjustes getMotivos() {
        return motivos;
    }

    public Abonos motivos(MotivoAjustes motivoAjustes) {
        this.motivos = motivoAjustes;
        return this;
    }

    public void setMotivos(MotivoAjustes motivoAjustes) {
        this.motivos = motivoAjustes;
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
        Abonos abonos = (Abonos) o;
        if (abonos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), abonos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Abonos{" +
            "id=" + getId() +
            ", observacao='" + getObservacao() + "'" +
            ", diaTodo='" + getDiaTodo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
