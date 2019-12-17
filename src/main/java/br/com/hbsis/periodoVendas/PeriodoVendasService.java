package br.com.hbsis.periodoVendas;
//regra de negocios

import br.com.hbsis.fornecedor.Fornecedor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PeriodoVendasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoVendas.class);

    private final IPeriodoVendasRepository iPeriodoVendasRepository;
   // private final Fornecedor fornecedor;

    public PeriodoVendasService(IPeriodoVendasRepository iPeriodoVendasRepository) {
        this.iPeriodoVendasRepository = iPeriodoVendasRepository;
     //   this.fornecedor = fornecedor;
    }

    public PeriodoVendasDTO save(PeriodoVendasDTO periodoVendasDTO) {
        this.validate(periodoVendasDTO);
        LOGGER.info("Salvando periodo de vendas");
        LOGGER.debug("br.com.hbsis.PeriodoVendas:{}", periodoVendasDTO);

        PeriodoVendas periodoVendas = new PeriodoVendas();

        periodoVendas.setId(periodoVendasDTO.getId());
        periodoVendas.setDataInicio(periodoVendasDTO.getDataInicio());
        periodoVendas.setDataFim(periodoVendasDTO.getDataFim());
        periodoVendas.setPeridoVendasFornecedor(periodoVendasDTO.getPeridoVendasFornecedor());
        periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
        periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

        periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);
        return periodoVendasDTO.of(periodoVendas);
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
        if (StringUtils.isEmpty(periodoVendasDTO.getPeridoVendasFornecedor().toString())) {
            throw new IllegalArgumentException("Data periodo de vendas por fornecedor não deve ser null");
        }
/*        if (StringUtils.isEmpty(fornecedor.getId().toString())){
            if (StringUtils.isEmpty(String.valueOf(periodoVendasDTO.getDataInicio().toString() == periodoVendasDTO.getDataFim().toString()))) {
                throw new IllegalArgumentException("Data de inicio e fim não devem ser iguais");
            }
            if (StringUtils.isEmpty(String.valueOf(periodoVendasDTO.getDataInicio().toString() == periodoVendasDTO.getDataInicio().toString()))) {
                throw new IllegalArgumentException("Data de inicio e início não devem ser iguais");
            }

            if (StringUtils.isEmpty(String.valueOf(periodoVendasDTO.getDataFim().toString() == periodoVendasDTO.getDataFim().toString()))) {
                throw new IllegalArgumentException("Data periodo de vendas por fornecedor não deve ser null");
            }
            if (StringUtils.isEmpty(String.valueOf(periodoVendasDTO.getDataFim().toString() == periodoVendasDTO.getDataInicio().toString()))) {
                throw new IllegalArgumentException("Data periodo de vendas por fornecedor não deve ser null");
            }
        }*/
    }

    public PeriodoVendasDTO update(PeriodoVendasDTO periodoVendasDTO, Long id){
        Optional<PeriodoVendas> periodoVendasOptional =this.iPeriodoVendasRepository.findById(id);

        if (periodoVendasOptional.isPresent()){
            PeriodoVendas periodoVendas = periodoVendasOptional.get();
            LOGGER.info("Atualizando Periodo de vendas... id[{}]", periodoVendas.getId());
            LOGGER.debug("Payload {}", periodoVendasDTO);
            LOGGER.debug("br.com.hbsis.PeriodoVendas EXistente:{}", periodoVendas);

            periodoVendas.setId(periodoVendasDTO.getId());
            periodoVendas.setDataInicio(periodoVendasDTO.getDataInicio());
            periodoVendas.setDataFim(periodoVendasDTO.getDataFim());
            periodoVendas.setPeridoVendasFornecedor(periodoVendasDTO.getPeridoVendasFornecedor());
            periodoVendas.setDataRetirada(periodoVendasDTO.getDataRetirada());
            periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

            periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

            return periodoVendasDTO.of(periodoVendas);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id){
        LOGGER.info("Executando delete para Periodo de Vendas de ID: [{}]", id);
        this.iPeriodoVendasRepository.deleteById(id);
    }
}
