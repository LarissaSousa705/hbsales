package br.com.hbsis.linhacategoria;

import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.CategoriasService;
import br.com.hbsis.categoria.ICategoriasRepository;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.opencsv.*;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    private final ICategoriasRepository iCategoriasRepository;



    public LinhaCategoriaService(ILinhaCategoriaRepository iLinhaCategoriaRepository, CategoriasService categoriasService, ICategoriasRepository iCategoriasRepository) {
        this.iLinhaCategoriaRepository = iLinhaCategoriaRepository;
        this.categoriasService = categoriasService;
        this.iCategoriasRepository = iCategoriasRepository;
    }

    public LinhaCategoriaDTO save(LinhaCategoriaDTO linhaCategoriaDTO) {
        this.validate(linhaCategoriaDTO);

        LOGGER.info("Salvando linhas de catagorias");
        LOGGER.debug("br.com.hbsis.CatgeoriasLinhas:{}", linhaCategoriaDTO);
        LinhaCategoria linhaCategoria = new LinhaCategoria();


        linhaCategoria.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria());
        linhaCategoria.setId(linhaCategoriaDTO.getId());
        linhaCategoria.setCategoriaLinha(linhaCategoriaDTO.getCategoriaLinha());
        linhaCategoria.setIdCategoriaProdutos(categoriasService.findByIdCategoria(linhaCategoriaDTO.getIdCategoriaProdutos()));

        if (linhaCategoria.getCodLinhaCategoria().length() < 10) {
            String codPronto9 = linhaCategoria.getCodLinhaCategoria();
            codPronto9 = StringUtil.leftPad(codPronto9, 10, "0").toUpperCase();
            linhaCategoria.setCodLinhaCategoria(codPronto9);
        }


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

    }

    public LinhaCategoriaDTO findById(Long id) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(id);

        if (linhaCategoriaOptional.isPresent()) {
            return LinhaCategoriaDTO.of(linhaCategoriaOptional.get());

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public LinhaCategoria findByCodLinha(String cod) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findByCodLinhaCategoria(cod);

        if (linhaCategoriaOptional.isPresent()) {
            return linhaCategoriaOptional.get();

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", cod));
    }

    public LinhaCategoria findByIdLinhaCategoria(Long id) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.iLinhaCategoriaRepository.findById(id);

        if (linhaCategoriaOptional.isPresent()) {
            return linhaCategoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("Id %s não existe ", id));
    }



    public LinhaCategoriaDTO update(LinhaCategoriaDTO linhaCategoriaDTO, Long id) {
        Optional<LinhaCategoria> linhaCategoriaExistenteOptional = this.iLinhaCategoriaRepository.findById(id);
        Categorias categorias = new Categorias();

        if (linhaCategoriaExistenteOptional.isPresent()) {
            LinhaCategoria linhaCategoriaExistente = linhaCategoriaExistenteOptional.get();
            LOGGER.info("Atualizando Linha de Categoria... id [{}] ", linhaCategoriaExistente.getId());
            LOGGER.debug("Payload {}", linhaCategoriaDTO);
            LOGGER.debug("br.com.hbsis.LinhaCategoria Existente: {}", linhaCategoriaExistente);

            linhaCategoriaExistente.setCategoriaLinha(linhaCategoriaDTO.getCategoriaLinha());
            linhaCategoriaExistente.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria());
            linhaCategoriaExistente.setIdCategoriaProdutos(categoriasService.findByIdCategoria(linhaCategoriaDTO.getIdCategoriaProdutos()));

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

        String headerCSV[] = {"codLinhaCategoria", "categoriaLinha", "codCatgeoria", "nomeCategoria"};
        icsvWriter.writeNext(headerCSV);

        for (LinhaCategoria line : iLinhaCategoriaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{line.getCodLinhaCategoria(), line.getCategoriaLinha(), line.getIdCategoriaProdutos().getCodCategoria(), line.getIdCategoriaProdutos().getNomeCategoria()});
        }
    }

    //import

    public List<LinhaCategoria> readAll(MultipartFile multipartFile) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();


        List<String[]> linhas = csvReader.readAll();
        List<LinhaCategoria> linhaCategorias = new ArrayList<>();

        for (String[] line : linhas) {
            try {
                String[] line2 = line[0].replaceAll("\"", "").split(";");
                if (iCategoriasRepository.existsByCodCategoria(line2[2])) {
                    LinhaCategoria linhaCategoria = new LinhaCategoria();
                    Categorias categorias = new Categorias();
                    linhaCategoria.setCodLinhaCategoria((line2[0]));
                    linhaCategoria.setCategoriaLinha(line2[1]);
                    categorias = categoriasService.findByCodCategoria(line2[2]);
                    linhaCategoria.setIdCategoriaProdutos(categorias);

                    if (!iLinhaCategoriaRepository.existsByCodLinhaCategoria(line2[0])) {
                        linhaCategorias.add(linhaCategoria);
                    } else {
                        LOGGER.info("Linha Categoria já existe");
                    }
                }else{
                    LOGGER.info("Categoria não existe");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }/*else {
            throw new IllegalArgumentException(String.format("Linha Categoria ja existente", linhaCategorias));

        } */
        return iLinhaCategoriaRepository.saveAll(linhaCategorias);
    }


}

