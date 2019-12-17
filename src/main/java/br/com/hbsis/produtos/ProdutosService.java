package br.com.hbsis.produtos;


import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.CategoriasDTO;
import br.com.hbsis.categoria.CategoriasService;
import br.com.hbsis.categoria.ICategoriasRepository;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import br.com.hbsis.linhacategoria.ILinhaCategoriaRepository;
import br.com.hbsis.linhacategoria.LinhaCategoria;
import br.com.hbsis.linhacategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhacategoria.LinhaCategoriaService;
import com.opencsv.*;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.generic.FieldOrMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//processamento da regra do negocio
@Service
public class ProdutosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);

    private final IProdutosRepository iProdutosRepository;
    private final LinhaCategoriaService linhaCategoriaService;
    private final ILinhaCategoriaRepository iLinhaCategoriaRepository;
    private final IFornecedorRepository iFornecedorRepository;
    private final ICategoriasRepository iCategoriasRepository;
    private final FornecedorService fornecedorService;
    private final CategoriasService categoriasService;
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");

    public ProdutosService(IProdutosRepository iProdutosRepository, LinhaCategoriaService linhaCategoriaService, ILinhaCategoriaRepository iLinhaCategoriaRepository, IFornecedorRepository iFornecedorRepository, ICategoriasRepository iCategoriasRepository, FornecedorService fornecedorService, CategoriasService categoriasService) {
        this.iProdutosRepository = iProdutosRepository;
        this.linhaCategoriaService = linhaCategoriaService;
        this.iLinhaCategoriaRepository = iLinhaCategoriaRepository;
        this.iFornecedorRepository = iFornecedorRepository;
        this.iCategoriasRepository = iCategoriasRepository;
        this.fornecedorService = fornecedorService;
        this.categoriasService = categoriasService;
    }

    public ProdutosDTO save(ProdutosDTO produtosDTO) {
        this.validate(produtosDTO);

        LOGGER.info("Salvando Produtos");
        LOGGER.debug("Produtos: {}", produtosDTO);

        Produtos produtos = new Produtos();
        LinhaCategoria linha9 = new LinhaCategoria();

        produtos.setNomeProduto(produtosDTO.getNomeProduto());
        produtos.setPreco(produtosDTO.getPreco());
        produtos.setUnidadeCaixa((long) produtosDTO.getUnidadeCaixa());
        produtos.setPesoUnidade(produtosDTO.getPesoUnidade());
        produtos.setValidade(produtosDTO.getValidade());
        produtos.setCodProduto(produtosDTO.getCodProduto());
        produtos.setMedidaPeso(produtosDTO.getMedidaPeso());
        produtos.setLinhaCategoriaProduto(linhaCategoriaService.findByIdLinhaCategoria(produtosDTO.getLinhaCategoriaProduto()));
        linha9 = linhaCategoriaService.findByIdLinhaCategoria(produtosDTO.getLinhaCategoriaProduto());
        produtos.setLinhaCategoriaProduto(linha9);

        if (produtos.getCodProduto().length() < 10) {
            String codPronto1 = produtos.getCodProduto();
            codPronto1 = StringUtil.leftPad(codPronto1, 10, "0").toUpperCase();
            produtos.setCodProduto(codPronto1);

        }
        produtos = this.iProdutosRepository.save(produtos);


        return produtosDTO.of(produtos);
    }


    private void validate(ProdutosDTO produtosDTO) {

        LOGGER.info("Validando Produtos");

        if (produtosDTO == null) {
            throw new IllegalArgumentException("Produtos DTO não devem ser nulo/vazio");
        }

        if (StringUtils.isEmpty(produtosDTO.getNomeProduto())) {
            throw new IllegalArgumentException("Nome produto nâo devem ser nulo/vazio");
        }
        if (StringUtils.isEmpty(produtosDTO.getMedidaPeso())) {
            throw new IllegalArgumentException("Medida de peso nâo devem ser nulo/vazio");
        }
        switch (produtosDTO.getMedidaPeso()) {
            case "Kg":
            case "mg":
            case "g":
                break;
            default:
                throw new IllegalArgumentException("Medida de peso nâo pode ser diferente de: kg,mg,g...");
        }

    }

    public ProdutosDTO finById(Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if (produtosOptional.isPresent()) {
            return ProdutosDTO.of(produtosOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public ProdutosDTO findByCodDTO(String cod) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findByCodProduto(cod);

        if (produtosOptional.isPresent()) {
            return ProdutosDTO.of(produtosOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", cod));
    }



    public Produtos findByCod(String cod) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findByCodProduto(cod);

        if (produtosOptional.isPresent()) {
            return produtosOptional.get();
        }
        throw new IllegalArgumentException("Cod %s não existe");
    }

    public ProdutosDTO update(ProdutosDTO produtosDTO, Long id) {
        Optional<Produtos> produtosOptionalExistente = this.iProdutosRepository.findById(id);


        if (produtosOptionalExistente.isPresent()) {
            Produtos produtosExistente = produtosOptionalExistente.get();
            LOGGER.info("Atualizando produtos... id[{}]", produtosExistente.getId());
            LOGGER.debug("Payload {}", produtosDTO);
            LOGGER.debug("Produtos Existentes: {}", produtosExistente);
            LinhaCategoria linhaCategoria = new LinhaCategoria();

            produtosExistente.setNomeProduto(produtosDTO.getNomeProduto());
            produtosExistente.setPreco(produtosDTO.getPreco());
            produtosExistente.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
            produtosExistente.setPesoUnidade(produtosDTO.getPesoUnidade());
            produtosExistente.setValidade(produtosDTO.getValidade());
            produtosExistente.setCodProduto(produtosDTO.getCodProduto());
            produtosExistente.setMedidaPeso(produtosDTO.getMedidaPeso());
            produtosExistente.setLinhaCategoriaProduto(linhaCategoriaService.findByIdLinhaCategoria(produtosDTO.getLinhaCategoriaProduto()));

            produtosExistente = this.iProdutosRepository.save(produtosExistente);

            return produtosDTO.of(produtosExistente);


        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para produtos de ID:[{}]", id);

        this.iProdutosRepository.deleteById(id);
    }

    //export
    public void findAllExport96(HttpServletResponse response) throws Exception {
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

        String heardeCSV[] = {"id","codProduto", "nome_produto",
                "preco", "unidadeCaixa",
                "pesoMedida", "validade",
                "codLinhaCategoria", "categoriaLinha",
                "codCategoria", "nomeCategoria",
                "cnpj", "razaoSocial"};
        icsvWriter.writeNext(heardeCSV);

        for (Produtos line : iProdutosRepository.findAll()) {
            Fornecedor fornecedor = new Fornecedor();

            //peso e medida
            String pesoMedida = line.getPesoUnidade() + line.getMedidaPeso();

            String preco2 = "R$" + line.getPreco();

            //formatação cnpj
            String cnpj8 = line.getLinhaCategoriaProduto().getIdCategoriaProdutos().getFornecedor04().getCnpj();
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            String cnpjMask = mask.valueToString(cnpj8);

            icsvWriter.writeNext(new String[]{
                    String.valueOf(line.getId()),
                    "\"" + line.getCodProduto(),
                    line.getNomeProduto(),
                    preco2,
                    String.valueOf(line.getUnidadeCaixa()),
                    pesoMedida,
                    simpleDateFormat1.format(line.getValidade()),
                    "\"" + line.getLinhaCategoriaProduto().getCodLinhaCategoria(),
                    line.getLinhaCategoriaProduto().getCategoriaLinha(),
                    "\"" + line.getLinhaCategoriaProduto().getIdCategoriaProdutos().getCodCategoria(),
                    line.getLinhaCategoriaProduto().getIdCategoriaProdutos().getNomeCategoria(),
                    cnpjMask,
                    line.getLinhaCategoriaProduto().getIdCategoriaProdutos().getFornecedor04().getRazao()
            });
        }
    }


    //import
    public List<Produtos> readAllImport(MultipartFile multipartFileImport) throws Exception {
        InputStreamReader inputStreamReaderImport = new InputStreamReader(multipartFileImport.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReaderImport).withSkipLines(1).build();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String[]> linhas = csvReader.readAll();
        List<Produtos> produtos = new ArrayList<>();

        for (String[] line : linhas) {
            try {
                String[] line2 = line[0].replaceAll("\"", "").split(";");
                Produtos classProdutos = new Produtos();
                LinhaCategoria classLinhaCategoria = new LinhaCategoria();
                Fornecedor classFornecedor = new Fornecedor();
                Categorias classCategoria = new Categorias();

                classProdutos.setNomeProduto(line2[1]);
                classProdutos.setUnidadeCaixa(Long.parseLong(line2[3]));
                classProdutos.setValidade(sdf.parse(sdf.format(simpleDateFormat1.parse(line2[5]))));
                classLinhaCategoria.setCategoriaLinha(line2[7]);
                classCategoria.setCodCategoria(line2[8]);
                classCategoria.setNomeCategoria(line2[9]);
                classFornecedor.setCnpj(line2[10]);
                classFornecedor.setRazao(line2[11]);

                String preco2 = (line2[2]);
                String peso = (line2[4]);
                String codProduto = (line2[0]);
                String medidaPeso = (line2[4]);
                String codCategoriaLinha55 = (line2[6]);

                String a = peso.replaceAll("[^0-9]", "");
                String b = medidaPeso.replaceAll("[0-9.]", "");
                String c = preco2.replaceAll("[^0-9.]", "");
                String d = codProduto.replaceAll("[^0-9]", "");
                String e = codCategoriaLinha55.replaceAll("[^0-9]", "");
                classLinhaCategoria = linhaCategoriaService.findByCodLinha(e);


                classProdutos.setPesoUnidade(Double.parseDouble(a));
                classProdutos.setMedidaPeso(b);
                classProdutos.setPreco(Double.parseDouble(c));
                classProdutos.setCodProduto(d);


                classProdutos.setLinhaCategoriaProduto(classLinhaCategoria);

                produtos.add(classProdutos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iProdutosRepository.saveAll(produtos);
    }

    //import planilha de produtos por fornecedor
    public void findByIdsla(MultipartFile multipartFile, Long id) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();
        SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");

        List<String[]> linhas = csvReader.readAll();

        for (String[] line : linhas) {
            String[] line2 = line[0].replaceAll("\"", "").split(";");
            try {
                if (iFornecedorRepository.existsById(id)) {
                    System.out.println(id);
                    Produtos classProdutos = new Produtos();
                    ProdutosDTO classProdutosDTO = new ProdutosDTO();
                    LinhaCategoria classLinhaCategoria = new LinhaCategoria();
                    Fornecedor classFornecedor = new Fornecedor();
                    CategoriasDTO classCategoriaDTO = new CategoriasDTO();
                    Categorias classCategorias =new Categorias();
                    LinhaCategoriaDTO classLinhaCategoriaDTO = new LinhaCategoriaDTO();

                    String idP = (line2[0]);
                    String cod1 = line2[1].replaceAll("\"", "");
                    String nomeProduto = line2[2];
                    String preco = line2[3].replaceAll("[^0-9.]", "");
                    String unidade = line2[4];
                    String peso = line2[5].replaceAll("[^0-9.]", "");
                    String medida = line2[5].replaceAll("[0-9.]", "");
                    Date validade = sdf.parse(sdf.format(simpleDateFormat1.parse(line2[6])));
                    String codLinhaCategoria = line2[7];
                    String categoriaLinha = line2[8];
                    String codCategoria = line2[9];
                    String nomeCategoria = line2[10];
                    String cnpj = line2[11].replaceAll("[^0-9]", "");
                    String razao = line2[12];

                    classLinhaCategoria = linhaCategoriaService.findByCodLinha(codLinhaCategoria);

                    classProdutos.setCodProduto(cod1);
                    classProdutos.setCodProduto(cod1);
                    classProdutos.setNomeProduto(nomeProduto);
                    classProdutos.setUnidadeCaixa(Long.parseLong(unidade));
                    classProdutos.setValidade(validade);
                    classProdutos.setPreco(Double.parseDouble(preco));
                    classProdutos.setPesoUnidade(Double.parseDouble(peso));
                    classProdutos.setMedidaPeso(medida);
                    classProdutos.setLinhaCategoriaProduto(classLinhaCategoria);

                    if (!iProdutosRepository.findByCodProduto(cod1).isPresent()) {
                        classProdutosDTO.setId(Long.parseLong(idP));
                        classProdutosDTO.setCodProduto(cod1);
                        classProdutosDTO.setNomeProduto(nomeProduto);
                        classProdutosDTO.setUnidadeCaixa(Long.parseLong(unidade));
                        classProdutosDTO.setValidade(validade);
                        classProdutosDTO.setPreco(Double.parseDouble(preco));
                        classProdutosDTO.setPesoUnidade(Double.parseDouble(peso));
                        classProdutosDTO.setMedidaPeso(medida);
                        classProdutosDTO.setLinhaCategoriaProduto(Long.parseLong(codLinhaCategoria));

                        save(classProdutosDTO);
                    } else if (iProdutosRepository.findByCodProduto(cod1).isPresent()) {
                        classProdutosDTO.setId(findByCod(classProdutos.getCodProduto()).getId());
                        update(ProdutosDTO.of(classProdutos), classProdutosDTO.getId());
                    }


                    if (!iCategoriasRepository.findByCodCategoria(codCategoria).isPresent()){

                        classFornecedor = iFornecedorRepository.findByCnpj(cnpj);
                        classCategoriaDTO.setFornecedor04(classFornecedor.getId());
                        classCategoriaDTO.setCodCategoria(codCategoria);
                        classCategoriaDTO.setNomeCategoria(nomeCategoria);
                        classCategoriaDTO.setId(id);

                        categoriasService.save(classCategoriaDTO);
                    }else if (iCategoriasRepository.existsById(id)){

                        classFornecedor = iFornecedorRepository.findByCnpj(cnpj);
                        classCategoriaDTO.setFornecedor04(classFornecedor.getId());
                        classCategoriaDTO.setCodCategoria(codCategoria);
                        classCategoriaDTO.setNomeCategoria(nomeCategoria);
                        classCategoriaDTO.setId(classCategoriaDTO.getId());
                        classCategorias = categoriasService.findByCodCategoria(codCategoria);

                        categoriasService.update(classCategoriaDTO, classCategorias.getId());

                    }
                    Long idg = classCategoriaDTO.getId();
                    if (!iLinhaCategoriaRepository.existsByCodLinhaCategoria(codLinhaCategoria)){
                        classLinhaCategoriaDTO.setCodLinhaCategoria(codLinhaCategoria);
                        classLinhaCategoriaDTO.setCategoriaLinha(categoriaLinha);
                        classCategorias = categoriasService.findByCodCategoria(codCategoria);
                        classLinhaCategoriaDTO.setIdCategoriaProdutos(classCategorias.getId());

                        linhaCategoriaService.save(classLinhaCategoriaDTO);
                    }else if (iLinhaCategoriaRepository.existsByCodLinhaCategoria(codLinhaCategoria)){
                        classLinhaCategoriaDTO.setCodLinhaCategoria(codLinhaCategoria);
                        classLinhaCategoriaDTO.setCategoriaLinha(categoriaLinha);
                        classCategorias = categoriasService.findByCodCategoria(codCategoria);
                        classLinhaCategoriaDTO.setIdCategoriaProdutos(classCategorias.getId());

                        linhaCategoriaService.update(classLinhaCategoriaDTO, classLinhaCategoria.getId());

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
