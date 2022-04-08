package br.com.rogrs.agamotto.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.rogrs.framework.domain.AbstractEntity;

/**
 * A ControlePonto.
 */
@Entity
@Table(name = "controle_ponto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ControlePonto extends AbstractEntity<Long> {


	@Column(name = "fechamento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechamento;

    @OneToMany(mappedBy = "controlePonto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ControlePontoItem> ajustesPontos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("controles")
    private Colaboradores colaborador;


    public Set<ControlePontoItem> getAjustesPontos() {
        return ajustesPontos;
    }

    public ControlePonto ajustesPontos(Set<ControlePontoItem> ajustesPontos) {
        this.ajustesPontos = ajustesPontos;
        return this;
    }

    public ControlePonto addAjustesPontos(ControlePontoItem ajustesPontos) {
        this.ajustesPontos.add(ajustesPontos);
        ajustesPontos.setControlePonto(this);
        return this;
    }

    public ControlePonto removeAjustesPontos(ControlePontoItem ajustesPontos) {
        this.ajustesPontos.remove(ajustesPontos);
        ajustesPontos.setControlePonto(null);
        return this;
    }

    public void setAjustesPontos(Set<ControlePontoItem> ajustesPontos) {
        this.ajustesPontos = ajustesPontos;
    }

    public Colaboradores getColaborador() {
        return colaborador;
    }

    public ControlePonto colaborador(Colaboradores colaboradores) {
        this.colaborador = colaboradores;
        return this;
    }

    public void setColaborador(Colaboradores colaboradores) {
        this.colaborador = colaboradores;
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
        ControlePonto controlePonto = (ControlePonto) o;
        if (controlePonto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), controlePonto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	public LocalDate getFechamento() {
		return fechamento;
	}

	public void setFechamento(LocalDate fechamento) {
		this.fechamento = fechamento;
	}

}
