package br.edu.ifsul.tsi.aulas_tads;

import br.edu.ifsul.tsi.aulas_tads.api.infra.security.exception.ObjectNotFoundException;
import br.edu.ifsul.tsi.aulas_tads.api.produtos.Produto;
import br.edu.ifsul.tsi.aulas_tads.api.produtos.ProdutoDTO;
import br.edu.ifsul.tsi.aulas_tads.api.produtos.ProdutoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService service;

    @Test
    public void testGetProdutos() {
        List<ProdutoDTO> produtos = service.getProdutos();
        assertEquals(5, produtos.size());
    }

    @Test
    public void testGetProdutoById(){
        ProdutoDTO p = service.getProdutoById(1L);
        assertNotNull(p);
        assertEquals("Frango com batata", p.getNome());
    }

    @Test
    public void getProdutosByNome(){
        assertEquals(1, service.getProdutosByNome("Frango com batata").size());
        assertEquals(1, service.getProdutosByNome("Salada de batata").size());
        assertEquals(1, service.getProdutosByNome("Batata Frita").size());
    }

    @Test
    public void testInsert() {

        //cria o produto para teste
        Produto produto = new Produto();
        produto.setNome("Teste");
        produto.setCalorias(new BigDecimal("10.00"));
        produto.setPreco(new BigDecimal("10.00"));
        produto.setEstoque(100L);
        produto.setVegano(true);

        //insere o produto na base da dados
        ProdutoDTO p = service.insert(produto);

        //se inseriu
        assertNotNull(p);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = p.getId();
        assertNotNull(id);
        p = service.getProdutoById(id);
        assertNotNull(p);

        //compara os valores inseridos com os valores pesquisados para confirmar
        assertEquals("Teste", p.getNome());
        assertEquals(new BigDecimal("10.00"), p.getCalorias());
        assertEquals(new BigDecimal("10.00"), p.getPreco());
        assertEquals(Long.valueOf(100), p.getEstoque());
        assertEquals(Boolean.TRUE, p.getVegano());

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        try {
            service.getProdutoById(id);
            fail("O produto não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }

    @Test
    public void TestUpdate(){
        ProdutoDTO pDTO = service.getProdutoById(1L);
        String nome = pDTO.getNome(); //armazena o valor original para voltar na base
        pDTO.setNome("Frango com batata rosa");
        Produto p = Produto.create(pDTO);

        pDTO = service.update(p, p.getId());
        assertNotNull(pDTO);
        assertEquals("Frango com batata rosa", pDTO.getNome());

        //volta ao valor original
        p.setNome(nome);
        pDTO = service.update(p, p.getId());
        assertNotNull(pDTO);
    }

    @Test
    public void testDelete(){
        //cria o produto para teste
        Produto produto = new Produto();
        produto.setNome("Teste");
        produto.setCalorias(new BigDecimal("1.00"));
        produto.setPreco(new BigDecimal("1.00"));
        produto.setEstoque(1L);
        produto.setVegano(true);

        //insere o produto na base da dados
        ProdutoDTO p = service.insert(produto);

        //se inseriu
        assertNotNull(p);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = p.getId();
        assertNotNull(id);
        p = service.getProdutoById(id);
        assertNotNull(p);

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        try {
            service.getProdutoById(id);
            fail("O produto não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }
}
