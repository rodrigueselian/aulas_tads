package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import lombok.Data;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {
    private Long id;
    private String nome;
    private BigDecimal calorias;
    private BigDecimal preco;
    private Long estoque;
    private Boolean vegano;

    public static ProdutoDTO create(Produto p){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(p, ProdutoDTO.class);
    }
}
