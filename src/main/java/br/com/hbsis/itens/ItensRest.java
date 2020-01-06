package br.com.hbsis.itens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
public class ItensRest {

        private static final Logger LOGGER = LoggerFactory.getLogger(ItensRest.class);
        private final ItensService itensService;

    @Autowired
    public ItensRest(ItensService itensService) {
        this.itensService = itensService;
    }

    @PostMapping
    public ItensDTO save(@RequestBody ItensDTO itensDTO){
        LOGGER.info("Recebendo solicitações de persistencia de Itens");
        LOGGER.debug("Payaload: {}\"", itensDTO);

        return this.itensService.save(itensDTO);
    }

    @PutMapping("/{ID")
    public ItensDTO update(@PathVariable("id") Long id, @RequestBody ItensDTO itensDTO){
        LOGGER.info("Recebendo solicitações de persistencia de Itens");
        LOGGER.debug("Payaload: {}\"", itensDTO);

        return this.itensService.update(itensDTO, id);
    }
    @DeleteMapping("/{ID}")
    public void delete(@PathVariable("ID") Long id){
        LOGGER.info("Recebendo Delete para Itens");

        this.itensService.delete(id);
    }


}
