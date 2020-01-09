package br.com.hbsis.itens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItensRepository extends JpaRepository<Itens, Long> {
}
