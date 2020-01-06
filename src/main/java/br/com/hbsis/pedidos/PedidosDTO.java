package br.com.hbsis.pedidos;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.itens.Itens;
import br.com.hbsis.itens.ItensDTO;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import br.com.hbsis.produtos.ProdutosDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidosDTO {

    private Long id;
    private String codPedido;
    private String status;
    private LocalDateTime dataPedido;
    private Long funcionario;
    private Long periodoVendas;
    private List<ItensDTO> itensDTOS;

    public PedidosDTO(Long id, String codPedido, String status, LocalDateTime dataPedido, Long funcionario, Long periodoVendas, List<ItensDTO> itensDTOS) {
        this.id = id;
        this.codPedido = codPedido;
        this.status = status;
        this.dataPedido = dataPedido;
        this.funcionario = funcionario;
        this.periodoVendas = periodoVendas;
        this.itensDTOS = itensDTOS;
    }

    public PedidosDTO() {

    }


    public static PedidosDTO of(Pedidos pedidos){

        List<ItensDTO> itens = new ArrayList<ItensDTO>();
        pedidos.getItensList().forEach(itens1 -> itens.add(ItensDTO.of(itens1)));

        return new PedidosDTO(
                pedidos.getId(),
                pedidos.getCodPedido(),
                pedidos.getStatus(),
                pedidos.getDataPedido(),
                pedidos.getFuncionario().getId(),
                pedidos.getPeriodoVendas().getId(),
                itens
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Long funcionario) {
        this.funcionario = funcionario;
    }

    public Long getPeriodoVendas() {
        return periodoVendas;
    }

    public void setPeriodoVendas(Long periodoVendas) {
        this.periodoVendas = periodoVendas;
    }

    public List<ItensDTO> getItensDTOS() {
        return itensDTOS;
    }

    public void setItensDTOS(List<ItensDTO> itensDTOS) {
        this.itensDTOS = itensDTOS;
    }

    @Override
    public String toString() {
        return "PedidosDTO{" +
                "id=" + id +
                ", codPedido='" + codPedido + '\'' +
                ", status='" + status + '\'' +
                ", dataPedido=" + dataPedido +
                ", funcionario=" + funcionario +
                ", periodoVendas=" + periodoVendas +
                '}';
    }
}