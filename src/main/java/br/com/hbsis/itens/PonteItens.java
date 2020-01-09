package br.com.hbsis.itens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class PonteItens {
    private static final Logger LOGGER = LoggerFactory.getLogger(PonteItens.class);
    private final IItensRepository iItensRepository;

    public PonteItens(IItensRepository iItensRepository) {
        this.iItensRepository = iItensRepository;
    }

    public Itens save(Itens itens) {
        itens = this.iItensRepository.save(itens);
        return itens;
    }

    public Optional<Itens> findById(Long id) {
        Optional<Itens> itensOptional = this.iItensRepository.findById(id);
        if (itensOptional.isPresent()){
            return itensOptional;
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public void deleteById(Long id) {
        LOGGER.info("Executando delete para Categorias de ID: [{}]", id);

        this.iItensRepository.deleteById(id);
    }
    public List<Itens> findAll() {
        return iItensRepository.findAll();
    }

}
