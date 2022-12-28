package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public ProdutoDTO update(Produto produto, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o produto no banco de dados
        Optional<Produto> optional = produtoRepository.findById(id);
        if(optional.isPresent()) {
            Produto db = optional.get();
            // Copiar as propriedades
            db.setNome(produto.getNome());
            db.setDescricao(produto.getDescricao());
            db.setPreco(produto.getPreco());
            System.out.println("Produto id " + db.getId());

            // Atualiza o produto
            produtoRepository.save(db);

            return ProdutoDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public ProdutoDTO salvarProduto(Produto p) {
        var produto = this.produtoRepository.save(p);
        return produto.getProdutoDTO();
    }


    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }

    public Optional<Produto> findByNome(String nome) {
        return produtoRepository.findByNome(nome);
    }
}