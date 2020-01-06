package br.com.hbsis.fornecedor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe responsável pelo processamento da regra de negócio
 */
@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);
    private final PonteFornecedor ponteFornecedor;

    public FornecedorService(PonteFornecedor ponteFornecedor) {
        this.ponteFornecedor = ponteFornecedor;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        this.validate(fornecedorDTO);

        LOGGER.info("Salvando fornecedores");
        LOGGER.debug("{} Fornecedor: {}", FornecedorService.class.getName(), fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazao(fornecedorDTO.getRazao());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        fornecedor = this.ponteFornecedor.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }


    private void validate(FornecedorDTO FornecedorDTO) {
        LOGGER.info("Validando Fornecedor");

        if (FornecedorDTO == null) {
            throw new IllegalArgumentException("UsuarioDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(FornecedorDTO.getRazao())) {
            throw new IllegalArgumentException("Razão Social não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(FornecedorDTO.getCnpj())) {
            throw new IllegalArgumentException("Cnpj não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getNomeFantasia())) {
            throw new IllegalArgumentException("Nome Fantasia não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getEndereco())) {
            throw new IllegalArgumentException("Endereço não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getTelefone())) {
            throw new IllegalArgumentException("Telefone não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail não deve ser nulo/vazio");
        }
    }



    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.ponteFornecedor.findById(id);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("br.com.hbsis.Fornecedor Existente: {}", fornecedorExistente);


            fornecedorExistente.setRazao(fornecedorDTO.getRazao());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());
            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());

            fornecedorExistente = this.ponteFornecedor.save(fornecedorExistente);


            return FornecedorDTO.of(fornecedorExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public void delete(Long id) {
        LOGGER.info("Executando delete para Fornecedor de ID: [{}]", id);

        this.ponteFornecedor.deleteById(id);
    }

}