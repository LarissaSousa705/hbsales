package br.com.hbsis.itens;

import br.com.hbsis.pedidos.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItensRepository extends JpaRepository<Itens, Long> {

    Itens findByPedidos(Pedidos id);
}
