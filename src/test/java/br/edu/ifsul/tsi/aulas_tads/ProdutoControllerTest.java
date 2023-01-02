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
        List<ProdutoDTO> produtos = getProdutos("/api/v1/produtos").getBody();
        assertNotNull(produtos);
        assertEquals(5, produtos.size());

        produtos = getProdutos("/api/v1/produtos?page=0&size=5").getBody();
        assertNotNull(produtos);
        assertEquals(5, produtos.size());
    }

    @Test
    public void selectByNome() {

        assertEquals(1, getProdutos("/api/v1/produtos/nome/Frango com batata").getBody().size());
        assertEquals(1, getProdutos("/api/v1/produtos/nome/salada de batata").getBody().size());
        assertEquals(1, getProdutos("/api/v1/produtos/nome/Escondidinho de batata com carne").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getProdutos("/api/v1/produtos/nome/xxx").getStatusCode());
    }

    @Test
    public void selectById() {

        assertNotNull(getProduto("/api/v1/produtos/1"));
        assertNotNull(getProduto("/api/v1/produtos/2"));
        assertNotNull(getProduto("/api/v1/produtos/3"));

        assertEquals(HttpStatus.NOT_FOUND, getProduto("/api/v1/produtos/1000").getStatusCode());
    }

    @Test
    public void testInsert() {

        Produto produto = new Produto();
        produto.setCalorias(new BigDecimal("900"));
        produto.setEstoque(100L);
        produto.setNome("Macarrao com creme de batata");
        produto.setVegano(true);
        produto.setPreco(new BigDecimal("14.90"));

        // Insert
        ResponseEntity response = post("/api/v1/produtos", produto, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        ProdutoDTO p = getProduto(location).getBody();

        assertNotNull(p);
        assertEquals("Macarrao com creme de batata", p.getNome());
        assertEquals(Long.valueOf(100), p.getEstoque());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getProduto(location).getStatusCode());
    }

    @Test
    public void testUpdate() {
        //primeiro insere o objeto
        Produto produto = new Produto();
        produto.setCalorias(new BigDecimal(400));
        produto.setEstoque(100L);
        produto.setNome("Chocolate");
        produto.setVegano(false);
        produto.setPreco(new BigDecimal(6.90));

        // Insert
        ResponseEntity response = post("/api/v1/produtos", produto, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        ProdutoDTO p = getProduto(location).getBody();

        assertNotNull(p);
        assertEquals("Chocolate", p.getNome());
        assertEquals(Long.valueOf(100), p.getEstoque());

        //depois altera seu valor
        Produto pa = Produto.create(p);
        pa.setNome("Chocolate Vegano");
        pa.setVegano(true);

        // Update
        response = put("/api/v1/produtos/" + p.getId(), pa, null);
        System.out.println(response);
        assertEquals("Chocolate Vegano", pa.getNome());

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
        ResponseEntity response = getProduto("/api/v1/produtos/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}