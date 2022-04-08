package br.com.rogrs.agamotto.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.ControlePonto;
import br.com.rogrs.agamotto.domain.ControlePontoItem;

//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
@SuppressWarnings("unused")
@Repository
public interface ControlePontoItemRepository extends JpaRepository<ControlePontoItem, Long> {
	
	List<ControlePontoItem> findByControlePonto(ControlePonto controlePonto);
	
	List<ControlePontoItem> findByDataPontoBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);


}
