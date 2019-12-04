package br.com.rogrs.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.rogrs.domain.enumeration.TipoBoolean;

/**
 * A Turnos.
 */
@Entity
@Table(name = "turnos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "turnos")
public class Turnos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "descricao_turno", nullable = false)
    private String descricaoTurno;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "intervalo_flexivel", nullable = false)
    private TipoBoolean intervaloFlexivel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricaoTurno() {
        return descricaoTurno;
    }

    public Turnos descricaoTurno(String descricaoTurno) {
        this.descricaoTurno = descricaoTurno;
        return this;
    }

    public void setDescricaoTurno(String descricaoTurno) {
        this.descricaoTurno = descricaoTurno;
    }

    public TipoBoolean getIntervaloFlexivel() {
        return intervaloFlexivel;
    }

    public Turnos intervaloFlexivel(TipoBoolean intervaloFlexivel) {
        this.intervaloFlexivel = intervaloFlexivel;
        return this;
    }

    public void setIntervaloFlexivel(TipoBoolean intervaloFlexivel) {
        this.intervaloFlexivel = intervaloFlexivel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turnos)) {
            return false;
        }
        return id != null && id.equals(((Turnos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Turnos{" +
            "id=" + getId() +
            ", descricaoTurno='" + getDescricaoTurno() + "'" +
            ", intervaloFlexivel='" + getIntervaloFlexivel() + "'" +
            "}";
    }
}
