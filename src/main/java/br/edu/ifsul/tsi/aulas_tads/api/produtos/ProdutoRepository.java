package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);

    @Modifying
    @Query(value = "update Produto p set p.nome = , p.descricao = ?2, p.preco = ?3 where p.id = ?4")
    Produto updateProduto(Produto x, Long id);

    @Query(value = "select p from Produto p where p.preco < ?1")
    Optional<List<Produto>> findAllByPreco (BigDecimal preco);
}