package br.com.hbsis.funcionario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PonteFuncionario {

    private static final Logger LOGGER = LoggerFactory.getLogger(PonteFuncionario.class);
    private final IFuncionarioRepository iFuncionarioRepository;

    public PonteFuncionario(IFuncionarioRepository iFuncionarioRepository) {
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    public Funcionario save(Funcionario funcionario) {
        funcionario = iFuncionarioRepository.save(funcionario);
        return funcionario;
    }

    public Funcionario findByIdFuncionario(Long id) {
        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);
        if (funcionarioOptional.isPresent()){
            return funcionarioOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe",id));
    }

    public void deleteById(Long id) {
        LOGGER.info("Executando delete para Categorias de ID: [{}]", id);
        this.iFuncionarioRepository.deleteById(id);
    }

    public Optional<Funcionario> findById(Long id) {
        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);
        if (funcionarioOptional.isPresent()){
            return funcionarioOptional;
        }
        throw new IllegalArgumentException(String.format("ID %s não existe",id));
    }
}
