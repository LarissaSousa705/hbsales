package br.com.hbsis.produtos;



import br.com.hbsis.linhacategoria.LinhaCategoria;
import br.com.hbsis.linhacategoria.LinhaCategoriaService;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

//processamento da regra do negocio
@Service
public class ProdutosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);

    private final IProdutosRepository iProdutosRepository;
    private final LinhaCategoriaService linhaCategoriaService;

    public ProdutosService(IProdutosRepository iProdutosRepository, LinhaCategoriaService linhaCategoriaService) {
        this.iProdutosRepository = iProdutosRepository;
        this.linhaCategoriaService = linhaCategoriaService;
    }

    public ProdutosDTO save(ProdutosDTO produtosDTO) {
        this.validate(produtosDTO);

        LOGGER.info("Salvando Produtos");
        LOGGER.debug("Produtos: {}", produtosDTO);

        Produtos produtos = new Produtos();
        LinhaCategoria linhaCategoria = new LinhaCategoria();


        produtos.setNomeProduto(produtosDTO.getNomeProduto());
        produtos.setPreco(produtosDTO.getPreco());
        produtos.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
        produtos.setPesoUnidade(produtosDTO.getPesoUnidade());
        produtos.setValidade(produtosDTO.getValidade());
        produtos.setLinhaCategoria(linhaCategoriaService.findByIdLinhaCategoria(produtosDTO.getLinhaCategoria()));



        linhaCategoria = linhaCategoriaService.findByIdLinhaCategoria(produtosDTO.getLinhaCategoria());

        produtos = this.iProdutosRepository.save(produtos);


        return produtosDTO.of(produtos);
    }

    private void validate(ProdutosDTO produtosDTO) {

    LOGGER.info("Validando Produtos");

    if (produtosDTO == null){
        throw  new IllegalArgumentException("Produtos DTO não devem ser nulo/vazio");
    }

    if (StringUtils.isEmpty(produtosDTO.getNomeProduto())){
        throw new IllegalArgumentException("Nome produto nâo devem ser nulo/vazio");
        }
    }

    public ProdutosDTO finById(Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if (produtosOptional.isPresent()){
            return ProdutosDTO.of(produtosOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
    public Produtos findByIdProdutos(Long id){
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if (produtosOptional.isPresent()){
            return produtosOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe"));
    }

    public ProdutosDTO update(ProdutosDTO produtosDTO, Long id) {
        Optional<Produtos> produtosOptionalExistente = this.iProdutosRepository.findById(id);

        if (produtosOptionalExistente.isPresent()){
            Produtos produtosExistente = produtosOptionalExistente.get();
            LOGGER.info("Atualizando produtos... id[{}]", produtosExistente.getId());
            LOGGER.debug("Payload {}", produtosDTO);
            LOGGER.debug("Produtos Existentes: {}", produtosExistente);
            LinhaCategoria linhaCategoria = new LinhaCategoria();

            produtosExistente.setId(produtosDTO.getId());
            produtosExistente.setNomeProduto(produtosDTO.getNomeProduto());
            produtosExistente.setPreco(produtosDTO.getPreco());
            produtosExistente.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
            produtosExistente.setPesoUnidade(produtosDTO.getPesoUnidade());
            produtosExistente.setValidade(produtosDTO.getValidade());

            linhaCategoria = linhaCategoriaService.findByIdLinhaCategoria(produtosDTO.getId());




            return  produtosDTO.of(produtosExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para produtos de ID:[{}]", id);

        this.iProdutosRepository.deleteById(id);
    }

    //export
    public void findAllExport(HttpServletResponse response) throws Exception{
        String produtosCsv = "produtos.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename= \"" + produtosCsv + "\"");
        PrintWriter writer = response.getWriter();


        ICSVWriter icsvWriter = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String heardeCSV[] = {"id", "nome_produto", "preco", "linha_categoria_produto", "unidade_caixa", "peso_unidade", "validade"};
        icsvWriter.writeNext(heardeCSV);

        for (Produtos line : iProdutosRepository.findAll()){

            //DateTimeFormatter formatar = new DateTimeFormatter.ofPattern("dd/MM/yyyy");
           // String dataCerta = line.getValidade().for;
            icsvWriter.writeNext(new String[]{
                    line.getId().toString(),
                    line.getNomeProduto(),
                    String.valueOf(line.getPreco()),
                    String.valueOf(line.getLinhaCategoria()),
                    String.valueOf(line.getUnidadeCaixa()),
                    String.valueOf(line.getPesoUnidade()),
                    String.valueOf(line.getValidade())});
        }
    }
}