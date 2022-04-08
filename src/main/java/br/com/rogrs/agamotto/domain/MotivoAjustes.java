package br.com.rogrs.agamotto.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.rogrs.agamotto.domain.enumeration.TipoAjustes;
import br.com.rogrs.agamotto.domain.enumeration.TipoBoolean;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.rogrs.framework.domain.AbstractEntity;

/**
 * A MotivoAjustes.
 */
@Entity
@Table(name = "motivo_ajustes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MotivoAjustes extends AbstractEntity<Long> {

    @Column(name = "descricao_ajuste", nullable = false)
    private String descricaoAjuste;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ajuste", nullable = false)
    private TipoAjustes tipoAjuste;

    @Enumerated(EnumType.STRING)
    @Column(name = "ativo", nullable = false)
    private TipoBoolean ativo;

    @OneToMany(mappedBy = "motivos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Abonos> abonos = new HashSet<>();

    public String getDescricaoAjuste() {
        return descricaoAjuste;
    }

    public MotivoAjustes descricaoAjuste(String descricaoAjuste) {
        this.descricaoAjuste = descricaoAjuste;
        return this;
    }

    public void setDescricaoAjuste(String descricaoAjuste) {
        this.descricaoAjuste = descricaoAjuste;
    }

    public TipoAjustes getTipoAjuste() {
        return tipoAjuste;
    }

    public MotivoAjustes tipoAjuste(TipoAjustes tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
        return this;
    }

    public void setTipoAjuste(TipoAjustes tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    public TipoBoolean getAtivo() {
        return ativo;
    }

    public MotivoAjustes ativo(TipoBoolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(TipoBoolean ativo) {
        this.ativo = ativo;
    }

    public Set<Abonos> getAbonos() {
        return abonos;
    }

    public MotivoAjustes abonos(Set<Abonos> abonos) {
        this.abonos = abonos;
        return this;
    }

    public MotivoAjustes addAbonos(Abonos abonos) {
        this.abonos.add(abonos);
        abonos.setMotivos(this);
        return this;
    }

    public MotivoAjustes removeAbonos(Abonos abonos) {
        this.abonos.remove(abonos);
        abonos.setMotivos(null);
        return this;
    }

    public void setAbonos(Set<Abonos> abonos) {
        this.abonos = abonos;
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
        MotivoAjustes motivoAjustes = (MotivoAjustes) o;
        if (motivoAjustes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motivoAjustes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MotivoAjustes{" +
            "id=" + getId() +
            ", descricaoAjuste='" + getDescricaoAjuste() + "'" +
            ", tipoAjuste='" + getTipoAjuste() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
