package br.com.petfriends.pedido.infra.adapter.endereco.adapter;

import br.com.petfriends.pedido.core.model.CEP;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ValidarCepViaCepAdapterTest {
    private static ValidarCepViaCepAdapter validarCepViaCepAdapter;

    @BeforeAll
    static void setupBeforeAll() {
        ClientHttpConnector connector = new ReactorClientHttpConnector(
                HttpClient.create().responseTimeout(Duration.ofSeconds(10))
        );
        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(connector)
                .build();
        validarCepViaCepAdapter = new ValidarCepViaCepAdapter(webClient);
    }

    @Test
    void verificarCepExistente() {
        CEP cep = new CEP("01001000");

        Boolean result = validarCepViaCepAdapter.cepExiste(cep).block();
        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void verificarCepNaoExistente() {
        CEP cep = new CEP("99999999");

        Boolean result = validarCepViaCepAdapter.cepExiste(cep).block();
        assertNotNull(result);
        assertFalse(result);
    }
}
