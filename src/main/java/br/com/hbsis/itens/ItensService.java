package br.com.hbsis.itens;

import br.com.hbsis.export.Export;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.PonteFornecedor;
import br.com.hbsis.pedidos.Pedidos;
import br.com.hbsis.pedidos.PontePedidos;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import br.com.hbsis.produtos.PonteProdutos;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class ItensService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItensRest.class);
    private final PonteItens ponteItens;
    private final PonteProdutos ponteProdutos;
    private final PontePedidos pontePedidos;
    private final PonteFornecedor ponteFornecedor;

    public ItensService(PonteItens ponteItens, PonteProdutos ponteProdutos, PontePedidos pontePedidos, PonteFornecedor ponteFornecedor) {
        this.ponteItens = ponteItens;
        this.ponteProdutos = ponteProdutos;
        this.pontePedidos = pontePedidos;
        this.ponteFornecedor = ponteFornecedor;
    }

    public ItensDTO save(ItensDTO itensDTO){
        this.validate(itensDTO);

        LOGGER.info("Salvando Itens");
        LOGGER.debug("br.com.hbsis.Itens:{}", itensDTO);
        Itens itens = new Itens();

        itens.setId(itensDTO.getId());
        itens.setQuantidade(itensDTO.getQuantidade());
        itens.setProdutos(ponteProdutos.findByIdProdutos(itensDTO.getProdutos()));
        itens.setPedidos(pontePedidos.findByIdPedido(itensDTO.getPedidos()));

        itens = this.ponteItens.save(itens);
        return  ItensDTO.of(itens);

    }

    private void validate(ItensDTO itensDTO) {
        LOGGER.info("Validando Itens");

        if (itensDTO == null){
            throw  new IllegalArgumentException("ItensDTO não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(itensDTO.getProdutos().toString())){
            throw  new IllegalArgumentException("Produtos não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(itensDTO.getQuantidade().toString())){
            throw  new IllegalArgumentException("Quantidade de produtos não deve ser nulo/vazio");
        }
    }

    public ItensDTO update(ItensDTO itensDTO, Long id){
        Optional<Itens> itensOptional =this.ponteItens.findById(id);

        if (itensOptional.isPresent()){
            Itens itensExistente = itensOptional.get();

            LOGGER.info("Atualizando Itens... id [{}] ", itensExistente.getId());
            LOGGER.debug("Payload {}", itensExistente);
            LOGGER.debug("br.com.hbsis.Itens Existente: {}", itensExistente);


            itensExistente.setId(itensDTO.getId());
            itensExistente.setQuantidade(itensDTO.getQuantidade());
            itensExistente.setProdutos(ponteProdutos.findByIdProdutos(itensDTO.getProdutos()));
            itensExistente.setPedidos(pontePedidos.findByIdPedido(itensDTO.getPedidos()));


            itensExistente = this.ponteItens.save(itensExistente);

            return ItensDTO.of(itensExistente);
        }
        throw new IllegalArgumentException("Id %s não existe");
    }
    public void delete(Long id){
        LOGGER.info("Executando delete para Itens de ID: [{}]", id);
        this.ponteItens.deleteById(id);
    }


}
