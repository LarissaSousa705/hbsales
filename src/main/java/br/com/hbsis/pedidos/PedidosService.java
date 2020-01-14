package br.com.hbsis.pedidos;

import br.com.hbsis.export.Export;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.PonteFuncionario;
import br.com.hbsis.itens.Itens;
import br.com.hbsis.itens.ItensDTO;
import br.com.hbsis.itens.PonteItens;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import br.com.hbsis.periodoVendas.PontePeriodoVendas;
import br.com.hbsis.produtos.PonteProdutos;
import br.com.hbsis.produtos.Produtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//regra de negocios
@Service
public class PedidosService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Pedidos.class);
    private final PontePedidos pontePedidos;
    private final PonteProdutos ponteProdutos;
    private final PontePeriodoVendas pontePeriodoVendas;
    private final PonteItens ponteItens;
    private final PonteFuncionario ponteFuncionario;
    private JavaMailSender javaMailSender;

    @Autowired
    public PedidosService(PontePedidos pontePedidos, PonteProdutos ponteProdutos, PontePeriodoVendas pontePeriodoVendas, PonteItens ponteItens, PonteFuncionario ponteFuncionario, JavaMailSender javaMailSender) {
        this.pontePedidos = pontePedidos;
        this.ponteProdutos = ponteProdutos;
        this.pontePeriodoVendas = pontePeriodoVendas;
        this.ponteItens = ponteItens;
        this.ponteFuncionario = ponteFuncionario;
        this.javaMailSender = javaMailSender;
    }


    public PedidosDTO save(PedidosDTO pedidosDTO) {
        this.validate(pedidosDTO);
        LOGGER.info("Salvando pedidos");
        LOGGER.debug("br.com.hbsis.Pedidos:{}", pedidosDTO);

        Pedidos pedidos = new Pedidos();
        pedidos.setId(pedidosDTO.getId());
        pedidos.setCodPedido(pedidosDTO.getCodPedido());
        pedidos.setStatus(pedidosDTO.getStatus());
        pedidos.setFuncionario(ponteFuncionario.findByIdFuncionario(pedidosDTO.getFuncionario()));
        pedidos.setPeriodoVendas(pontePeriodoVendas.findByPeriodo(pedidosDTO.getPeriodoVendas()));
        pedidos.setDataPedido(pedidosDTO.getDataPedido());

        if (validacaoAPI(pedidos.getPeriodoVendas().getPeriodoVendasFornecedor().getCnpj(), pedidos.getFuncionario().getUuidApi(),
                convercaoItens(pedidosDTO.getItensDTOS(), pedidos), valorTotal(pedidosDTO.getItensDTOS()))) {

            pedidos = this.pontePedidos.save(pedidos);

            List<Itens> itensList = new ArrayList<>();
            pedidos.setItensList(convercaoItens(pedidosDTO.getItensDTOS(), pedidos));

            for (Itens itens1 : pedidos.getItensList()) {
                ponteItens.save(itens1);
                itensList.add(itens1);

            }
            this.email(pedidos, pedidos.getItensList());
        } else {
            throw new IllegalArgumentException("Erro na validção da API");
        }
        return PedidosDTO.of(pedidos);
    }

    private void validate(PedidosDTO pedidosDTO) {
        LOGGER.info("Validando Pedidos");

        if (pedidosDTO == null) {
            throw new IllegalArgumentException("Pedidos não devem ser nulo");
        }

        switch (pedidosDTO.getStatus()) {
            case "ativo":
            case "cancelado":
            case "retirado":
                break;
            default:
                throw new IllegalArgumentException("Status de pedido não pode ser diferente de: ativo, cancelado, retirado");
        }
        for (ItensDTO itensDTO : pedidosDTO.getItensDTOS()) {
            Fornecedor fornecedorCompleto;
            fornecedorCompleto = pontePeriodoVendas.findByPeriodo(pedidosDTO.getPeriodoVendas()).getPeriodoVendasFornecedor();
            Produtos produtosCompleto = ponteProdutos.findByIdProdutos(itensDTO.getProdutos());

            this.validacaoFornecedor(produtosCompleto, fornecedorCompleto);
        }
        if (pedidosDTO.getDataPedido() == null) {
            throw new IllegalArgumentException("Data de Pedido não deve ser nulo/vazio");
        } else {
            this.validacaoDataPedido(pedidosDTO);
        }

    }

    private void validacaoDataPedido(PedidosDTO pedidosDTO) {
        LOGGER.info("Validando se existe período de vendas para esse pedido...");
        PeriodoVendas periodoVendasCompleto;
        periodoVendasCompleto = pontePeriodoVendas.findByPeriodo(pedidosDTO.getPeriodoVendas());

        LocalDateTime dataInicio1 = periodoVendasCompleto.getDataInicio();
        LocalDateTime dataFim1 = periodoVendasCompleto.getDataFim();
        LocalDateTime dataPedido = pedidosDTO.getDataPedido();

        if (periodoVendasCompleto.getId() == null) {
            throw new IllegalArgumentException("Esse período não existe");
        } else if (!dataPedido.isAfter(dataInicio1) ||!dataPedido.equals(dataInicio1) ) {
            throw new IllegalArgumentException("Esse período ainda não começou...");
        } else if (!dataPedido.isBefore(dataFim1) || !dataPedido.equals(dataFim1)) {
            throw new IllegalArgumentException("Esse período já terminou...");
        }
    }


    private void validacaoFornecedor(Produtos produtos, Fornecedor fornecedor) {
        LOGGER.info("Validando Produtos por Fornecedor");

        String idFornecedorP = produtos.getLinhaCategoriaProduto().getIdCategoriaProdutos().getFornecedor().getId().toString();

        if (!idFornecedorP.equals(fornecedor.getId().toString())) {
            throw new IllegalArgumentException("Fornecedor dos produtos são diferentes");
        }
    }

    public PedidosDTO update(PedidosDTO pedidosDTO, Long id) {
        Optional<Pedidos> pedidosOptional = this.pontePedidos.findById(id);

        if (pedidosOptional.isPresent()) {
            Pedidos pedidos = new Pedidos();
            LOGGER.info("Atualizando Pedidos... id[{}]", pedidos.getId());
            LOGGER.debug("Payload {}", pedidosDTO);
            LOGGER.debug("br.com.hbsis.Pedidos EXistente:{}", pedidos);

            Itens itens;
            pedidos.setId(pedidosDTO.getId());
            pedidos.setCodPedido(pedidosDTO.getCodPedido());
            pedidos.setStatus(pedidosDTO.getStatus());
            pedidos.setFuncionario(ponteFuncionario.findByIdFuncionario(pedidosDTO.getFuncionario()));
            pedidos.setPeriodoVendas(pontePeriodoVendas.findByPeriodo(pedidosDTO.getPeriodoVendas()));
            pedidos.setDataPedido(pedidosDTO.getDataPedido());
            pedidos.setItensList(convercaoItens(pedidosDTO.getItensDTOS(), pedidos));

            save(pedidosDTO);
            return PedidosDTO.of(pedidos);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para pedido de id: [{}]", id);
        this.pontePedidos.deleteById(id);
    }

    private List<Itens> convercaoItens(List<ItensDTO> itensDTOS, Pedidos pedidos) {
        List<Itens> itens = new ArrayList<>();

        for (ItensDTO itensDTO : itensDTOS) {
            Itens itens1 = new Itens();

            itens1.setPedidos(pedidos);
            itens1.setQuantidade(itensDTO.getQuantidade());
            itens1.setProdutos(ponteProdutos.findByIdProdutos(itensDTO.getProdutos()));
            itens.add(itens1);
        }
        return itens;
    }

    private boolean validacaoAPI(String cnjp, String uuid, List<Itens> itens, double valorTotal) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        httpHeaders.add("Authorization", "f59feb10-1b67-11ea-978f-2e728ce88125");

        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<InvoiceDTO> invoiceDTOHttpEntity = new HttpEntity<>(invoiceDTO, httpHeaders);

        ResponseEntity<InvoiceDTO> responseEntityDTO = restTemplate.exchange("http://10.2.54.25:9999/v2/api-docs", HttpMethod.POST,
                invoiceDTOHttpEntity, InvoiceDTO.class);

        return true;
    }

    private double valorTotal(List<ItensDTO> itens) {
        double total = 0;
        for (ItensDTO itens1 : itens) {
            Produtos produtos = ponteProdutos.findByIdProdutos(itens1.getProdutos());
            total += produtos.getPreco() * itens1.getQuantidade();

        }
        return total;
    }


    public void exportPeriodoFornecedor(HttpServletResponse response, Long id) throws Exception {
        Export export = new Export();

        PeriodoVendas periodoVendas = pontePeriodoVendas.findByPeriodo(id);
        if (periodoVendas.getId().toString().equals(id.toString())) {

            export.exportPadrao(new String[]{"nomeProduto", "quantidade", "razao/cnpj"}, response, "export-csv-periodoFornecedor");
            for (Pedidos pedidos : pontePedidos.findAll()) {
                if (pedidos.getPeriodoVendas().getId().toString().equals(id.toString())) {

                    for (Itens itens : ponteItens.findAll()) {
                        String quantidade = itens.getQuantidade().toString();
                        String nomeProduto = itens.getProdutos().getNomeProduto();

                        MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
                        mask.setValueContainsLiteralCharacters(false);
                        String cnpj = pedidos.getPeriodoVendas().getPeriodoVendasFornecedor().getCnpj();
                        String cnpjPronto = mask.valueToString(cnpj);
                        String razao = pedidos.getPeriodoVendas().getPeriodoVendasFornecedor().getRazao();

                        String razaoCnpj = razao + " - " + cnpjPronto;

                        export.exportPadrao(new String[]{nomeProduto, quantidade, razaoCnpj},
                                response, "export-csv-periodoFornecedor");
                    }

                }
            }
        } else {
            throw new IllegalArgumentException("Período de vendas não encontrado");
        }
    }

    public List<PedidosDTO> pedidosFuncionario(Long id) {
        Funcionario funcionario = ponteFuncionario.findByIdFuncionario(id);

        List<Pedidos> listPedidos = pontePedidos.findAll();

        List<PedidosDTO> listPedidosCerto = new ArrayList<>();
        PedidosDTO pedidoDTO = new PedidosDTO();
        for (Pedidos pedido : listPedidos) {

            if (pedido.getFuncionario().getId().toString().equals(funcionario.getId().toString())) {
                if (pedido.getStatus().equals("ativo")) {

                    pedidoDTO.setCodPedido(pedido.getCodPedido());
                    pedidoDTO.setDataPedido(pedido.getDataPedido());
                    pedidoDTO.setFuncionario(pedido.getFuncionario().getId());
                    pedidoDTO.setId(pedido.getId());
                    pedidoDTO.setItensDTOS(convercaoItens(pedido.getItensList()));
                    pedidoDTO.setStatus(pedido.getStatus());
                    pedidoDTO.setPeriodoVendas(pedido.getPeriodoVendas().getId());

                    listPedidosCerto.add(pedidoDTO);

                } else if (pedido.getStatus().equals("retirado")) {
                    pedidoDTO.setCodPedido(pedido.getCodPedido());
                    pedidoDTO.setDataPedido(pedido.getDataPedido());
                    pedidoDTO.setFuncionario(pedido.getFuncionario().getId());
                    pedidoDTO.setId(pedido.getId());
                    pedidoDTO.setItensDTOS(convercaoItens(pedido.getItensList()));
                    pedidoDTO.setStatus(pedido.getStatus());
                    pedidoDTO.setPeriodoVendas(pedido.getPeriodoVendas().getId());

                    listPedidosCerto.add(pedidoDTO);
                }
            } else {
                throw new IllegalArgumentException("Pedidos desse funcionário não encontrado");
            }
        }
        return listPedidosCerto;
    }

    private List<ItensDTO> convercaoItens(List<Itens> itens) {
        List<ItensDTO> itensDTOS = new ArrayList<>();

        for (Itens itens2 : itens) {
            ItensDTO itensDTO = new ItensDTO();

            itensDTO.setQuantidade(itens2.getQuantidade());
            itensDTOS.add(itensDTO);
        }
        return itensDTOS;
    }

    public PedidosDTO updateStatus(PedidosDTO pedidosDTO, Long id) {
        Pedidos pedidos = pontePedidos.findByIdP(id);
        LocalDateTime hoje = LocalDateTime.now();

        if (pedidos.getStatus().equals("ativo")) {
            if (hoje.isBefore(pedidos.getPeriodoVendas().getDataFim())) {
                pedidos.setStatus(pedidosDTO.getStatus());
                update(PedidosDTO.of(pedidos), id);
            }
        }
        return pedidosDTO;
    }

    public PedidosDTO updateCompleto(PedidosDTO pedidosDTO, Long id) {
        Pedidos pedidos = pontePedidos.findByIdP(id);
        LocalDateTime hoje = LocalDateTime.now();

        if (pedidos.getStatus().equals("ativo")) {
            if (hoje.isBefore(pedidos.getPeriodoVendas().getDataFim())) {

                pedidos.setStatus(pedidosDTO.getStatus());
                pedidos.setDataPedido(pedidosDTO.getDataPedido());
                pedidos.setCodPedido(pedidosDTO.getCodPedido());
                pedidos.setFuncionario(ponteFuncionario.findByIdFuncionario(pedidosDTO.getFuncionario()));
                pedidos.setPeriodoVendas(pontePeriodoVendas.findByPeriodo(pedidosDTO.getPeriodoVendas()));
                pedidos.setItensList(convercaoItens(pedidosDTO.getItensDTOS(), pedidos));

                update(PedidosDTO.of(pedidos), id);
            }
        }
        return pedidosDTO;
    }


    public PedidosDTO updateEntrega(PedidosDTO pedidosDTO, Long id) {
        PeriodoVendas periodoVendas = pontePeriodoVendas.findByPeriodo(id);
        LocalDate hoje = LocalDate.now();
        LocalDate dataRetirada  = periodoVendas.getDataRetirada().toLocalDate();
        List<Pedidos> pedidosList = pontePedidos.findAll();
        for (Pedidos pedidos : pedidosList) {
            if (pedidos.getPeriodoVendas().getId().toString().equals(id.toString()))
                if (pedidos.getStatus().equals("ativo")) {
                    if (hoje.equals(dataRetirada)) {
                        pedidos.setStatus("retirado");

                        update(PedidosDTO.of(pedidos), pedidos.getId());
                    } else if (hoje.isAfter(dataRetirada)) {
                        throw new IllegalArgumentException("Essa data de retirada já passou");
                    }
                } else {
                    throw new IllegalArgumentException("Esse pedido não está ativo");
                }
        }
        return pedidosDTO;
    }


    public void email(Pedidos pedidos, List<Itens> itensList) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject("Pedido realizado com sucesso!");
        simpleMailMessage.setText(pedidos.getFuncionario().getNome() + "," + "você realizou a compra de: " + "\r\n"
                + itensListMetodo(itensList) + "\r\n"
                + "Data de retirada do pedido : " + pedidos.getPeriodoVendas().getDataRetirada()
                + "\r\n" + "HBSIS - Soluções em TI" + "\r\n"
                + "Rua Theodoro Holtrup, 982 - Vila Nova, Blumenau - SC"
                + "(47) 2123-5400"

        );
        simpleMailMessage.setTo(pedidos.getFuncionario().getEmail());
        simpleMailMessage.setFrom("larissaf.sousa17@gmail.com");
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList itensListMetodo(List<Itens> itensList) {
        ArrayList<String> itens = new ArrayList<>();
        for (Itens itens1 : itensList) {
            itens.add("Produto " + itens1.getProdutos().getNomeProduto() + "\r\n" + "Quantidade " + itens1.getQuantidade() + "\r\n");
        }
        return itens;
    }
}