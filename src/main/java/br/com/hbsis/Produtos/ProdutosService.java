package br.com.hbsis.Produtos;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.Fornecedor.FornecedorDTO;
import br.com.hbsis.Fornecedor.FornecedorService;
import com.opencsv.*;
import org.apache.commons.lang.StringUtils;
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

/**
 * Classe responsável pelo processamento da regra de negócio
 */
@Service
public class ProdutosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosService.class);

    private final IProdutosRepository iProdutosRepository;
    private final FornecedorService fornecedorService;

    public ProdutosService(IProdutosRepository iProdutosRepository, FornecedorService fornecedorService) {
        this.iProdutosRepository = iProdutosRepository;
        this.fornecedorService = fornecedorService;

    }

    public ProdutosDTO save(ProdutosDTO ProdutosDTO) {

        this.validate(ProdutosDTO);

        LOGGER.info("Salvando produtos");
        LOGGER.debug("br.com.hbsis.Produtos: {}", ProdutosDTO);

        Produtos Produtos = new Produtos();
        Produtos.setNome_categoria(ProdutosDTO.getNome_categoria());
        Produtos.setCod_categoria(ProdutosDTO.getCod_categoria());
        Produtos.setFornecedor_id(ProdutosDTO.getFornecedor_id());

        Produtos = this.iProdutosRepository.save(Produtos);

        return ProdutosDTO.of(Produtos);

    }

    private void validate(ProdutosDTO ProdutosDTO) {
        LOGGER.info("Validando Produtos");

        if (ProdutosDTO == null) {
            throw new IllegalArgumentException("ProdutosDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(ProdutosDTO.getNome_categoria())) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nulo/vazio");
        }
    }

    public ProdutosDTO findById(Long id) {
        Optional<Produtos> ProdutosOptional = this.iProdutosRepository.findById(id);

        if (ProdutosOptional.isPresent()) {
            return ProdutosDTO.of(ProdutosOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public ProdutosDTO update(ProdutosDTO ProdutosDTO, Long id) {
        Optional<Produtos> ProdutosExistenteOptional = this.iProdutosRepository.findById(id);

        if (ProdutosExistenteOptional.isPresent()) {
            Produtos ProdutosExistente = ProdutosExistenteOptional.get();

            LOGGER.info("Atualizando produtos... id: [{}]", ProdutosExistente.getId());
            LOGGER.debug("Payload: {}", ProdutosDTO);
            LOGGER.debug("br.com.hbsis.Produtos Existente: {}", ProdutosExistente);

            ProdutosExistente.setFornecedor_id(ProdutosDTO.getFornecedor_id());
            ProdutosExistente.setCod_categoria(ProdutosDTO.getCod_categoria());
            ProdutosExistente.setNome_categoria(ProdutosDTO.getNome_categoria());

            ProdutosExistente = this.iProdutosRepository.save(ProdutosExistente);

            return ProdutosDTO.of(ProdutosExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produtos de ID: [{}]", id);

        this.iProdutosRepository.deleteById(id);
    }


    public void findAll(HttpServletResponse response) throws Exception {
        String produtos= "produtos.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + produtos + "\"");
        PrintWriter writer = response.getWriter();

        ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCVS[] = {"id","cod_categoria" , "nome_categoria" ,"fornecedor_id" };
        csvWriter.writeNext(headerCVS);

        for(Produtos linha : iProdutosRepository.findAll()) {
            csvWriter.writeNext(new String[]{ linha.getId().toString(), linha.getCod_categoria().toString(), linha.getNome_categoria(), linha.getFornecedor_id().toString()});
        }
    }

    public List<Produtos> readAll(MultipartFile multipartFile) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();

        List<String[]> linhas = csvReader.readAll();
        List<Produtos> produtos = new ArrayList<>();
        for (String[] line : linhas) {
            try {
                String[] line2 = line[0].replaceAll("\"", "").split(";");

                Produtos clas_produtos = new Produtos();
                Fornecedor fornecedor = new Fornecedor();
                FornecedorDTO fornecedorDTO = new FornecedorDTO();

                clas_produtos.setId(Long.parseLong(line2[0]));
                clas_produtos.setCod_categoria(Long.parseLong(line2[1]));
                clas_produtos.setNome_categoria(line2[2]);
               clas_produtos.setFornecedor_id(Long.parseLong(line2[3]));

                produtos.add(clas_produtos);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iProdutosRepository.saveAll(produtos);
    }
}