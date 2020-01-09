package br.com.hbsis.produtos;


import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.categoria.CategoriasDTO;
import br.com.hbsis.categoria.CategoriasService;
import br.com.hbsis.categoria.PonteCategoria;
import br.com.hbsis.export.Export;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.PonteFornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.PonteFuncionario;
import br.com.hbsis.itens.Itens;
import br.com.hbsis.itens.PonteItens;
import br.com.hbsis.linhacategoria.LinhaCategoria;
import br.com.hbsis.linhacategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhacategoria.LinhaCategoriaService;
import br.com.hbsis.linhacategoria.PonteLinhaCategoria;
import br.com.hbsis.pedidos.PontePedidos;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//processamento da regra do negocio
@Service
public class ProdutosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);

    private final PonteProdutos ponteProdutos;
    private final PonteLinhaCategoria ponteLinhaCategoria;
    private final PonteFornecedor ponteFornecedor;
    private final PonteCategoria ponteCategoria;
    private final FornecedorService fornecedorService;
    private final CategoriasService categoriasService;
    private final LinhaCategoriaService linhaCategoriaService;
    private final PontePedidos pontePedidos;
    private final PonteFuncionario ponteFuncionario;
    private final PonteItens ponteItens;
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");

    public ProdutosService(PonteProdutos ponteProdutos, PonteLinhaCategoria ponteLinhaCategoria, PonteFornecedor ponteFornecedor, PonteCategoria ponteCategoria, FornecedorService fornecedorService, CategoriasService categoriasService, LinhaCategoriaService linhaCategoriaService, PontePedidos pontePedidos, PonteFuncionario ponteFuncionario, PonteItens ponteItens) {
        this.ponteProdutos = ponteProdutos;
        this.ponteLinhaCategoria = ponteLinhaCategoria;
        this.ponteFornecedor = ponteFornecedor;
        this.ponteCategoria = ponteCategoria;
        this.fornecedorService = fornecedorService;
        this.categoriasService = categoriasService;
        this.linhaCategoriaService = linhaCategoriaService;
        this.pontePedidos = pontePedidos;
        this.ponteFuncionario = ponteFuncionario;
        this.ponteItens = ponteItens;
    }


    public ProdutosDTO save(ProdutosDTO produtosDTO) {
        this.validate(produtosDTO);

        LOGGER.info("Salvando Produtos");
        LOGGER.debug("Produtos: {}", produtosDTO);

        Produtos produtos = new Produtos();
        LinhaCategoria linha9;

        produtos.setNomeProduto(produtosDTO.getNomeProduto());
        produtos.setPreco(produtosDTO.getPreco());
        produtos.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
        produtos.setPesoUnidade(produtosDTO.getPesoUnidade());
        produtos.setValidade(produtosDTO.getValidade());
        produtos.setCodProduto(produtosDTO.getCodProduto());
        produtos.setMedidaPeso(produtosDTO.getMedidaPeso());
        produtos.setIdFornecedor(ponteFornecedor.findByIdFornecedor(produtosDTO.getIdFornecedor()));
        produtos.setLinhaCategoriaProduto(ponteLinhaCategoria.findByIdLinhaCategoria(produtosDTO.getLinhaCategoriaProduto()));
        linha9 = ponteLinhaCategoria.findByIdLinhaCategoria(produtosDTO.getLinhaCategoriaProduto());
        produtos.setLinhaCategoriaProduto(linha9);

        if (produtos.getCodProduto().length() < 10) {
            String codPronto = produtos.getCodProduto();
            codPronto = StringUtil.leftPad(codPronto, 10, "0").toUpperCase();
            produtos.setCodProduto(codPronto);

        }
        produtos = this.ponteProdutos.save(produtos);
        return ProdutosDTO.of(produtos);
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
            case "kg":
            case "mg":
            case "g":
                break;
            default:
                throw new IllegalArgumentException("Medida de peso nâo pode ser diferente de: kg,mg,g...");
        }
    }

    public ProdutosDTO update(ProdutosDTO produtosDTO, Long id) {
        Optional<Produtos> produtosOptionalExistente = this.ponteProdutos.findById(id);

        if (produtosOptionalExistente.isPresent()) {
            Produtos produtosExistente = produtosOptionalExistente.get();
            LOGGER.info("Atualizando produtos... id[{}]", produtosExistente.getId());
            LOGGER.debug("Payload {}", produtosDTO);
            LOGGER.debug("Produtos Existentes: {}", produtosExistente);

            produtosExistente.setNomeProduto(produtosDTO.getNomeProduto());
            produtosExistente.setPreco(produtosDTO.getPreco());
            produtosExistente.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
            produtosExistente.setPesoUnidade(produtosDTO.getPesoUnidade());
            produtosExistente.setValidade(produtosDTO.getValidade());
            produtosExistente.setCodProduto(produtosDTO.getCodProduto());
            produtosExistente.setMedidaPeso(produtosDTO.getMedidaPeso());
            produtosExistente.setIdFornecedor(ponteFornecedor.findByIdFornecedor(produtosDTO.getIdFornecedor()));
            produtosExistente.setLinhaCategoriaProduto(ponteLinhaCategoria.findByIdLinhaCategoria(produtosDTO.getLinhaCategoriaProduto()));

            produtosExistente = this.ponteProdutos.save(produtosExistente);

            return ProdutosDTO.of(produtosExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para produtos de ID:[{}]", id);

        this.ponteProdutos.deleteById(id);
    }
    public void exportProduto(HttpServletResponse response) throws Exception {
        Export export = new Export();

        export.exportPadrao(new String[]{"id", "codProduto", "nome_produto",
                "preco", "unidadeCaixa",
                "pesoMedida", "validade",
                "codLinhaCategoria", "categoriaLinha",
                "codCategoria", "nomeCategoria",
                "cnpj", "razaoSocial"}, response, "exportProdutos");


        for (Produtos line : ponteProdutos.findAll()) {

            String pesoMedida = line.getPesoUnidade() + line.getMedidaPeso();

            String preco2 = "R$" + line.getPreco();

            String cnpj8 = line.getLinhaCategoriaProduto().getIdCategoriaProdutos().getFornecedor().getCnpj();
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            String cnpjMask = mask.valueToString(cnpj8);

            export.exportPadrao(new String[]{
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
                    line.getLinhaCategoriaProduto().getIdCategoriaProdutos().getFornecedor().getRazao()}, response, "exportProdutos");
        }
    }


    public List<Produtos> importProduto(MultipartFile multipartFileImport) throws Exception {
        InputStreamReader inputStreamReaderImport = new InputStreamReader(multipartFileImport.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReaderImport).withSkipLines(1).build();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String[]> linhas = csvReader.readAll();
        List<Produtos> produtosList = new ArrayList<>();

        for (String[] line : linhas) {
            try {
                String[] line2 = line[0].replaceAll("\"", "").split(";");
                Produtos classProdutos = new Produtos();
                LinhaCategoria classLinhaCategoria = new LinhaCategoria();
                Fornecedor classFornecedor = new Fornecedor();
                Categorias classCategoria = new Categorias();

                classProdutos.setNomeProduto(line2[2]);
                classProdutos.setUnidadeCaixa(Long.parseLong(line2[4]));
                classProdutos.setValidade(sdf.parse(sdf.format(simpleDateFormat1.parse(line2[6]))));
                classLinhaCategoria.setCategoriaLinha(line2[8]);
                classCategoria.setCodCategoria(line2[9]);
                classCategoria.setNomeCategoria(line2[10]);
                classFornecedor.setCnpj(line2[11]);
                classFornecedor.setRazao(line2[12]);

                String preco2 = (line2[3]);
                String peso = (line2[5]);
                String codProduto = (line2[1]);
                String medidaPeso = (line2[5]);
                String codCategoriaLinha55 = (line2[7]);

                String a = peso.replaceAll("[^0-9]", "");
                String b = medidaPeso.replaceAll("[0-9.]", "");
                String c = preco2.replaceAll("[^0-9.]", "");
                String d = codProduto.replaceAll("[^0-9]", "");
                String e = codCategoriaLinha55.replaceAll("[^0-9]", "");
                classLinhaCategoria = ponteLinhaCategoria.findByCodLinha(e);

                classProdutos.setPesoUnidade(Double.parseDouble(a));
                classProdutos.setMedidaPeso(b);
                classProdutos.setPreco(Double.parseDouble(c));
                classProdutos.setCodProduto(d);
                classProdutos.setLinhaCategoriaProduto(classLinhaCategoria);

                produtosList.add(classProdutos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ponteProdutos.saveAll(produtosList);
    }

    public void importProdutoFornecedor(MultipartFile multipartFile, Long id) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();
        SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");

        List<String[]> linhas = csvReader.readAll();

        for (String[] line : linhas) {
            String[] line2 = line[0].replaceAll("\"", "").split(";");
            try {
                if (ponteFornecedor.existsById(id)) {
                    System.out.println(id);
                    Produtos produtos = new Produtos();
                    ProdutosDTO produtosDTO = new ProdutosDTO();
                    LinhaCategoria linhaCategoria;
                    CategoriasDTO categoriasDTO = new CategoriasDTO();
                    Categorias categorias ;
                    LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO();
                    Fornecedor fornecedor = new Fornecedor();

                    String idP = (line2[0]);
                    String cod1 = line2[1].replaceAll("\"", "");
                    String nomeProduto = line2[2];
                    String preco = line2[3].replaceAll("[^0-9.]", "");
                    String unidade = line2[4];
                    String peso = line2[5].replaceAll("[^0-9.]", "");
                    String medida = line2[5].replaceAll("[0-9.]", "");
                    Date validade = sdf.parse(sdf.format(simpleDateFormat1.parse(line2[6])));
                    String codLinhaCategoria = line2[7].replaceAll("\"", "");
                    String categoriaLinha = line2[8];
                    String codCategoria = line2[9].replaceAll("\"", "");
                    String nomeCategoria = line2[10];
                    String cnpj = line2[11].replaceAll("[^0-9]", "");
                    String razao = line2[12];

                    String cnpjPronto = cnpj.substring(10, 14);

                    String codCategoria2 = codCategoria.substring(7, 10);

                    String codPronto = "CAT" + cnpjPronto + codCategoria2;

                    linhaCategoria = ponteLinhaCategoria.findByCodLinha(codLinhaCategoria);

                    produtos.setCodProduto(cod1);
                    produtos.setNomeProduto(nomeProduto);
                    produtos.setUnidadeCaixa(Long.parseLong(unidade));
                    produtos.setValidade(validade);
                    produtos.setPreco(Double.parseDouble(preco));
                    produtos.setPesoUnidade(Double.parseDouble(peso));
                    produtos.setMedidaPeso(medida);
                    produtos.setLinhaCategoriaProduto(linhaCategoria);

                    if (ponteFornecedor.existsById(id)) {
                        String nomeFantasia = ponteFornecedor.findByIdFornecedor(id).getNomeFantasia();
                        String endereco = ponteFornecedor.findByIdFornecedor(id).getEndereco();
                        String telefone = ponteFornecedor.findByIdFornecedor(id).getTelefone();
                        String email = ponteFornecedor.findByIdFornecedor(id).getEmail();
                        fornecedor.setEmail(email);
                        fornecedor.setCnpj(cnpj);
                        fornecedor.setRazao(razao);
                        fornecedor.setNomeFantasia(nomeFantasia);
                        fornecedor.setEndereco(endereco);
                        fornecedor.setTelefone(telefone);

                        fornecedorService.update(FornecedorDTO.of(fornecedor), id);
                    }
                    if (!ponteCategoria.findByCodC(codCategoria).isPresent()) {

                        String idFornecedor = ponteFornecedor.findByIdFornecedor(id).getId().toString();
                        categoriasDTO.setFornecedor(Long.parseLong(idFornecedor));
                        categoriasDTO.setCodCategoria(codCategoria);
                        categoriasDTO.setNomeCategoria(nomeCategoria);

                        categoriasService.save(categoriasDTO);

                    } else if (ponteCategoria.existsById(id)) {

                        String idFornecedor = ponteFornecedor.findByIdFornecedor(id).getId().toString();
                        categoriasDTO.setFornecedor(Long.parseLong(idFornecedor));
                        categoriasDTO.setCodCategoria(codPronto);
                        categoriasDTO.setNomeCategoria(nomeCategoria);
                        categoriasDTO.setId(categoriasDTO.getId());
                        categorias = ponteCategoria.findByCod(codCategoria);

                        categoriasService.update(categoriasDTO, categorias.getId());
                    }
                    if (!ponteLinhaCategoria.existsByCodLinhaCategoria(codLinhaCategoria)) {
                        linhaCategoriaDTO.setCodLinhaCategoria(codLinhaCategoria);
                        linhaCategoriaDTO.setCategoriaLinha(categoriaLinha);
                        categorias = ponteCategoria.findByCod(codCategoria);
                        linhaCategoriaDTO.setIdCategoriaProdutos(categorias.getId());
                        linhaCategoriaService.save(linhaCategoriaDTO);

                    } else if (ponteLinhaCategoria.existsByCodLinhaCategoria(codLinhaCategoria)) {
                        linhaCategoriaDTO.setCodLinhaCategoria(codLinhaCategoria);
                        linhaCategoriaDTO.setCategoriaLinha(categoriaLinha);
                        categorias = ponteCategoria.findByCod(codCategoria);
                        linhaCategoriaDTO.setIdCategoriaProdutos(categorias.getId());
                        linhaCategoria = ponteLinhaCategoria.findByCodLinha(codLinhaCategoria);


                      linhaCategoriaService.update(linhaCategoriaDTO, linhaCategoria.getId());
                    }

                    if (!ponteProdutos.findByCodProduto(cod1).isPresent()) {
                        produtosDTO.setId(Long.parseLong(idP));
                        produtosDTO.setCodProduto(cod1);
                        produtosDTO.setNomeProduto(nomeProduto);
                        produtosDTO.setUnidadeCaixa(Long.parseLong(unidade));
                        produtosDTO.setValidade(validade);
                        produtosDTO.setPreco(Double.parseDouble(preco));
                        produtosDTO.setPesoUnidade(Double.parseDouble(peso));
                        produtosDTO.setMedidaPeso(medida);
                        produtosDTO.setLinhaCategoriaProduto(linhaCategoria.getId());


                        save(produtosDTO);
                    } else if (ponteProdutos.findByCodProduto(cod1).isPresent()) {
                        produtosDTO.setId(ponteProdutos.findByCod(produtos.getCodProduto()).getId());
                        update(ProdutosDTO.of(produtos), produtosDTO.getId());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void exportProdutos(HttpServletResponse response, Long id, Long id2) throws Exception{
        Export export = new Export();

        Produtos produtos = ponteProdutos.findByIdProdutos(id);
        if (produtos.getId().toString().equals(id.toString())) {
            Funcionario funcionario = ponteFuncionario.findByIdFuncionario(id2);
            String nomeFuncionario = funcionario.getNome();
            String cnpj = produtos.getIdFornecedor().getCnpj();
            String razao = produtos.getIdFornecedor().getRazao();

            String razaoCnpj = razao + " - " + cnpj;

            export.exportPadrao(new String[]{"nome_funcionario", "nome_produto", "quantidade", "razaoCnpj"}, response, "exportProdutos");

            for (Itens itens : ponteItens.findAll()) {
                export.exportPadrao(new String[]{nomeFuncionario, produtos.getNomeProduto(), String.valueOf(itens.getQuantidade()), razaoCnpj}, response, "exportProdutos");

            }
        }else {
            throw new IllegalArgumentException("Erro na exportação de produtos");
        }
    }
}
