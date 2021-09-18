package br.com.fundatec.doacao.integration;


import br.com.fundatec.model.PedidoDoacao;
import br.com.fundatec.repository.DoacaoRepository;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExcluirDoacaoTest {

    @LocalServerPort
    private int randomPort;

    @Autowired
    private DoacaoRepository doacaoRepository;
    private PedidoDoacao doacao;

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = randomPort;

        doacaoRepository.deleteAll();

        doacao = new PedidoDoacao();
        doacao.setIdInstituicao(1L);
        doacao.setStatus(true);
        doacao = doacaoRepository.save(doacao);

}

    @Test
    public void deveExcluirUmaDoacao() {
        RestAssured
                .given()
                .when()
                .delete("/doacoes/{id}", doacao.getId())
                .then()
                .statusCode(HttpStatus.OK.value());

        Assert.assertEquals(0, doacaoRepository.count());
    }
    }
