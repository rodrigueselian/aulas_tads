package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    List<Produto> findByNome(String nome);
}
