package br.com.hbsis.categoria;

import br.com.hbsis.export.Export;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.PonteFornecedor;
import com.opencsv.*;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe responsável pelo processamento da regra de negócio
 */
@Service
public class CategoriasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriasService.class);

    private final PonteCategoria ponteCategoria;
    private final PonteFornecedor ponteFornecedor;

    public CategoriasService(PonteCategoria ponteCategoria, PonteFornecedor ponteFornecedor) {
        this.ponteCategoria = ponteCategoria;
        this.ponteFornecedor = ponteFornecedor;
    }


    public CategoriasDTO save(CategoriasDTO categoriasDTO) {
        Optional<Fornecedor> fornecedorOptional = this.ponteFornecedor.findById(categoriasDTO.getFornecedor());

        Fornecedor fornecedor = new Fornecedor();
        LOGGER.info("Salvando categorias");
        LOGGER.debug("br.com.hbsis.Categorias: {}", categoriasDTO);

        this.validate(categoriasDTO);
        Categorias categorias = new Categorias();

        String cnpj = fornecedorOptional.get().getCnpj().replaceAll("[^0-9.]", "");
        String cnpjPronto = cnpj.substring(10, 14);

        categorias.setFornecedor(ponteFornecedor.findByIdFornecedor(categoriasDTO.getFornecedor()));
        Long idF = ponteFornecedor.findByIdFornecedor(categoriasDTO.getFornecedor()).getId();
        categoriasDTO.setFornecedor(idF);

        categorias.setNomeCategoria(categoriasDTO.getNomeCategoria());
        String codCategoria = categoriasDTO.getCodCategoria();

        if (codCategoria.length() < 10) {
            String codPronto2 = "CAT" + cnpjPronto + codPronto(categoriasDTO.getCodCategoria());
            categorias.setCodCategoria(codPronto2);
        }
        categorias.setCodCategoria(codCategoria);

        categorias = this.ponteCategoria.save(categorias);

        return CategoriasDTO.of(categorias);

    }

    private void validate(CategoriasDTO categoriasDTO) {
        LOGGER.info("Validando Categorias");

        if (categoriasDTO == null) {
            throw new IllegalArgumentException("CategoriasDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(categoriasDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nulo/vazio");
        }
    }

    public CategoriasDTO update(CategoriasDTO categoriasDTO, Long id) {
        Optional<Categorias> categoriasExistenteOptional = this.ponteCategoria.findByIdCategoria(id);

        if (categoriasExistenteOptional.isPresent()) {
            Categorias categoriasExistente = categoriasExistenteOptional.get();

            LOGGER.info("Atualizando categorias... id: [{}]", categoriasExistente.getId());
            LOGGER.debug("Payload: {}", categoriasDTO);
            LOGGER.debug("br.com.hbsis.Categorias Existente: {}", categoriasExistente);


            categoriasExistente.setCodCategoria(categoriasDTO.getCodCategoria());
            categoriasExistente.setNomeCategoria(categoriasDTO.getNomeCategoria());
            categoriasExistente.setFornecedor(ponteFornecedor.findByIdFornecedor(categoriasDTO.getFornecedor()));

            categoriasExistente = this.ponteCategoria.save(categoriasExistente);

            return CategoriasDTO.of(categoriasExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void exportCategorias(HttpServletResponse response) throws Exception {
        Export export = new Export();

        export.exportPadrao(new String[]{"id", "cod_categoria", "nome_categoria", "fornecedor04", "razão social", "cnpj"}, response, "exportCategoria");


        for (Categorias linha : ponteCategoria.findAll()) {

            String cnpj = linha.getFornecedor().getCnpj();
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            String cnpjMask = mask.valueToString(cnpj);

            export.exportPadrao(new String[]{linha.getId().toString(), linha.getCodCategoria(),
                    linha.getNomeCategoria(), linha.getFornecedor().getId().toString(),
                    linha.getFornecedor().getRazao(), cnpjMask}, response, "exportCategoria");

        }

    }

    //import
    public List<Categorias> importCategorias(MultipartFile multipartFile) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();

        List<String[]> linhas = csvReader.readAll();
        List<Categorias> produtos = new ArrayList<>();
        for (String[] line : linhas) {
            try {
                String[] line2 = line[0].replaceAll("\"", "").split(";");

                Categorias clas_categorias = new Categorias();

                String cnpj = (line2[4]);

                clas_categorias.setFornecedor(ponteFornecedor.findByIdFornecedor(Long.parseLong(line2[3])));
                clas_categorias.setId(Long.parseLong(line2[0]));
                clas_categorias.setCodCategoria(line2[1]);
                clas_categorias.setNomeCategoria(line2[2]);

                cnpj.replaceAll("[^0-9]", "");

                produtos.add(clas_categorias);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ponteCategoria.saveAll(produtos);
    }


    public String codPronto(String cod) {
        cod = StringUtil.leftPad(cod, 3, "0");
        return cod;

    }

}