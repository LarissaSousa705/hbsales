package br.com.hbsis.linhacategoria;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PonteLinhaCategoria {

    private static final Logger LOGGER = LoggerFactory.getLogger(PonteLinhaCategoria.class);
    private final ILinhaCategoriaRepository iLinhaCategoriaRepository;

    public PonteLinhaCategoria(ILinhaCategoriaRepository iLinhaCategoriaRepository) {
        this.iLinhaCategoriaRepository = iLinhaCategoriaRepository;
    }

    public LinhaCategoria save(LinhaCategoria linhaCategoria) {
        linhaCategoria = this.iLinhaCategoriaRepository.save(linhaCategoria);
        return linhaCategoria;
    }

    public Optional<LinhaCategoria> findById(Long id) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(id);
        if (linhaCategoriaOptional.isPresent()){
            return linhaCategoriaOptional;
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Optional<LinhaCategoria> findByCodLinhaCategoria(String cod) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findByCodLinhaCategoria(cod);
        if (linhaCategoriaOptional.isPresent()){
            return linhaCategoriaOptional;
        }
        throw new IllegalArgumentException(String.format("COD %s não existe", cod));
    }

    public void deleteById(Long id) {
        LOGGER.info("Executando delete para Categorias de ID: [{}]", id);

        this.iLinhaCategoriaRepository.deleteById(id);
    }

    public List<LinhaCategoria> findAll() {
        List<LinhaCategoria> linhaCategoriaList = this.iLinhaCategoriaRepository.findAll();
        if (linhaCategoriaList.isEmpty()){
            return linhaCategoriaList;
        }
        throw new IllegalArgumentException("Linha Categoria não encontrado ");
    }


    public boolean existsByCodLinhaCategoria(String s) {
        return this.iLinhaCategoriaRepository.existsByCodLinhaCategoria(s);
    }

    public List<LinhaCategoria> saveAll(List<LinhaCategoria> linhaCategorias) {
        List<LinhaCategoria> linhaCategoriaList = this.iLinhaCategoriaRepository.saveAll(linhaCategorias);
        if (linhaCategoriaList.isEmpty()){
            return linhaCategoriaList;
        }
        throw new IllegalArgumentException("Linha Categoria não foi salvo... ");
    }

    public LinhaCategoria findByIdLinhaCategoria(Long linhaCategoriaProduto) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(linhaCategoriaProduto);
        if (linhaCategoriaOptional.isPresent()){
            return linhaCategoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não encontrado", linhaCategoriaProduto));
    }

    public LinhaCategoria findByCodLinha(String e) {
        Optional<LinhaCategoria> linhaCategoriaOptional =this.iLinhaCategoriaRepository.findByCodLinhaCategoria(e);
        if (linhaCategoriaOptional.isPresent()){
            return linhaCategoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("COD %s não encontrador", e));

    }
}
