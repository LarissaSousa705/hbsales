package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import com.opencsv.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe responsável pelo processamento da regra de negócio
 */
@Service
public class CategoriasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriasService.class);

    private final ICategoriasRepository iCategoriasRepository;
    private final FornecedorService fornecedorService;

    public CategoriasService(ICategoriasRepository iCategoriasRepository, FornecedorService fornecedorService) {
        this.iCategoriasRepository = iCategoriasRepository;
        this.fornecedorService = fornecedorService;


    }

    public CategoriasDTO save(CategoriasDTO categoriasDTO) {

        //Fornecedor fornecedor = fornecedorService.findByIdFornecedor(categoriasDTO.getFornecedor04().get;

        this.validate(categoriasDTO);



        LOGGER.info("Salvando categorias");
        LOGGER.debug("br.com.hbsis.Categorias: {}", categoriasDTO);

        //Optional<Fornecedor> fornecedorOptional = this.fornecedorService.findByIdFornecedor(categoriasDTO.getFornecedor04().get);

        Categorias Categorias = new Categorias();


        Categorias.setCod_categoria(categoriasDTO.getCodCategoria());
        Categorias.setNome_categoria(categoriasDTO.getNomeCategoria());
        Categorias.setFornecedor04(fornecedorService.findByIdFornecedor(categoriasDTO.getFornecedor04()));

        Categorias = this.iCategoriasRepository.save(Categorias);

        return categoriasDTO.of(Categorias);

    }

    private void validate(CategoriasDTO CategoriasDTO) {
        LOGGER.info("Validando Categorias");

        if (CategoriasDTO == null) {
            throw new IllegalArgumentException("CategoriasDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(CategoriasDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nulo/vazio");
        }
    }

    public CategoriasDTO findById(Long id) {
        Optional<Categorias> CategoriasOptional = this.iCategoriasRepository.findById(id);

        if (CategoriasOptional.isPresent()) {
            return CategoriasDTO.of(CategoriasOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Categorias findByIdCategoria(Long id) {
        Optional<Categorias> CategoriasOptional = this.iCategoriasRepository.findById(id);

        if (CategoriasOptional.isPresent()) {
            return CategoriasOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriasDTO update(CategoriasDTO categoriasDTO, Long id) {
        Optional<Categorias> categoriasExistenteOptional = this.iCategoriasRepository.findById(id);

        if (categoriasExistenteOptional.isPresent()) {
            Categorias categoriasExistente = categoriasExistenteOptional.get();

            LOGGER.info("Atualizando categorias... id: [{}]", categoriasExistente.getId());
            LOGGER.debug("Payload: {}", categoriasDTO);
            LOGGER.debug("br.com.hbsis.Categorias Existente: {}", categoriasExistente);


            categoriasExistente.setCod_categoria(categoriasDTO.getCodCategoria());
            categoriasExistente.setNome_categoria(categoriasDTO.getNomeCategoria());
            categoriasExistente.setFornecedor04(fornecedorService.findByIdFornecedor(categoriasDTO.getFornecedor04()));

            categoriasExistente = this.iCategoriasRepository.save(categoriasExistente);

            return categoriasDTO.of(categoriasExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produtos de ID: [{}]", id);

        this.iCategoriasRepository.deleteById(id);
    }

    //export
    public void findAll(HttpServletResponse response) throws Exception {
        String categorias= "categorias.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + categorias + "\"");
        PrintWriter writer = response.getWriter();

        ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCVS[] = {"id","cod_categoria" , "nome_categoria" ,"fornecedor04" };
        csvWriter.writeNext(headerCVS);

        for(Categorias linha : iCategoriasRepository.findAll()) {
            csvWriter.writeNext(new String[]{ linha.getId().toString(), linha.getCod_categoria().toString(), linha.getNome_categoria(), linha.getFornecedor04().toString()});
        }
    }
    //import
    public List<Categorias> readAll(MultipartFile multipartFile) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();

        List<String[]> linhas = csvReader.readAll();
        List<Categorias> produtos = new ArrayList<>();
        for (String[] line : linhas) {
            try {
                String[] line2 = line[0].replaceAll("\"", "").split(";");

                Categorias clas_categorias = new Categorias();
                Fornecedor fornecedorCompleto = new Fornecedor();
                FornecedorDTO fornecedorDTO = new FornecedorDTO();

                clas_categorias.setId(Long.parseLong(line2[0]));
                clas_categorias.setCod_categoria(Long.parseLong(line2[1]));
                clas_categorias.setNome_categoria(line2[2]);
                System.out.println("chegou aqui");
           //     clas_categorias.setFornecedor04(List.class(line2[3]));



          //     clas_categorias.setFornecedor04(fornecedorService.findByIdFornecedor(toString(line2[3])));

//               clas_categorias.setFornecedor04(fornecedorService.findByIdFornecedor(line2[3]));
            //   clas_categorias.setFornecedor04(Long.parseLong(line2[3]));
           //    clas_categorias.getFornecedor04((line2[3]).toString());
           //     clas_categorias.setFornecedor04(line2[3]);
           //     clas_categorias.setFornecedor04(fornecedorService.findByIdFornecedor(line2[3]));
           //     clas_categorias.setFornecedor04(fornece);
           //     clas_categorias.setFornecedor04(Long.parseLong(line2[3]));
                //fornecedorDTO = fornecedorService.findByIdFornecedor(Long.parseLong(line2[3]));
             //  clas_categorias.setFornecedor(Long.parseLong(line2[5]));
            //    clas_categorias.setFornecedor04(line2[4]);

           //     clas_categorias.setFornecedor(line2[3]);

                produtos.add(clas_categorias);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iCategoriasRepository.saveAll(produtos);
    }

/*    public void importcsv(MultipartFile multipartFile){
        String line = "";
        String split = ";";

        try(BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {

            //negocio que vai fazer pular uma linha
            line = br.readLine();


            while ((line = br.readLine()) != null){
                String[] categoria = line.split(split);

                Optional<Fornecedor> fornecedorOptional = Optional.ofNullable(fornecedorService.findByIdOptitional(Long.parseLong(categoria[3])));

                if(fornecedorOptional.isPresent()){
                    Categorias categorias = new Categorias();

                    categorias.setId(Long.parseLong(categoria[0]));
                    categorias.setCod_categoria(Long.parseLong(categoria[1]));
                    categorias.setNome_categoria(categoria[2]);
                    categorias.setFornecedor04(fornecedorOptional.get());
                    this.iCategoriasRepository.save(categorias);


                }else{
                    throw new IllegalArgumentException(String.format("Fornecedor Id nao existe", fornecedorOptional));
                }

                System.out.println("Errou");
            }

        }catch (IOException e){
            e.printStackTrace();
        }



    }*/

}