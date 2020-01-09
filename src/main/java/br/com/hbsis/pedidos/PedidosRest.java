package br.com.hbsis.pedidos;
//receber requisições externas ao sistema

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidosRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pedidos.class);
    private final PedidosService pedidosService;

    public PedidosRest(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    @PostMapping
    public PedidosDTO save(@RequestBody PedidosDTO pedidosDTO){
        LOGGER.info("Recebendo solicitações de persistencia do Pedido");
        LOGGER.debug("Payload: {}\"", pedidosDTO );

        return this.pedidosService.save(pedidosDTO);
    }

    @PutMapping("/{id}")
    public PedidosDTO update(@PathVariable("id") Long id, @RequestBody PedidosDTO pedidosDTO){
        LOGGER.info("Recebendo solicitações de persistencia do Pedidos");
        LOGGER.debug("Payload: {}\"", pedidosDTO );

        return this.pedidosService.update(pedidosDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Deletando pedido de id: [{}]",id);
        this.pedidosService.delete(id);
    }
    @GetMapping("/export-csv-periodoFornecedor/{id}")
    public void exportCSV(HttpServletResponse response, @PathVariable("id") Long id) throws Exception{
        pedidosService.exportPeriodoFornecedor(response, id);
    }
    @GetMapping("/pedidos-funcionario/{id}")
    public List<PedidosDTO> pedidosFuncionario(@PathVariable("id") Long id) {
        return pedidosService.pedidosFuncionario(id);
    }
    @PutMapping("/update-status/{id}")
    public PedidosDTO updateStatus(@PathVariable("id") Long id, @RequestBody PedidosDTO pedidosDTO){
        return this.pedidosService.updateStatus(pedidosDTO, id);
    }
    @PutMapping("/update-completo/{id}")
    public PedidosDTO updateCompleto(@PathVariable("id") Long id, @RequestBody PedidosDTO pedidosDTO){
        return this.pedidosService.updateCompleto(pedidosDTO, id);
    }
    @PutMapping("/update-entrega/{id}")
    public PedidosDTO updateEntrega(@PathVariable("id") Long id, @RequestBody PedidosDTO pedidosDTO){
        return this.pedidosService.updateEntrega(pedidosDTO, id);
    }
}
