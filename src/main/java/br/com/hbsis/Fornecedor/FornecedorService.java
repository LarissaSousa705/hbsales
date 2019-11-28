package br.com.hbsis.Fornecedor;

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

    private final IFornecedorRepository iFornecedorRepository;

    public FornecedorService(IFornecedorRepository iFornecedorRepository) {
        this.iFornecedorRepository = iFornecedorRepository;

    }
    public FornecedorDTO save(FornecedorDTO FornecedorDTO) {

        this.validate(FornecedorDTO);

        LOGGER.info("Salvando fornecedores");
        LOGGER.debug("br.com.hbsis.Fornecedor: {}", FornecedorDTO);

        Fornecedor Fornecedor = new Fornecedor();
        Fornecedor.setRazao_social(FornecedorDTO.getRazao_social());
        Fornecedor.setCnpj(FornecedorDTO.getCnpj());
        Fornecedor.setNome_fantasia(FornecedorDTO.getNome_fantasia());
        Fornecedor.setEndereco(FornecedorDTO.getEndereco());
        Fornecedor.setTelefone(FornecedorDTO.getTelefone());
        Fornecedor.setEmail(FornecedorDTO.getEmail());

        Fornecedor = this.iFornecedorRepository.save(Fornecedor);

        return FornecedorDTO.of(Fornecedor);
    }

    private void validate(FornecedorDTO FornecedorDTO) {
        LOGGER.info("Validando Fornecedor");

        if (FornecedorDTO == null) {
            throw new IllegalArgumentException("UsuarioDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(FornecedorDTO.getRazao_social())) {
            throw new IllegalArgumentException("Razão Social não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(FornecedorDTO.getCnpj())) {
            throw new IllegalArgumentException("Cnpj não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(FornecedorDTO.getNome_fantasia())) {
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

    public FornecedorDTO findById(Long id) {
        Optional<Fornecedor> FornecedorOptional = this.iFornecedorRepository.findById(id);

        if (FornecedorOptional.isPresent()) {
            return FornecedorDTO.of(FornecedorOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public FornecedorDTO update(FornecedorDTO FornecedorDTO, Long id) {
        Optional<Fornecedor> FornecedorExistenteOptional = this.iFornecedorRepository.findById(id);

        if (FornecedorExistenteOptional.isPresent()) {
            Fornecedor FornecedorExistente = FornecedorExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", FornecedorExistente.getId());
            LOGGER.debug("Payload: {}", FornecedorDTO);
            LOGGER.debug("br.com.hbsis.Fornecedor Existente: {}", FornecedorExistente);

            FornecedorExistente.setRazao_social(FornecedorDTO.getRazao_social());
            FornecedorExistente.setCnpj(FornecedorDTO.getCnpj());
            FornecedorExistente.setNome_fantasia(FornecedorDTO.getNome_fantasia());
            FornecedorExistente.setEndereco(FornecedorDTO.getEndereco());
            FornecedorExistente.setTelefone(FornecedorDTO.getTelefone());
            FornecedorExistente.setEmail(FornecedorDTO.getEmail());

            FornecedorExistente = this.iFornecedorRepository.save(FornecedorExistente);

            return FornecedorDTO.of(FornecedorExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Fornecedor de ID: [{}]", id);

        this.iFornecedorRepository.deleteById(id);
    }
}

