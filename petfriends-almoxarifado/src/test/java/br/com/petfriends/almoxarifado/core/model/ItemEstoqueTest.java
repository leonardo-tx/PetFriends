package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.ItemIdentificadorNuloException;
import br.com.petfriends.almoxarifado.core.exception.ItemQuantidadeNegativoException;
import br.com.petfriends.almoxarifado.core.exception.ItemQuantidadeReservadaInvalidaException;
import br.com.petfriends.almoxarifado.core.exception.PedidoIdentificadorNuloException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ItemEstoqueTest {

    @Test
    void deveCriarItemEstoqueComConstrutorCompleto() {
        Map<String, Reserva> reservas = new HashMap<>();
        reservas.put("pedido1", new Reserva("pedido1", 2));

        ItemEstoque item = new ItemEstoque("item1", 10, 2, reservas);

        assertEquals("item1", item.getItemId());
        assertEquals(10, item.getQuantidadeDisponivel());
        assertEquals(2, item.getQuantidadeReservada());
        assertEquals(reservas, item.getReservas());
    }

    @Test
    void deveCriarItemEstoqueComConstrutorSimples() {
        ItemEstoque item = new ItemEstoque("item1");

        assertEquals("item1", item.getItemId());
        assertEquals(0, item.getQuantidadeDisponivel());
        assertEquals(0, item.getQuantidadeReservada());
        assertTrue(item.getReservas().isEmpty());
    }

    @Test
    void deveLancarExcecaoComItemIdNulo() {
        assertThrows(ItemIdentificadorNuloException.class, () -> new ItemEstoque(null));
        assertThrows(ItemIdentificadorNuloException.class,
                () -> new ItemEstoque(null, 10, 0, new HashMap<>()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10})
    void deveLancarExcecaoQuandoQuantidadesNegativas(int quantidade) {
        assertThrows(ItemQuantidadeNegativoException.class,
                () -> new ItemEstoque("item1", quantidade, 0, new HashMap<>()));
        assertThrows(ItemQuantidadeNegativoException.class,
                () -> new ItemEstoque("item1", 0, quantidade, new HashMap<>()));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeReservadaMaiorQueDisponivel() {
        assertThrows(ItemQuantidadeReservadaInvalidaException.class,
                () -> new ItemEstoque("item1", 5, 10, new HashMap<>()));
    }

    @Test
    void deveAdicionarReservaCorretamente() {
        ItemEstoque item = new ItemEstoque("item1", 10, 0, new HashMap<>());
        item.adicionarReserva("pedido1", 5);

        assertEquals(5, item.getQuantidadeReservada());
        assertTrue(item.getReservas().containsKey("pedido1"));
        assertEquals(5, item.getReservas().get("pedido1").getQuantidade());
    }

    @Test
    void deveLiberarReservaCorretamente() {
        ItemEstoque item = new ItemEstoque("item1", 10, 0, new HashMap<>());
        item.adicionarReserva("pedido1", 5);
        item.liberarReserva("pedido1");

        assertEquals(0, item.getQuantidadeReservada());
        assertTrue(item.getReservas().isEmpty());
    }

    @Test
    void deveConsumirEstoqueCorretamente() {
        ItemEstoque item = new ItemEstoque("item1", 10, 0, new HashMap<>());
        item.adicionarReserva("pedido1", 5);
        item.consumirEstoque("pedido1");

        assertEquals(5, item.getQuantidadeDisponivel());
        assertEquals(0, item.getQuantidadeReservada());
        assertTrue(item.getReservas().isEmpty());
    }

    @Test
    void deveAdicionarEstoqueCorretamente() {
        ItemEstoque item = new ItemEstoque("item1");
        item.adicionarEstoque(10);
        assertEquals(10, item.getQuantidadeDisponivel());
    }

    @Test
    void equalsEstaBaseadoNoItemId() {
        ItemEstoque item1 = new ItemEstoque("item1");
        ItemEstoque item2 = new ItemEstoque("item1");
        ItemEstoque item3 = new ItemEstoque("item2");

        assertEquals(item1, item2);
        assertNotEquals(item1, item3);
    }

    @Test
    void hashCodeEstaBaseadoNoItemId() {
        ItemEstoque item1 = new ItemEstoque("item1");
        ItemEstoque item2 = new ItemEstoque("item1");

        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void deveLancarExcecaoSePedidoIdNuloNasReservas() {
        ItemEstoque item = new ItemEstoque("item1", 10, 0, new HashMap<>());
        assertThrows(PedidoIdentificadorNuloException.class,
                () -> item.adicionarReserva(null, 1));
        assertThrows(PedidoIdentificadorNuloException.class,
                () -> item.verificarAdicionarReserva(null, 1));
        assertThrows(PedidoIdentificadorNuloException.class,
                () -> item.verificarConsumirEstoque(null));
        assertThrows(PedidoIdentificadorNuloException.class,
                () -> item.verificarLiberarReserva(null));
    }

    @Test
    void deveLancarExcecaoAoConsumirEstoqueSemReserva() {
        ItemEstoque item = new ItemEstoque("item1", 5, 0, new HashMap<>());
        item.consumirEstoque("pedido1");
        assertEquals(5, item.getQuantidadeDisponivel());
        assertEquals(0, item.getQuantidadeReservada());
    }

    @Test
    void verificarAdicionarReservaDeveLancarExcecaoQuandoQuantidadeNaoPositiva() {
        ItemEstoque item = new ItemEstoque("item1", 10, 0, new HashMap<>());

        assertThrows(ItemQuantidadeNegativoException.class, () -> item.verificarAdicionarReserva("pedido1", 0));
        assertThrows(ItemQuantidadeNegativoException.class, () -> item.verificarAdicionarReserva("pedido1", -5));
    }

    @Test
    void verificarConsumirEstoqueSemReservaNaoLancaExcecao() {
        ItemEstoque item = new ItemEstoque("item1", 5, 0, new HashMap<>());
        assertDoesNotThrow(() -> item.verificarConsumirEstoque("pedido1"));
    }

    @Test
    void equalsDeveRetornarFalsoQuandoObjNuloOuOutraClasse() {
        ItemEstoque item = new ItemEstoque("item1");

        assertNotEquals(null, item);
        assertNotEquals("uma string qualquer", item);
    }

    @Test
    void equalsDeveRetornarVerdadeiroQuandoMesmoItemId() {
        ItemEstoque item1 = new ItemEstoque("item1");
        ItemEstoque item2 = new ItemEstoque("item1");

        assertEquals(item1, item2);
    }

    @Test
    void equalsDeveRetornarFalsoQuandoItemIdDiferente() {
        ItemEstoque item1 = new ItemEstoque("item1");
        ItemEstoque item2 = new ItemEstoque("item2");

        assertNotEquals(item1, item2);
    }
}
