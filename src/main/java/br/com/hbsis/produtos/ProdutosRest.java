package br.com.hbsis.produtos;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/produtos")
public class ProdutosRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);
    private final ProdutosService produtosService;

    @Autowired
    public ProdutosRest(ProdutosService produtosService) {
        this.produtosService = produtosService;
    }

    @PostMapping
    public ProdutosDTO save(@RequestBody ProdutosDTO produtosDTO){
        LOGGER.info("Recebendo solicitações de persistencia de produtos");
        LOGGER.debug("Payaload: {}\"", produtosDTO);

        return this.produtosService.save(produtosDTO);
    }

    @GetMapping("/{id}")
    public ProdutosDTO find (@PathVariable("id")Long id){
        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.produtosService.finById(id);
    }

    @PutMapping("/{id}")
    public ProdutosDTO update(@PathVariable("id") Long id, @RequestBody ProdutosDTO produtosDTO){
        LOGGER.info("Recebendo Update para .Produtos de ID: {`}", id);
        LOGGER.debug("payload: {]", produtosDTO);

        return this.produtosService.update(produtosDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Recebendo delete para .Produtos de ID: {}", id);

        this.produtosService.delete(id);
    }

    //export
    @GetMapping("/export-csv-produtos")
    public void exportcsv(HttpServletResponse response) throws Exception{
        produtosService.findAllExport96(response);
    }

    //import
    @PostMapping(value = "/import-csv-produtos")
    public void importcsv(@RequestParam("file")MultipartFile multipartFileImport) throws Exception{
        produtosService.readAllImport(multipartFileImport);
    }
    //import
    @PutMapping("import-csv-produtos-fornecedor/{id}")
    public void importcsva(@RequestParam("file")MultipartFile multipartFileProdutos, @PathVariable("id") Long id) throws Exception{
        produtosService.findByIdsla(multipartFileProdutos, id);

    }
}