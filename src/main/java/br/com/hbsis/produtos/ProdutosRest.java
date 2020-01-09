package br.com.hbsis.produtos;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@RestController
@RequestMapping("/produtos")
public class ProdutosRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);
    private final ProdutosService produtosService;
    private final PonteProdutos ponteProdutos;

    @Autowired
    public ProdutosRest(ProdutosService produtosService, PonteProdutos ponteProdutos) {
        this.produtosService = produtosService;
        this.ponteProdutos = ponteProdutos;
    }

    @PostMapping
    public ProdutosDTO save(@RequestBody ProdutosDTO produtosDTO){
        LOGGER.info("Recebendo solicitações de persistencia de produtos");
        LOGGER.debug("Payaload: {}\"", produtosDTO);

        return this.produtosService.save(produtosDTO);
    }

    @GetMapping("/{id}")
    public Optional<Produtos> find (@PathVariable("id")Long id){
        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.ponteProdutos.findById(id);
    }

    @PutMapping("/{id}")
    public ProdutosDTO update(@PathVariable("id") Long id, @RequestBody ProdutosDTO produtosDTO){
        LOGGER.info("Recebendo Update para .Produtos de ID: {}", id);
        LOGGER.debug("payload: {}", produtosDTO);

        return this.produtosService.update(produtosDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Recebendo delete para .Produtos de ID: {}", id);

        this.produtosService.delete(id);
    }

    @GetMapping("/export-csv-produtos")
    public void exportcsv(HttpServletResponse response) throws Exception{
        produtosService.exportProduto(response);
    }

    @PostMapping(value = "/import-csv-produtos")
    public void importcsv(@RequestParam("file")MultipartFile multipartFileImport) throws Exception{
        produtosService.importProduto(multipartFileImport);
    }
    @PutMapping("import-csv-produtos-fornecedor/{id}")
    public void importcsva(@RequestParam("file")MultipartFile multipartFileProdutos, @PathVariable("id") Long id) throws Exception{
        produtosService.importProdutoFornecedor(multipartFileProdutos, id);

    }
    @GetMapping("/export-csv-periodoFornecedor/{id}/{id}")
    public void exportCSV(HttpServletResponse response, @PathVariable("id") Long id, @PathVariable("id") Long id2) throws Exception{
        produtosService.exportProdutos(response, id, id2);
    }

}