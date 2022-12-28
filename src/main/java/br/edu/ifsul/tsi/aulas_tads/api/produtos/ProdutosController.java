package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/produtos")
public class ProdutosController {

    private final ProdutoService produtoService;

    public ProdutosController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> selectAll() {
        return new ResponseEntity<>(produtoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Produto> selectById(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoService.findById(id);
        return produto.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Produto> selectByNome(@PathVariable("nome") String nome) {
        Optional<Produto> produto = produtoService.findByNome(nome);
        return produto.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> insert(@RequestBody Produto produto){
        try {
            ProdutoDTO p = produtoService.salvarProduto(produto);
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable("id") Long id, @RequestBody Produto produto){
        try {
            ProdutoDTO p = produtoService.update(produto, id);
            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        try {
            produtoService.deleteById(id);
            return new ResponseEntity<>("Produto Deletado Com Sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Produto Não Encontrado", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

