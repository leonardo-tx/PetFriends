package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.ItemPedidoQuantidadeInvalidaException;
import br.com.petfriends.pedido.core.exception.ItemPedidoQuantidadeNuloException;
import br.com.petfriends.pedido.core.exception.ProdutoIdentificadorNuloException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemPedidoTest {

    @ParameterizedTest
    @CsvSource({
            "produto-1, 10.50, 1",
            "abc, 0, 5",
            "xyz, 99.99, 10"
    })
    void deveCriarItemPedidoValido(String produtoId, BigDecimal valor, int quantidade) {
        Dinheiro dinheiro = new Dinheiro(valor);

        ItemPedido item = new ItemPedido(produtoId, dinheiro, quantidade);

        assertEquals(produtoId, item.getProdutoId());
        assertEquals(dinheiro, item.getValorUnitario());
        assertEquals(quantidade, item.getQuantidade());
    }

    @Test
    void deveDarExcecaoComQuantidadeNula() {
        Dinheiro dinheiro = new Dinheiro(BigDecimal.TEN);
        assertThrows(
                ItemPedidoQuantidadeNuloException.class,
                () -> new ItemPedido("produto-1", dinheiro, null)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void deveDarExcecaoComQuantidadeInvalida(int quantidadeInvalida) {
        Dinheiro dinheiro = new Dinheiro(BigDecimal.ONE);
        assertThrows(
                ItemPedidoQuantidadeInvalidaException.class,
                () -> new ItemPedido("produto-1", dinheiro, quantidadeInvalida)
        );
    }

    @Test
    void deveDarExcecaoComProdutoIdNulo() {
        Dinheiro dinheiro = new Dinheiro(BigDecimal.ONE);
        assertThrows(
                ProdutoIdentificadorNuloException.class,
                () -> new ItemPedido(null, dinheiro, 1)
        );
    }
}
