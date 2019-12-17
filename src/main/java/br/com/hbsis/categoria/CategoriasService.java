package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import com.opencsv.*;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
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
    private final IFornecedorRepository iFornecedorRepository;


    public CategoriasService(ICategoriasRepository iCategoriasRepository, FornecedorService fornecedorService, IFornecedorRepository iFornecedorRepository) throws ParseException {
        this.iCategoriasRepository = iCategoriasRepository;
        this.fornecedorService = fornecedorService;
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public CategoriasDTO save(CategoriasDTO categoriasDTO) {
        Optional<Fornecedor> fornecedorOptional = Optional.ofNullable(this.fornecedorService.findByIdFornecedor(categoriasDTO.getFornecedor04()));

        LOGGER.info("Salvando categorias");
        LOGGER.debug("br.com.hbsis.Categorias: {}", categoriasDTO);

        this.validate(categoriasDTO);
        Categorias categorias = new Categorias();

        String cnpjPronto = fornecedorOptional.get().getCnpj().substring(10,13);


        categorias.setFornecedor04(fornecedorService.findByIdFornecedor(categoriasDTO.getFornecedor04()));
        categorias.setNomeCategoria(categoriasDTO.getNomeCategoria());
        String codPronto3 =  "CAT" + cnpjPronto + codPronto5(categoriasDTO.getCodCategoria());
        categorias.setCodCategoria(codPronto3);


        categorias = this.iCategoriasRepository.save(categorias);

        return categoriasDTO.of(categorias);

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
    public Fornecedor findByIdF(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return Fornecedor.of(fornecedorOptional.get());
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
    public Categorias findByCodCategoria(String cod) {
        Optional<Categorias> CategoriasOptional = this.iCategoriasRepository.findByCodCategoria(cod);

        if (CategoriasOptional.isPresent()) {
            return CategoriasOptional.get();
        }

        throw new IllegalArgumentException(String.format("Cod %s não existe", cod));
    }


    public CategoriasDTO update(CategoriasDTO categoriasDTO, Long id) {
        Optional<Categorias> categoriasExistenteOptional = this.iCategoriasRepository.findById(id);

        if (categoriasExistenteOptional.isPresent()) {
            Categorias categoriasExistente = categoriasExistenteOptional.get();

            LOGGER.info("Atualizando categorias... id: [{}]", categoriasExistente.getId());
            LOGGER.debug("Payload: {}", categoriasDTO);
            LOGGER.debug("br.com.hbsis.Categorias Existente: {}", categoriasExistente);


            categoriasExistente.setCodCategoria(categoriasDTO.getCodCategoria());
            categoriasExistente.setNomeCategoria(categoriasDTO.getNomeCategoria());
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

        String headerCVS[] = {"id","cod_categoria" , "nome_categoria" ,"fornecedor04", "razão social", "cnpj" };
        csvWriter.writeNext(headerCVS);

        for(Categorias linha : iCategoriasRepository.findAll()) {

            String cnpj =linha.getFornecedor04().getCnpj();
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            String cnpjMask = mask.valueToString(cnpj);

            csvWriter.writeNext(new String[]{ linha.getId().toString(), linha.getCodCategoria(),
                    linha.getNomeCategoria(), linha.getFornecedor04().getId().toString(),
                    linha.getFornecedor04().getRazao(), cnpjMask});
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
                FornecedorDTO fornecedorDTO = new FornecedorDTO();
                Fornecedor fornecedor = new Fornecedor();

                String cnpj =(line2[4]);
                String razao = (line2[5]);

                Fornecedor fornecedorCompleto = iFornecedorRepository.findByCnpj(cnpj);
                clas_categorias.setFornecedor04(findByIdF(Long.parseLong(line2[3])));
                clas_categorias.setId(Long.parseLong(line2[0]));
                clas_categorias.setCodCategoria(line2[1]);
                clas_categorias.setNomeCategoria(line2[2]);

                cnpj.replaceAll("[^0-9]", "");

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
    public String codPronto5 (String cod){
        cod = StringUtil.leftPad(cod, 3, "0");
        return cod;

    }

}