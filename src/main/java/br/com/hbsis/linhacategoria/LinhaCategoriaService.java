package br.com.hbsis.linhacategoria;

import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.PonteCategoria;
import br.com.hbsis.export.Export;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.opencsv.*;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaCategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaRest.class);

    private final PonteLinhaCategoria ponteLinhaCategoria;
    private final PonteCategoria ponteCategoria;

    public LinhaCategoriaService(PonteLinhaCategoria ponteLinhaCategoria, PonteCategoria ponteCategoria) {
        this.ponteLinhaCategoria = ponteLinhaCategoria;
        this.ponteCategoria = ponteCategoria;
    }


    public LinhaCategoriaDTO save(LinhaCategoriaDTO linhaCategoriaDTO) {
        this.validate(linhaCategoriaDTO);

        LOGGER.info("Salvando linhas de catagorias");
        LOGGER.debug("br.com.hbsis.CatgeoriasLinhas:{}", linhaCategoriaDTO);
        LinhaCategoria linhaCategoria = new LinhaCategoria();


        linhaCategoria.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria());
        linhaCategoria.setId(linhaCategoriaDTO.getId());
        linhaCategoria.setCategoriaLinha(linhaCategoriaDTO.getCategoriaLinha());
        linhaCategoria.setIdCategoriaProdutos(ponteCategoria.findByIdC(linhaCategoriaDTO.getIdCategoriaProdutos()));

        if (linhaCategoria.getCodLinhaCategoria().length() < 10) {
            String codPronto = linhaCategoria.getCodLinhaCategoria();
            codPronto = StringUtil.leftPad(codPronto, 10, "0").toUpperCase();
            linhaCategoria.setCodLinhaCategoria(codPronto);
        }


        linhaCategoria = this.ponteLinhaCategoria.save(linhaCategoria);

        return LinhaCategoriaDTO.of(linhaCategoria);

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

    public LinhaCategoriaDTO update(LinhaCategoriaDTO linhaCategoriaDTO, Long id) {
        Optional<LinhaCategoria> linhaCategoriaExistenteOptional = this.ponteLinhaCategoria.findById(id);

        if (linhaCategoriaExistenteOptional.isPresent()) {
            LinhaCategoria linhaCategoriaExistente = linhaCategoriaExistenteOptional.get();
            LOGGER.info("Atualizando Linha de Categoria... id [{}] ", linhaCategoriaExistente.getId());
            LOGGER.debug("Payload {}", linhaCategoriaDTO);
            LOGGER.debug("br.com.hbsis.LinhaCategoria Existente: {}", linhaCategoriaExistente);

            linhaCategoriaExistente.setCategoriaLinha(linhaCategoriaDTO.getCategoriaLinha());
            linhaCategoriaExistente.setCodLinhaCategoria(linhaCategoriaDTO.getCodLinhaCategoria());
            linhaCategoriaExistente.setIdCategoriaProdutos(ponteCategoria.findByIdC(linhaCategoriaDTO.getIdCategoriaProdutos()));

            linhaCategoriaExistente = this.ponteLinhaCategoria.save(linhaCategoriaExistente);

            return LinhaCategoriaDTO.of(linhaCategoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Linhas de Categoria de ID: [{}]", id);

        this.ponteLinhaCategoria.deleteById(id);
    }


    //export
    public void exportLinhaCategoria(HttpServletResponse response) throws Exception {

        Export export = new Export();

        export.exportPadrao(new String[]{"codLinhaCategoria", "categoriaLinha", "codCatgeoria", "nomeCategoria"}, response, "exportLinhaCategoria");


        for (LinhaCategoria line : ponteLinhaCategoria.findAll()) {
            export.exportPadrao(new String[]{line.getCodLinhaCategoria(), line.getCategoriaLinha(), line.getIdCategoriaProdutos().getCodCategoria(),
                    line.getIdCategoriaProdutos().getNomeCategoria()}, response, "exportLinhaCategoria");
        }
    }


    public List<LinhaCategoria> importLinhaCategoria(MultipartFile multipartFile) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();


        List<String[]> linhas = csvReader.readAll();
        List<LinhaCategoria> linhaCategorias = new ArrayList<>();

        for (String[] line : linhas) {
            try {
                String[] line2 = line[0].replaceAll("\"", "").split(";");
                if (ponteCategoria.existCodCategoria(line2[2])) {
                    LinhaCategoria linhaCategoria = new LinhaCategoria();
                    Categorias categorias;
                    linhaCategoria.setCodLinhaCategoria((line2[0]));
                    linhaCategoria.setCategoriaLinha(line2[1]);
                    categorias = ponteCategoria.findByCod(line2[2]);
                    linhaCategoria.setIdCategoriaProdutos(categorias);

                    if (!ponteLinhaCategoria.existsByCodLinhaCategoria(line2[0])) {
                        linhaCategorias.add(linhaCategoria);
                    } else {
                        LOGGER.info("Linha Categoria já existe");
                    }
                } else {
                    LOGGER.info("Categoria não existe");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ponteLinhaCategoria.saveAll(linhaCategorias);
    }
}

