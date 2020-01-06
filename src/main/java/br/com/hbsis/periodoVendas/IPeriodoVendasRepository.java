package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import org.aspectj.apache.bcel.generic.FieldOrMethod;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPeriodoVendasRepository extends JpaRepository<PeriodoVendas, Long> {


    boolean existsByPeriodoVendasFornecedor(Fornecedor id);
    PeriodoVendas findByPeriodoVendasFornecedor(Long id);

}
