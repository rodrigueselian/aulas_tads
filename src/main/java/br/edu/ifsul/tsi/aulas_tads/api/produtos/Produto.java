package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal calorias;
    private BigDecimal preco;
    private Long estoque;
    private Boolean vegano;

    public static Produto create(ProdutoDTO p){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(p, Produto.class);
    }
}
