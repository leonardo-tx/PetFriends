package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.CEPNuloException;
import br.com.petfriends.pedido.core.exception.CEPTamanhoInvalidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CEPTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "123456-78",
            "51051403",
            "20040002",
            "227258-91",
            "699327-01"
    })
    void deveCriarCEPValido(String value) {
        CEP cep = new CEP(value);
        assertEquals(value.replaceAll("\\D", ""), cep.valor());
    }

    @Test
    void deveDarExcecaoComCEPNulo() {
        assertThrows(CEPNuloException.class, () -> new CEP(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1234567", "123456789", "1234567890"})
    void deveDarExcecaoComCEPTamanhoInvalido(String invalidValue) {
        assertThrows(CEPTamanhoInvalidoException.class, () -> new CEP(invalidValue));
    }
}
