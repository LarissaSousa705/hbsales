package br.com.hbsis.periodoVendas;
//regra de negocios

import br.com.hbsis.export.Export;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.PonteFornecedor;
import br.com.hbsis.itens.Itens;
import br.com.hbsis.itens.PonteItens;
import br.com.hbsis.pedidos.Pedidos;
import br.com.hbsis.pedidos.PontePedidos;
import br.com.hbsis.produtos.PonteProdutos;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PeriodoVendasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoVendas.class);

    private final PontePeriodoVendas pontePeriodoVendas;
    private final PonteFornecedor ponteFornecedor;
    private final PonteProdutos ponteProdutos;
    private final PontePedidos pontePedidos;
    private final PonteItens ponteItens;


    public PeriodoVendasService(PontePeriodoVendas pontePeriodoVendas, PonteFornecedor ponteFornecedor, PonteProdutos ponteProdutos, PontePedidos pontePedidos, PonteItens ponteItens) {
        this.pontePeriodoVendas = pontePeriodoVendas;
        this.ponteFornecedor = ponteFornecedor;
        this.ponteProdutos = ponteProdutos;
        this.pontePedidos = pontePedidos;
        this.ponteItens = ponteItens;
    }

    public PeriodoVendasDTO save(PeriodoVendasDTO periodoVendasDTO) {
        this.validate(periodoVendasDTO);
        LOGGER.info("Salvando periodo de vendas");
        LOGGER.debug("br.com.hbsis.PeriodoVendas:{}", periodoVendasDTO);

        PeriodoVendas periodoVendas = new PeriodoVendas();

        periodoVendas.setId(periodoVendasDTO.getId());
        periodoVendas.setDataInicio(periodoVendasDTO.getDataInicio());
        periodoVendas.setDataFim(periodoVendasDTO.getDataFim());
        periodoVendas.setPeriodoVendasFornecedor(ponteFornecedor.findByIdFornecedor(periodoVendasDTO.getPeriodoVendasFornecedor()));
        periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
        periodoVendas.setDescricao(periodoVendasDTO.getDescricao());


        periodoVendas = this.pontePeriodoVendas.save(periodoVendas);
        return PeriodoVendasDTO.of(periodoVendas);
    }


    private void validate(PeriodoVendasDTO periodoVendasDTO) {
        LOGGER.info("Validando periodos de vendas");

        if (periodoVendasDTO == null) {
            throw new IllegalArgumentException("periodoVendasDTO não deve ser null");
        }
        if (StringUtils.isEmpty(periodoVendasDTO.getDescricao())) {
            throw new IllegalArgumentException("Descrição não deve ser null");
        }
        if (StringUtils.isEmpty(periodoVendasDTO.getDataFim().toString())) {
            throw new IllegalArgumentException("Data de fim não deve ser null");
        }
        if (StringUtils.isEmpty(periodoVendasDTO.getDataInicio().toString())) {
            throw new IllegalArgumentException("Data de inicio não deve ser null");
        }
        if (StringUtils.isEmpty(periodoVendasDTO.getDataRetirada().toString())) {
            throw new IllegalArgumentException("Data de retirada de produto não deve ser null");
        }

        if (StringUtils.isEmpty(periodoVendasDTO.getPeriodoVendasFornecedor().toString())) {
        }

        String datInicio = periodoVendasDTO.getDataInicio().toString();
        String datFim = periodoVendasDTO.getDataFim().toString();
        String idFornecedor = periodoVendasDTO.getPeriodoVendasFornecedor().toString();


        for (PeriodoVendas line : pontePeriodoVendas.findallF()) {
            String datInicio2 = line.getDataInicio().toString();
            String datFim2 = line.getDataFim().toString();
            String periodoIdF = line.getPeriodoVendasFornecedor().getId().toString();

            if (datInicio.equals(datInicio2) && idFornecedor.equals(periodoIdF)) {
                throw new IllegalArgumentException("Esta data de início de período já existe...");
            } else if (datFim.equals(datFim2) && idFornecedor.equals(periodoIdF)) {
                throw new IllegalArgumentException("Esta data de fim de período já existe...");
            }
        }
        for (PeriodoVendas line2 : pontePeriodoVendas.findallF()) {
            LocalDateTime datInicio3 = periodoVendasDTO.getDataInicio();
            String idFornecedor3 = periodoVendasDTO.getPeriodoVendasFornecedor().toString();

            LocalDateTime datHoje = LocalDateTime.now();
            if (datInicio3.isBefore(datHoje) && idFornecedor.equals(idFornecedor3) || datInicio3.isBefore(datHoje) && idFornecedor.equals(idFornecedor3)) {
                throw new IllegalArgumentException("Período de Vendas não podem ser criado com as datas de vigência anteriores a hoje");
            }
        }
    }

    public PeriodoVendasDTO update(PeriodoVendasDTO periodoVendasDTO, Long id) {
        Optional<PeriodoVendas> periodoVendasOptional = this.pontePeriodoVendas.findById(id);

        if (periodoVendasOptional.isPresent()) {
            PeriodoVendas periodoVendas = periodoVendasOptional.get();
            LOGGER.info("Atualizando Periodo de vendas... id[{}]", periodoVendas.getId());
            LOGGER.debug("Payload {}", periodoVendasDTO);
            LOGGER.debug("br.com.hbsis.PeriodoVendas EXistente:{}", periodoVendas);

            periodoVendas.setId(periodoVendasDTO.getId());
            periodoVendas.setPeriodoVendasFornecedor(ponteFornecedor.findByIdFornecedor(periodoVendasDTO.getPeriodoVendasFornecedor()));
            periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
            periodoVendas.setDescricao(periodoVendasDTO.getDescricao());
            periodoVendas.setDataInicio(periodoVendasDTO.getDataInicio());
            periodoVendas.setDataFim(periodoVendasDTO.getDataFim());

            LocalDateTime datFim = periodoVendas.setDataFim(periodoVendasDTO.getDataFim());


            if (datFim.isAfter(datFim)) {
                throw new IllegalArgumentException("Este período de vendas já acabou, portanto não deve ser alterado");
            }
            periodoVendas = this.pontePeriodoVendas.save(periodoVendas);

            return PeriodoVendasDTO.of(periodoVendas);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Periodo de Vendas de ID: [{}]", id);
        this.pontePeriodoVendas.deleteById(id);
    }

/*
    public void exportPeriodoFornecedor(HttpResponse response, Long id) throws Exception {

        Export export = new Export();

        export.exportPadrao(new String[]{"nomeProduto", "quantidade", "razao/cnpj"}, (HttpServletResponse) response, "exportPeriodoFornecedor");

        PeriodoVendas periodoVendas = pontePeriodoVendas.findByPeriodo(id);
        if (){

        }

        for (Pedidos pedidos : pontePedidos.findAll()) {
            for (Itens itens : ponteItens.findAll()){
                String quantidade = itens.getQuantidade().toString();
                String  nomeProduto = itens.getProdutos().getNomeProduto();

                if (pedidos.getPeriodoVendas().toString().equals(id.toString())){

                    MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
                    mask.setValueContainsLiteralCharacters(false);
                    String cnpj = pedidos.getPeriodoVendas().getPeriodoVendasFornecedor().getCnpj();
                    String cnpjPronto = mask.valueToString(cnpj);
                    String razao = pedidos.getPeriodoVendas().getPeriodoVendasFornecedor().getRazao();

                    String razaoCnpj = razao + " - " + cnpjPronto;

                    export.exportPadrao(new String[]{nomeProduto, quantidade, razaoCnpj},
                            (HttpServletResponse) response, "exportPeriodoFornecedor");

                }
            }

        }
    }*/

}
