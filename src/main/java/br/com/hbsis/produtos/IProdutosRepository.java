package br.com.hbsis.produtos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProdutosRepository extends JpaRepository<Produtos, Long> {

    Optional<Produtos> findByCodProduto(String codProduto);

}




