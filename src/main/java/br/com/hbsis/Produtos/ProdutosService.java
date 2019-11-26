package br.com.hbsis.Produtos;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe responsável pelo processamento da regra de negócio
 */
@Service
public class ProdutosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosService.class);

    private final IProdutosRepository iProdutosRepository;

    public ProdutosService(IProdutosRepository iProdutosRepository) {
        this.iProdutosRepository = iProdutosRepository;

    }
    public ProdutosDTO save(ProdutosDTO ProdutosDTO) {

        this.validate(ProdutosDTO);

        LOGGER.info("Salvando produtos");
        LOGGER.debug("br.com.hbsis.Produtos: {}", ProdutosDTO);

        Produtos Produtos = new Produtos();
        Produtos.setNome_categoria(ProdutosDTO.getNome_categoria());
        Produtos.setFornecedor_id(ProdutosDTO.getFornecedor_id());

        Produtos = this.iProdutosRepository.save(Produtos);

        return ProdutosDTO.of(Produtos);

    }
    private void validate(ProdutosDTO ProdutosDTO) {
        LOGGER.info("Validando Produtos");

        if (ProdutosDTO == null) {
            throw new IllegalArgumentException("ProdutosDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(ProdutosDTO.getNome_categoria())) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nulo/vazio");
        }
    }

    public ProdutosDTO findById(Long id) {
        Optional<Produtos> ProdutosOptional = this.iProdutosRepository.findById(id);

        if (ProdutosOptional.isPresent()) {
            return ProdutosDTO.of(ProdutosOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public ProdutosDTO update(ProdutosDTO ProdutosDTO, Long id) {
        Optional<Produtos> ProdutosExistenteOptional = this.iProdutosRepository.findById(id);

        if (ProdutosExistenteOptional.isPresent()) {
            Produtos ProdutosExistente = ProdutosExistenteOptional.get();

            LOGGER.info("Atualizando produtos... id: [{}]", ProdutosExistente.getId());
            LOGGER.debug("Payload: {}", ProdutosDTO);
            LOGGER.debug("br.com.hbsis.Produtos Existente: {}", ProdutosExistente);

            ProdutosExistente.setFornecedor_id(ProdutosDTO.getFornecedor_id());
            ProdutosExistente.setNome_categoria(ProdutosDTO.getNome_categoria());

            ProdutosExistente = this.iProdutosRepository.save(ProdutosExistente);

            return ProdutosDTO.of(ProdutosExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produtos de ID: [{}]", id);

        this.iProdutosRepository.deleteById(id);
    }
}