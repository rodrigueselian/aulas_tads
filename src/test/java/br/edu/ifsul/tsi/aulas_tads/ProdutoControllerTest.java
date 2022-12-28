package br.edu.ifsul.tsi.aulas_tads;


import br.edu.ifsul.tsi.aulas_tads.api.produtos.Produto;
import br.edu.ifsul.tsi.aulas_tads.api.produtos.ProdutoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AulasTadsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoControllerTest extends BaseAPITest {

    //Métodos utilitários
    private ResponseEntity<ProdutoDTO> getProduto(String url) {
        return get(url, ProdutoDTO.class);
    }

    private ResponseEntity<List<ProdutoDTO>> getProdutos(String url) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(
            url,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<List<ProdutoDTO>>() {
            });
    }

    @Test
    public void selectAll() {
        List<ProdutoDTO> produtos = getProdutos("/api/produtos").getBody();
        assertNotNull(produtos);
        assertEquals(5, produtos.size());

        produtos = getProdutos("/api/produtos?page=0&size=5").getBody();
        assertNotNull(produtos);
        assertEquals(3, produtos.size());
    }

    @Test
    public void selectByNome() {

        assertEquals(1, getProdutos("/api/produtos/nome/arroz").getBody().size());
        assertEquals(1, getProdutos("/api/produtos/nome/cafe").getBody().size());
        assertEquals(1, getProdutos("/api/produtos/nome/feijao").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getProdutos("/api/v1/produtos/nome/xxx").getStatusCode());
    }

    @Test
    public void selectById() {

        assertNotNull(getProduto("/api/produtos/1"));
        assertNotNull(getProduto("/api/produtos/2"));
        assertNotNull(getProduto("/api/produtos/3"));

        assertEquals(HttpStatus.NOT_FOUND, getProduto("/api/produtos/1000").getStatusCode());
    }

    @Test
    public void testInsert() {

        Produto produto = new Produto();
        produto.setDescricao("Memoria Ram Adicionada no teste");
        produto.setPreco(50);
        produto.setNome("Memoria Ram");

        // Insert
        ResponseEntity response = post("/api/produtos", produto, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        ProdutoDTO p = getProduto(location).getBody();

        assertNotNull(p);
        assertEquals("Memoria", p.getNome());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getProduto(location).getStatusCode());
    }

    @Test
    public void testUpdate() {
        //primeiro insere o objeto
        Produto produto = new Produto();
        produto.setDescricao("Memoria Ram Adicionada no teste");
        produto.setPreco(100);
        produto.setNome("Memoria Ram");

        // Insert
        ResponseEntity response = post("/api/produtos", produto, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        ProdutoDTO p = getProduto(location).getBody();

        assertNotNull(p);
        assertEquals("Memoria", p.getNome());

        //depois altera seu valor
        Produto pa = Produto.create(p);
        pa.setPreco(500);

        // Update
        response = put("/api/produtos/" + p.getId(), pa, null);
        System.out.println(response);
        assertEquals(Long.valueOf(500), pa.getPreco());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getProduto(location).getStatusCode());

    }

    @Test
    public void testDelete() {
        this.testInsert();
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getProduto("/api/produtos/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}