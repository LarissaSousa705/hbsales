package br.com.hbsis.Produtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Classe resposável por receber as requisições externas ao sistema
 */
@RestController
@RequestMapping("/produtos")
public class ProdutosRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);

    private final ProdutosService ProdutosService;


    @Autowired
    public ProdutosRest(ProdutosService ProdutosService) {
        this.ProdutosService = ProdutosService;
    }

    @PostMapping
    public ProdutosDTO save(@RequestBody ProdutosDTO ProdutosDTO) {
        LOGGER.info("Recebendo solicitação de persistência de produtos...");
        LOGGER.debug("Payaload: {}", ProdutosDTO);

        return this.ProdutosService.save(ProdutosDTO);
    }

    @GetMapping("/{id}")
    public ProdutosDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.ProdutosService.findById(id);
    }

    @PutMapping("/{id}")
    public ProdutosDTO udpate(@PathVariable("id") Long id, @RequestBody ProdutosDTO ProdutosDTO) {
        LOGGER.info("Recebendo Update para br.com.hbsis.Produtos de ID: {}", id);
        LOGGER.debug("Payload: {}", ProdutosDTO);

        return this.ProdutosService.update(ProdutosDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para br.com.hbsis.Produtos de ID: {}", id);

        this.ProdutosService.delete(id);
    }
}