package br.com.hbsis.pedidos;

import br.com.hbsis.periodoVendas.PeriodoVendas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidosRepository extends JpaRepository<Pedidos, Long> {

    Pedidos findByPeriodoVendas(PeriodoVendas id);
}