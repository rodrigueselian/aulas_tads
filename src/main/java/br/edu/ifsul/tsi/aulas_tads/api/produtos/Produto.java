package br.edu.ifsul.tsi.aulas_tads.api.produtos;

import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq")
    @SequenceGenerator(name = "produto_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "preco", scale = 2, nullable = false)
    private double preco;

    public Produto(){

    }

    public Produto(String nome, String descricao, double preco){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public ProdutoDTO getProdutoDTO(){
        return new ProdutoDTO(this.id, this.nome, this.descricao);
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public static Produto create(ProdutoDTO p){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(p, Produto.class);
    }
}