package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.exception.ItemIdentificadorNuloException;
import br.com.petfriends.transporte.core.exception.ItemRemessaQuantidadeInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemRemessaTest {
    @Test
    void deveCriarItemRemessaValido() {
        ItemRemessa item = new ItemRemessa("item-1", 5);

        assertEquals("item-1", item.getItemId());
        assertEquals(5, item.getQuantidade());
    }

    @Test
    void deveLancarExcecaoQuandoItemIdForNulo() {
        assertThrows(ItemIdentificadorNuloException.class, () -> new ItemRemessa(null, 5));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForMenorQueMinima() {
        assertThrows(ItemRemessaQuantidadeInvalidaException.class, () -> new ItemRemessa("item-1", 0));
    }
}
