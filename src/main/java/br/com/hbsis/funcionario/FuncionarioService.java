package br.com.hbsis.funcionario;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

//regra de negocios

@Service
public class FuncionarioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Funcionario.class);
    private final PonteFuncionario ponteFuncionario;

    public FuncionarioService(PonteFuncionario ponteFuncionario) {
        this.ponteFuncionario = ponteFuncionario;
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO){
        this.validate(funcionarioDTO);
        LOGGER.info("Salvando funcionario");
        LOGGER.debug("br.com.hbsis.Funcionario:{}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();

        funcionario.setId(funcionarioDTO.getId());
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setUuidApi(funcionarioDTO.getUuidApi());

        funcionario = this.ponteFuncionario.save(funcionario);
        return FuncionarioDTO.of(funcionario);
    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando Funcionario");

        if (funcionarioDTO == null){
            throw new IllegalArgumentException("FuncionarioDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(funcionarioDTO.getNome())){
            throw new IllegalArgumentException("Nome do funcionário não deve ser nulo");
        }
        if (StringUtils.isEmpty(funcionarioDTO.getEmail())){
            throw new IllegalArgumentException("E-mail do funcionário não deve ser nulo");
        }

        this.validacaoAPI(funcionarioDTO);
    }

    private void validacaoAPI(FuncionarioDTO funcionarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "f59feb10-1b67-11ea-978f-2e728ce88125");
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(funcionarioDTO, httpHeaders);
        ResponseEntity<EmployeeDTO> reponseEntity = restTemplate.exchange("http://10.2.54.25:9999/api/employees", HttpMethod.POST, httpEntity, EmployeeDTO.class);
        funcionarioDTO.setUuidApi(Objects.requireNonNull(reponseEntity.getBody().getEmployeeUuid()));
        funcionarioDTO.setNome(reponseEntity.getBody().getEmployeeName());

    }

    public FuncionarioDTO update(FuncionarioDTO funcionarioDTO, Long id){
        Optional<Funcionario> funcionarioOptional = this.ponteFuncionario.findById(id);

        if (funcionarioOptional.isPresent()){
            Funcionario funcionario = funcionarioOptional.get();
            LOGGER.info("Atualizando Funcionario... id[{}]", funcionario.getId());
            LOGGER.debug("Payload {}", funcionarioDTO);
            LOGGER.debug("br.com.hbsis.Funcionario EXistente:{}", funcionario);

            funcionario.setId(funcionarioDTO.getId());
            funcionario.setNome(funcionarioDTO.getNome());
            funcionario.setEmail(funcionarioDTO.getEmail());
            funcionario.setUuidApi(funcionarioDTO.getUuidApi());

            funcionario = this.ponteFuncionario.save(funcionario);
            return FuncionarioDTO.of(funcionario);

        }
        throw new IllegalArgumentException(String.format("ID %s não existe ", id));
    }

    public void delete(Long id){
        LOGGER.info("Executando delete para Funcionario de ID: [{}]", id);
        this.ponteFuncionario.deleteById(id);
    }
}
