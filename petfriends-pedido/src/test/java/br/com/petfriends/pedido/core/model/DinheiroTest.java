package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.DinheiroNegativoException;
import br.com.petfriends.pedido.core.exception.DinheiroNuloException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DinheiroTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "0",
            "10",
            "10.5",
            "10.567",
            "999999.99"
    })
    void deveCriarDinheiroValido(String value) {
        BigDecimal big = new BigDecimal(value);
        Dinheiro dinheiro = new Dinheiro(big);

        assertEquals(big.setScale(2, RoundingMode.HALF_UP), dinheiro.valor());
    }

    @Test
    void deveDarExcecaoComDinheiroNulo() {
        assertThrows(DinheiroNuloException.class, () -> new Dinheiro(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-0.01",
            "-5",
            "-100.99"
    })
    void deveDarExcecaoComDinheiroNegativo(String value) {
        BigDecimal big = new BigDecimal(value);
        assertThrows(DinheiroNegativoException.class, () -> new Dinheiro(big));
    }
}
