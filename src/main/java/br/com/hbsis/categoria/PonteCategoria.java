package br.com.hbsis.categoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PonteCategoria {

    private static final Logger LOGGER = LoggerFactory.getLogger(PonteCategoria.class);
    private final ICategoriasRepository iCategoriasRepository;

    public PonteCategoria(ICategoriasRepository iCategoriasRepository) {
        this.iCategoriasRepository = iCategoriasRepository;
    }


    public CategoriasDTO findById(Long id) {
        Optional<Categorias> categoriasOptional = this.iCategoriasRepository.findById(id);

        if (categoriasOptional.isPresent()) {
            return CategoriasDTO.of(categoriasOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public Categorias findByIdC(Long id) {
        Optional<Categorias> categoriasOptional = this.iCategoriasRepository.findById(id);

        if (categoriasOptional.isPresent()) {
            return categoriasOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public Optional<Categorias> findByIdCategoria(Long id) {
        Optional<Categorias> categoriasOptional = this.iCategoriasRepository.findById(id);

        if (categoriasOptional.isPresent()) {
            return categoriasOptional;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Categorias de ID: [{}]", id);

        this.iCategoriasRepository.deleteById(id);
    }

    public Categorias save(Categorias categorias){
        return iCategoriasRepository.save(categorias);
    }
    public List<Categorias> findAll(){
        return  this.iCategoriasRepository.findAll();

        }

    public List<Categorias> saveAll(List<Categorias> categorias) {
        return iCategoriasRepository.saveAll(categorias);
    }

    public Categorias findByCod(String cod) {
        Optional<Categorias> categoriasOptional = this.iCategoriasRepository.findByCodCategoria(cod);
        if (categoriasOptional.isPresent()){
            return categoriasOptional.get();
        }
        throw new IllegalArgumentException(String.format("COD %s não existe", cod));
    }

    public boolean existCodCategoria(String s) {
        return this.iCategoriasRepository.existsByCodCategoria(s);
    }

    public Optional<Categorias> findByCodC(String codCategoria) {
        Optional<Categorias> categoriasOptional =this.iCategoriasRepository.findByCodCategoria(codCategoria);
        if (categoriasOptional.isPresent()){
            return categoriasOptional;
        }
        throw new IllegalArgumentException(String.format("COD %s não existe", codCategoria));
    }

    public boolean existsById(Long id) {
       return this.iCategoriasRepository.existsById(id);
    }

}