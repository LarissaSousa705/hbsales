package br.com.hbsis.Produtos;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe responsável pela comunciação com o banco de dados
 */
@Repository
public interface IProdutosRepository extends JpaRepository<Produtos, Long> {
}