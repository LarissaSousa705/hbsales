package br.com.hbsis.pedidos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class PontePedidos {
    private static final Logger LOGGER = LoggerFactory.getLogger(PontePedidos.class);
    private final IPedidosRepository iPedidosRepository;

    public PontePedidos(IPedidosRepository iPedidosRepository) {
        this.iPedidosRepository = iPedidosRepository;
    }

    public Pedidos save(Pedidos pedidos) {
        pedidos = this.iPedidosRepository.save(pedidos);
        return pedidos;
    }

    public Optional<Pedidos> findById(Long id) {
        Optional<Pedidos> pedidosOptional = this.iPedidosRepository.findById(id);
        if (pedidosOptional.isPresent()){
            return pedidosOptional;
        }
        throw new IllegalArgumentException(String.format("ID %s não encontrado", id));
    }

    public void deleteById(Long id) {
        LOGGER.info("Executando delete para Pedidos com ID :[{}]", id);
        this.iPedidosRepository.deleteById(id);
    }

    public Pedidos findByIdPedido(Long pedidos) {
        Optional<Pedidos> pedidosOptional = this.iPedidosRepository.findById(pedidos);
        if (pedidosOptional.isPresent()){
            return pedidosOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não encontrador", pedidos));

    }
    public Pedidos findByIdP(Long id){
        Optional<Pedidos> pedidosOptional = this.iPedidosRepository.findById(id);
        if (pedidosOptional.isPresent()){
            return pedidosOptional.get();
        }
        throw new IllegalArgumentException(String.format("Id não encontrado ou inválido", id));
    }

    public List<Pedidos> findAll() {
        return this.iPedidosRepository.findAll();
    }
}
