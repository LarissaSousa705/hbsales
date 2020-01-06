package br.com.hbsis.produtos;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PonteProdutos {
    private final IProdutosRepository iProdutosRepository;

    public PonteProdutos(IProdutosRepository iProdutosRepository) {
        this.iProdutosRepository = iProdutosRepository;
    }

    public Produtos save(Produtos produtos) {
        produtos = this.iProdutosRepository.save(produtos);
        return produtos;
    }

    public Optional<Produtos> findById(Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);
        if (produtosOptional.isPresent()){
            return produtosOptional;
        }
        throw new IllegalArgumentException(String.format("ID %s não encontrador", id));
    }

    public Optional<Produtos> findByCodProduto(String cod) {
        Optional<Produtos> produtosOptional =this.iProdutosRepository.findByCodProduto(cod);
        if (produtosOptional.isPresent()){
            return produtosOptional;
        }
        throw new IllegalArgumentException(String.format("COD %s não encontrador", cod));
    }
    public Produtos findByCod(String cod) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findByCodProduto(cod);

        if (produtosOptional.isPresent()) {
            return produtosOptional.get();
        }
        throw new IllegalArgumentException("Cod %s não existe");
    }

    public void deleteById(Long id) {
        this.iProdutosRepository.deleteById(id);
    }

    public List<Produtos> findAll() {
        List<Produtos> produtos = this.iProdutosRepository.findAll();
        if (produtos.isEmpty()){
            return produtos;
        }
        throw new IllegalArgumentException("Produto não encontrado");
    }

    public List<Produtos> saveAll(List<Produtos> produtosList) {
        return this.iProdutosRepository.saveAll(produtosList);
    }

    public Produtos findByIdProdutos(Long id) {
        Optional <Produtos> produtosOptional = this.iProdutosRepository.findById(id);
        if (produtosOptional.isPresent()) {
            return produtosOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não encontrador", id));
    }
    /*public Optional<Produtos> findById(Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);
        if (produtosOptional.isPresent()){
            return produtosOptional;
        }
        throw new IllegalArgumentException(String.format("ID %s não encontrador", id));
    }*/


}
