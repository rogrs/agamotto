package br.com.rogrs.agamotto.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.Turnos;


@SuppressWarnings("unused")
@Repository
public interface TurnosRepository extends JpaRepository<Turnos, Long> {

}
