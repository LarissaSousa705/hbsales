package br.com.hbsis.periodoVendas;
//receber requisiçoes externas ao sistema


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periodoVendas")
public class PeriodoVendasRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoVendasRest.class);

    private final PeriodoVendasService periodoVendasService;

    @Autowired
    public PeriodoVendasRest(PeriodoVendasService periodoVendasService) {
        this.periodoVendasService = periodoVendasService;
    }

    @PostMapping
    public PeriodoVendasDTO save(@RequestBody PeriodoVendasDTO periodoVendasDTO){
        LOGGER.info("Recebendo solicitações de persistencia do periodo de venddas");
        LOGGER.debug("Payload: {}\"", periodoVendasDTO );

        return this.periodoVendasService.save(periodoVendasDTO);
    }

    @PostMapping("/{Id}")
    public  PeriodoVendasDTO update(@PathVariable("id") Long id, @RequestBody PeriodoVendasDTO periodoVendasDTO){
        LOGGER.info("Recebendo solicitações de persistencia do periodo de venddas");
        LOGGER.debug("Payload: {}\"", periodoVendasDTO );

        return this.periodoVendasService.update(periodoVendasDTO, id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Recebendo solicitações de persistencia do periodo de venddas");

        this.periodoVendasService.delete(id);
    }

}
