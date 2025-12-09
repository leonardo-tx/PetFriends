package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.EnderecoCampoNuloException;
import br.com.petfriends.pedido.core.exception.EnderecoCampoTamanhoInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {
    private final CEP validCEP = new CEP("12345678");

    @Test
    void shouldCreateValidEndereco() {
        Endereco endereco = new Endereco(
                "Rua A",
                "123",
                "Apto 1",
                "Bairro B",
                "Cidade C",
                "Estado D",
                validCEP
        );
        assertNotNull(endereco);
        assertEquals("Rua A", endereco.rua());
        assertEquals("123", endereco.numero());
        assertEquals("Apto 1", endereco.complemento());
        assertEquals("Bairro B", endereco.bairro());
        assertEquals("Cidade C", endereco.cidade());
        assertEquals("Estado D", endereco.estado());
        assertEquals(validCEP, endereco.cep());
    }

    @Test
    void shouldCreateValidEnderecoWithoutComplement() {
        Endereco endereco = new Endereco(
                "Rua A",
                "123",
                null,
                "Bairro B",
                "Cidade C",
                "Estado D",
                validCEP
        );
        assertNotNull(endereco);
        assertEquals("Rua A", endereco.rua());
        assertEquals("123", endereco.numero());
        assertNull(endereco.complemento());
        assertEquals("Bairro B", endereco.bairro());
        assertEquals("Cidade C", endereco.cidade());
        assertEquals("Estado D", endereco.estado());
        assertEquals(validCEP, endereco.cep());
    }

    @Test
    void shouldThrowExceptionWhenStreetIsNull() {
        assertThrows(EnderecoCampoNuloException.class, () ->
                new Endereco(null, "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNumberIsNull() {
        assertThrows(EnderecoCampoNuloException.class, () ->
                new Endereco("Rua A", null, "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNeighborhoodIsNull() {
        assertThrows(EnderecoCampoNuloException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", null, "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCityIsNull() {
        assertThrows(EnderecoCampoNuloException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", "Bairro B", null, "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStateIsNull() {
        assertThrows(EnderecoCampoNuloException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", null, validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCEPIsNull() {
        assertThrows(NullPointerException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", null));
    }

    @Test
    void shouldThrowExceptionWhenStreetTooLong() {
        String longStreet = "A".repeat(Endereco.TAMANHO_MAXIMO + 1);
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco(longStreet, "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNumberTooLong() {
        String longNumber = "9".repeat(Endereco.TAMANHO_MAXIMO + 1);
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", longNumber, "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenComplementTooLong() {
        String longComplement = "C".repeat(Endereco.TAMANHO_MAXIMO + 1);
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", longComplement, "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNeighborhoodTooLong() {
        String longNeighborhood = "N".repeat(Endereco.TAMANHO_MAXIMO + 1);
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", longNeighborhood, "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCityTooLong() {
        String longCity = "C".repeat(Endereco.TAMANHO_MAXIMO + 1);
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", "Bairro B", longCity, "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStateTooLong() {
        String longState = "S".repeat(Endereco.TAMANHO_MAXIMO + 1);
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", longState, validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStreetTooShort() {
        String longStreet = "";
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco(longStreet, "123", "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNumberTooShort() {
        String longNumber = "";
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", longNumber, "Apto 1", "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenComplementTooShort() {
        String longComplement = "";
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", longComplement, "Bairro B", "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenNeighborhoodTooShort() {
        String longNeighborhood = "";
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", longNeighborhood, "Cidade C", "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenCityTooShort() {
        String longCity = "";
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", "Bairro B", longCity, "Estado D", validCEP));
    }

    @Test
    void shouldThrowExceptionWhenStateTooShort() {
        String longState = "";
        assertThrows(EnderecoCampoTamanhoInvalidoException.class, () ->
                new Endereco("Rua A", "123", "Apto 1", "Bairro B", "Cidade C", longState, validCEP));
    }
}
