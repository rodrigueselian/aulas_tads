package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto save(Produto p) {
        return produtoRepository.save(p);
    }

    public Produto update(Long id, String nome, String descricao, double preco) {
        return produtoRepository.updateProduto(nome, descricao, preco,id);
    }

    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }

    public Optional<Produto> findByNome(String nome) {
        return produtoRepository.findByNome(nome);
    }
}