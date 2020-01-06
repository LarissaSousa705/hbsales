package br.com.hbsis.funcionario;
//receber requisições externas ao sistema

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioRest.class);
    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRest(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public FuncionarioDTO save(@RequestBody FuncionarioDTO funcionarioDTO){
        LOGGER.info("Recebendo solicitações de persistencia do Funcionario");
        LOGGER.debug("Payload: {}\"", funcionarioDTO );

        return this.funcionarioService.save(funcionarioDTO);
    }
    @PutMapping("/{id}")
    public  FuncionarioDTO update(@PathVariable("id") Long id, @RequestBody FuncionarioDTO funcionarioDTO){
        LOGGER.info("Recebendo solicitações de persistencia do Funcionario");
        LOGGER.debug("Payload: {}\"", funcionarioDTO );

        return this.funcionarioService.update(funcionarioDTO,id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Deletando Funcionario de id: [{}]",id);
        this.funcionarioService.delete(id);
    }
}
