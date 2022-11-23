package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProdutoConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(ProdutoRepository produtoRepository) {
        return args -> {
            Produto p = new Produto("Intel i5 9400f", "Processador massa", 849.50);
            Produto p2 = new Produto("GTX 1050 TI", "Placa de video massa",1849.99);
            Produto p3 = new Produto("Memoria HyperX Fury 8gb 3200mhz", "Memoria massa", 199.99);
            List<Produto> produtos = List.of(p,p2,p3);
            produtoRepository.saveAll(produtos);
        };
    }
}