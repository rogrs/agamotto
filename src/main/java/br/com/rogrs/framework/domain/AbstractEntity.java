package br.com.rogrs.framework.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@MappedSuperclass
@Getter
@Setter
public abstract class  AbstractEntity<PK extends Serializable> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "criado", updatable = false, nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate criado;

	@Column(name = "modificado", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate modificado;

	@PrePersist
	protected void prePersist() {
		criado = LocalDate.now();
		preUpdate();
	}

	@PreUpdate
	public void preUpdate() {
		modificado = LocalDate.now();
	}

}