package br.com.hbsis.linhacategoria;

import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.CategoriasService;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaCategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaRest.class);

    private final ILinhaCategoriaRepository iLinhaCategoriaRepository;
    private final CategoriasService categoriasService;


    public LinhaCategoriaService(ILinhaCategoriaRepository iLinhaCategoriaRepository, CategoriasService categoriasService) {
        this.iLinhaCategoriaRepository = iLinhaCategoriaRepository;
        this.categoriasService = categoriasService;
    }


    public LinhaCategoriaDTO save(LinhaCategoriaDTO linhaCategoriaDTO) {
        this.validate(linhaCategoriaDTO);

        LOGGER.info("Salvando linhas de catagorias");
        LOGGER.debug("br.com.hbsis.CatgeoriasLinhas:{}", linhaCategoriaDTO);

        LinhaCategoria linhaCategoria = new LinhaCategoria();
        linhaCategoria.setId(linhaCategoriaDTO.getId());
        linhaCategoria.setCategoriaLinha(linhaCategoriaDTO.getCategoriaLinha());
        linhaCategoria.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria());
        linhaCategoria.setNomeCategoria(linhaCategoriaDTO.getNomeCategoria());
        linhaCategoria.setIdCategoriaProdutos(linhaCategoriaDTO.getIdCategoriaProdutos());
        linhaCategoria = this.iLinhaCategoriaRepository.save(linhaCategoria);


        return linhaCategoriaDTO.of(linhaCategoria);
    }

    private void validate(LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Validando Categoria");

        if (linhaCategoriaDTO == null) {
            throw new IllegalArgumentException("linhaCategoriaDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(linhaCategoriaDTO.getCategoriaLinha())) {
            throw new IllegalArgumentException("CategoriaLinha não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(linhaCategoriaDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("NomeCategoria não deve ser nula/vazia");
        }

    }

    public LinhaCategoriaDTO findById(Long id) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(id);

        if(linhaCategoriaOptional.isPresent()){
            return LinhaCategoriaDTO.of(linhaCategoriaOptional.get());

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public LinhaCategoria findByIdLinhaCategoria(Long id){
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(id);

        if (linhaCategoriaOptional.isPresent()){
            return linhaCategoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("Id %s não existe ", id));
    }

    public LinhaCategoriaDTO update(LinhaCategoriaDTO linhaCategoriaDTO, Long id) {
        Optional<LinhaCategoria> linhaCategoriaExistenteOptional = this.iLinhaCategoriaRepository.findById(id);

        if (linhaCategoriaExistenteOptional.isPresent()){
            LinhaCategoria linhaCategoriaExistente = linhaCategoriaExistenteOptional.get();
            LOGGER.info("Atualizando Linha de Categoria... id [{}] ", linhaCategoriaExistente.getId());
            LOGGER.debug("Payload {}", linhaCategoriaDTO);
            LOGGER.debug("br.com.hbsis.LinhaCategoria Existente: {}", linhaCategoriaExistente);

            linhaCategoriaExistente.setId(linhaCategoriaDTO.getId());
            linhaCategoriaExistente.setCategoriaLinha(linhaCategoriaDTO.getCategoriaLinha());
            linhaCategoriaExistente.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria());
            linhaCategoriaExistente.setNomeCategoria(linhaCategoriaDTO.getNomeCategoria());
            linhaCategoriaExistente.setIdCategoriaProdutos(linhaCategoriaDTO.getIdCategoriaProdutos());


            linhaCategoriaExistente = this.iLinhaCategoriaRepository.save(linhaCategoriaExistente);

            return linhaCategoriaDTO.of(linhaCategoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Linhas de Categoria de ID: [{}]", id);

        this.iLinhaCategoriaRepository.deleteById(id);
    }

    //export
    public void findAll(HttpServletResponse response) throws Exception {
        String linhaCategoria = "linhaCategoria.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "Attachment; filename = \"" + linhaCategoria + "\"");
        PrintWriter writer = response.getWriter();


        ICSVWriter icsvWriter = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCSV[] = {"id", "codLinhaCategoria", "nomeCategoria", "categoriaLinha", "idCategoriaProdutos"};
        icsvWriter.writeNext(headerCSV);

        for (LinhaCategoria line : iLinhaCategoriaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{line.getId().toString(), line.getCodLinhaCategoria().toString(), line.getNomeCategoria(), line.getCategoriaLinha(), line.getIdCategoriaProdutos().toString()});
        }
    }

        //import

        public List<LinhaCategoria> readAll(MultipartFile multipartFile) throws Exception {
            InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();

            List<String[]> linhas = csvReader.readAll();
            List<LinhaCategoria> categorias = new ArrayList<>();
            for (String[] line : linhas){
                try{
                    String[] line2 = line[0].replaceAll("\"", "").split(";");
                    LinhaCategoria linhaCategoria = new LinhaCategoria();

                    linhaCategoria.setId(Long.parseLong(line2[0]));
                    linhaCategoria.setCodLinhaCategoria(Long.parseLong(line2[1]));
                    linhaCategoria.setNomeCategoria(line2[2]);
                    linhaCategoria.setCategoriaLinha(line2[3]);
                    linhaCategoria.setIdCategoriaProdutos(Long.parseLong(line2[4]));

                    categorias.add(linhaCategoria);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        return iLinhaCategoriaRepository.saveAll(categorias);

    }

}
