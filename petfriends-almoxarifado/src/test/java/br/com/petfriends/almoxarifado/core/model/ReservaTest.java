package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.ItemQuantidadeNegativoException;
import br.com.petfriends.almoxarifado.core.exception.PedidoIdentificadorNuloException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {
    @Test
    void deveCriarReservaValida() {
        Reserva reserva = new Reserva("pedido123", 5);

        assertEquals("pedido123", reserva.getPedidoId());
        assertEquals(5, reserva.getQuantidade());
    }

    @Test
    void deveLancarExcecaoQuandoPedidoIdNulo() {
        assertThrows(PedidoIdentificadorNuloException.class, () -> new Reserva(null, 5));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    void deveLancarExcecaoQuandoQuantidadeNegativa(int quantidadeInvalida) {
        assertThrows(ItemQuantidadeNegativoException.class, () -> new Reserva("pedido123", quantidadeInvalida));
    }
}
