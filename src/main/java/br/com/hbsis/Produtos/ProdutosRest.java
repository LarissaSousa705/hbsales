package br.com.hbsis.Produtos;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Classe resposável por receber as requisições externas ao sistema
 */
@RestController
@RequestMapping("/produtos")
public class ProdutosRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);


    private final ProdutosService produtosService;


    @Autowired
    public ProdutosRest(ProdutosService ProdutosService) {
        this.produtosService = ProdutosService;
    }

    @PostMapping
    public ProdutosDTO save(@RequestBody ProdutosDTO ProdutosDTO) {
        LOGGER.info("Recebendo solicitação de persistência de produtos...");
        LOGGER.debug("Payaload: {}", ProdutosDTO);

        return this.produtosService.save(ProdutosDTO);
    }

    @GetMapping("/export-csv-produtos")
    public void exportCSV(HttpServletResponse response) throws Exception{
        produtosService.findAll(response);
    }

    @PostMapping("/import-csv-produtos")
    public void importCSV(@RequestParam ("file") MultipartFile multipartFile) throws Exception{
        produtosService.readAll(multipartFile);

    }

    @GetMapping("/{id}")
    public ProdutosDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.produtosService.findById(id);
    }

    @PutMapping("/{id}")
    public ProdutosDTO udpate(@PathVariable("id") Long id, @RequestBody ProdutosDTO ProdutosDTO) {
        LOGGER.info("Recebendo Update para br.com.hbsis.Produtos de ID: {}", id);
        LOGGER.debug("Payload: {}", ProdutosDTO);

        return this.produtosService.update(ProdutosDTO, id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para br.com.hbsis.Produtos de ID: {}", id);

        this.produtosService.delete(id);
    }
}