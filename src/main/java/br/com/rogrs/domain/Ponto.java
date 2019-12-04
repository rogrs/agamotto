package br.com.rogrs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Duration;

import br.com.rogrs.domain.enumeration.TipoMotivoAjuste;

/**
 * A Ponto.
 */
@Entity
@Table(name = "ponto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ponto")
public class Ponto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "marcacao", nullable = false)
    private Duration marcacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo", nullable = false)
    private TipoMotivoAjuste motivo;

    @ManyToOne
    @JsonIgnoreProperties("pontos")
    private ControlePonto controlePonto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getMarcacao() {
        return marcacao;
    }

    public Ponto marcacao(Duration marcacao) {
        this.marcacao = marcacao;
        return this;
    }

    public void setMarcacao(Duration marcacao) {
        this.marcacao = marcacao;
    }

    public TipoMotivoAjuste getMotivo() {
        return motivo;
    }

    public Ponto motivo(TipoMotivoAjuste motivo) {
        this.motivo = motivo;
        return this;
    }

    public void setMotivo(TipoMotivoAjuste motivo) {
        this.motivo = motivo;
    }

    public ControlePonto getControlePonto() {
        return controlePonto;
    }

    public Ponto controlePonto(ControlePonto controlePonto) {
        this.controlePonto = controlePonto;
        return this;
    }

    public void setControlePonto(ControlePonto controlePonto) {
        this.controlePonto = controlePonto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ponto)) {
            return false;
        }
        return id != null && id.equals(((Ponto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ponto{" +
            "id=" + getId() +
            ", marcacao='" + getMarcacao() + "'" +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
