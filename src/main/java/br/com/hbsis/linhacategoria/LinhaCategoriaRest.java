package br.com.hbsis.linhacategoria;
//receber aquisições externas


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/linhaCategoria")
public class LinhaCategoriaRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaRest.class);


    private final LinhaCategoriaService linhaCategoriaService;

    @Autowired
    public LinhaCategoriaRest(LinhaCategoriaService linhaCategoriaService){
        this.linhaCategoriaService =  linhaCategoriaService;
    }

    @PostMapping
    public LinhaCategoriaDTO save(@RequestBody LinhaCategoriaDTO linhaCategoriaDTO){
      LOGGER.info("Recebendo solicitações de persistencia da linha de categoria");
      LOGGER.debug("Payaload: {}\"", linhaCategoriaDTO );

      return this.linhaCategoriaService.save(linhaCategoriaDTO);
    }

/*    @GetMapping
    public  LinhaCategoriaDTO find(@PathVariable("Id") Long Id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", Id);

        return this.linhaCategoriaService.findById(Id);
    }*/
    @PutMapping("/{Id}")
    public LinhaCategoriaDTO udpate(@PathVariable("Id") Long Id, @RequestBody LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Recebendo Update para br.com.hbsis.LinhasCategoria de ID: {}", Id);
        LOGGER.debug("Payload: {}", linhaCategoriaDTO);

        return this.linhaCategoriaService.update(linhaCategoriaDTO, Id);
    }

    @DeleteMapping("/{Id}")
    public void delete(@PathVariable("Id") Long Id) {
        LOGGER.info("Recebendo Delete para br.com.hbsis.LinhaCategoria de ID: {}", Id);

        this.linhaCategoriaService.delete(Id);
    }

    @GetMapping("/export-csv-linhaCategoria")
    public void exportCSV(HttpServletResponse response) throws Exception{
        linhaCategoriaService.exportLinhaCategoria(response);
    }

    @PostMapping(value= "/import-csv-linhaCategoria")
    public void importCSV(@RequestParam ("file")MultipartFile multipartFileImport) throws  Exception{
        linhaCategoriaService.importLinhaCategoria(multipartFileImport);
    }
}
