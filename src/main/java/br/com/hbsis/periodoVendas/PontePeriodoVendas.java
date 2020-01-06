package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PontePeriodoVendas {
    private final IPeriodoVendasRepository iPeriodoVendasRepository;

    public PontePeriodoVendas(IPeriodoVendasRepository iPeriodoVendasRepository) {
        this.iPeriodoVendasRepository = iPeriodoVendasRepository;
    }

    public PeriodoVendas save(PeriodoVendas periodoVendas) {
        periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);
        return periodoVendas;
    }

    public List<PeriodoVendas> findAll() {
        List<PeriodoVendas> periodoVendasList= this.iPeriodoVendasRepository.findAll();
        if (periodoVendasList.isEmpty()){
            return periodoVendasList;
        }
        throw new IllegalArgumentException("Período de Vendas não encontrado");
    }

    public Optional<PeriodoVendas> findById(Long id) {
        Optional<PeriodoVendas> periodoVendasOptional =this.iPeriodoVendasRepository.findById(id);
        if (periodoVendasOptional.isPresent()){
            return periodoVendasOptional;
        }
        throw new IllegalArgumentException("ID %s não encontrado...");
    }

    public PeriodoVendas findByPeriodo(Long id) {
        Optional<PeriodoVendas> periodoVendasOptional =this.iPeriodoVendasRepository.findById(id);
        if (periodoVendasOptional.isPresent()){
            return periodoVendasOptional.get();
        }
        throw new IllegalArgumentException("ID %s não encontrado...");
    }

    public void deleteById(Long id) {
        this.iPeriodoVendasRepository.deleteById(id);
    }

    public List<PeriodoVendas> findallF() {
        return iPeriodoVendasRepository.findAll();
    }

    public boolean existFornecedor(Fornecedor id){
        return iPeriodoVendasRepository.existsByPeriodoVendasFornecedor(id);
    }

    public PeriodoVendas findByFornecedor(Long id){
        return iPeriodoVendasRepository.findByPeriodoVendasFornecedor(id);
    }

}
