package br.com.rogrs.agamotto.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.rogrs.agamotto.domain.enumeration.TipoMotivoAjuste;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.rogrs.framework.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "controle_ponto_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AttributeOverride(name = "id", column = @Column(name = "CONTROLE_PONTO_ITEM_ID"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ControlePontoItem extends AbstractEntity<Long> {

	@Column(name = "data_ponto", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataPonto;


	@Column(name = "hora_ajuste", nullable = false)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime horaAjuste;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivo")
	private TipoMotivoAjuste motivo;

	@ManyToOne
	@JsonIgnoreProperties("ajustesPontos")
	private ControlePonto controlePonto;

}
