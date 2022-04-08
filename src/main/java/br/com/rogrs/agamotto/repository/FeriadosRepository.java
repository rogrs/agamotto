package br.com.rogrs.agamotto.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.Feriados;


@SuppressWarnings("unused")
@Repository
public interface FeriadosRepository extends JpaRepository<Feriados, Long> {

}
