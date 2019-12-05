package br.com.hbsis.categoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Classe resposável por receber as requisições externas ao sistema
 */
@RestController
@RequestMapping("/categorias")
public class CategoriasRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriasRest.class);


    private final CategoriasService categoriasService;


    @Autowired
    public CategoriasRest(CategoriasService CategoriasService) {
        this.categoriasService = CategoriasService;
    }

    @PostMapping
    public CategoriasDTO save(@RequestBody CategoriasDTO categoriasDTO) {
        LOGGER.info("Recebendo solicitação de persistência de categorias...");
        LOGGER.debug("Payaload: {}", categoriasDTO);

        return this.categoriasService.save(categoriasDTO);
    }

    @GetMapping("/export-csv-categorias")
    public void exportCSV(HttpServletResponse response) throws Exception{
        categoriasService.findAll(response);
    }

    @PostMapping("/import-csv-categorias")
    public void importCSV(@RequestParam ("file") MultipartFile multipartFileImport) throws Exception{
        categoriasService.readAll(multipartFileImport);

    }

/*    @PostMapping(value = "/import-csv-categorias", consumes = "multipart/form-data")
    public void issoAi(@RequestParam("file") MultipartFile multipartFile){
        this.categoriasService.importcsv(multipartFile);
    }*/


    @GetMapping("/{id}")
    public CategoriasDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.categoriasService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoriasDTO udpate(@PathVariable("id") Long id, @RequestBody CategoriasDTO ProdutosDTO) {
        LOGGER.info("Recebendo Update para br.com.hbsis.Produtos de ID: {}", id);
        LOGGER.debug("Payload: {}", ProdutosDTO);

        return this.categoriasService.update(ProdutosDTO, id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para br.com.hbsis.Produtos de ID: {}", id);

        this.categoriasService.delete(id);
    }
}