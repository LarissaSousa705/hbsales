package br.com.hbsis.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PonteFornecedor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PonteFornecedor.class);
    private final IFornecedorRepository iFornecedorRepository;

    public PonteFornecedor(IFornecedorRepository iFornecedorRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public Optional<Fornecedor> findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public Fornecedor findByIdFornecedor(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor save(Fornecedor fornecedor) {
        LOGGER.info("Executando save para Fornecedor : [{}]", fornecedor);
        fornecedor = iFornecedorRepository.save(fornecedor);
        return fornecedor;
    }

    public void deleteById(Long id) {
        LOGGER.info("Executando delete para Fornecedor de ID: [{}]", id);
        this.iFornecedorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return this.iFornecedorRepository.existsById(id);
    }

    public List<Fornecedor> findAll() {
        return this.iFornecedorRepository.findAll();
    }

}
