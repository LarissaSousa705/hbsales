package br.com.hbsis.categoria;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe responsável pela comunciação com o banco de dados
 */
@Repository
interface ICategoriasRepository extends JpaRepository<Categorias, Long> {

    boolean existsByCodCategoria(String codCategoria);
    Optional<Categorias> findByCodCategoria(String codCategoria);

}
