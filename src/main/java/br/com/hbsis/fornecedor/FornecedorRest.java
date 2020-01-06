package br.com.hbsis.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Classe resposável por receber as requisições externas ao sistema
 */
@RestController
@RequestMapping("/fornecedor")
public class FornecedorRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorRest.class);

    private final FornecedorService fornecedorService;


    @Autowired
    public FornecedorRest(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public FornecedorDTO save(@RequestBody FornecedorDTO fornecedorDTO) {
        LOGGER.info("Recebendo solicitação de persistência de fornecedor...");
        LOGGER.debug("Payaload: {}", fornecedorDTO);

        return this.fornecedorService.save(fornecedorDTO);
    }


    @PutMapping("/{id}")
    public FornecedorDTO udpate(@PathVariable("id") Long id, @RequestBody FornecedorDTO fornecedorDTO) {
        LOGGER.info("Recebendo Update para br.com.hbsis.Fornecedor de ID: {}", id);
        LOGGER.debug("Payload: {}", fornecedorDTO);

        return this.fornecedorService.update(fornecedorDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para br.com.hbsis.Fornecedor de ID: {}", id);

        this.fornecedorService.delete(id);
    }



}
