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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AtualizarDoacaoTest {

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
    public void deveAtualizarUmaDoacao() {
        PedidoDoacao pedidoDoacao = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("{" +
                        "\"idInstituicao\": 10," +
                        "\"status\": \"true\",\n" +
                        "}")
                .when()
                .put("/doacoes/{id}", doacao.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PedidoDoacao.class);


        Assert.assertEquals(10L, pedidoDoacao.getIdInstituicao().longValue());
        Assert.assertEquals(true, pedidoDoacao.isStatus());

        
    }


    @Test
    public void deveRetornarVazioQuandoAtualizarUmaDoacaoInexistente() {
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{" +
                        "\"nome\": \"Medicamento\"," +
                        "\"quantidade\": \"20_Caixas_Novalgina\",\n" +
                        "}")
                .when()
                .put("/doacoes/{id}", -456)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }


}
