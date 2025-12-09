package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.exception.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RemessaTest {
    @Test
    void deveCriarRemessaValida() {
        ItemRemessa item = new ItemRemessa("item-1", 5);
        Remessa remessa = new Remessa("almox-1", RemessaStatus.EM_SEPARACAO, List.of(item));

        assertEquals("almox-1", remessa.getAlmoxarifadoId());
        assertEquals(RemessaStatus.EM_SEPARACAO, remessa.getStatus());
        assertEquals(1, remessa.getItems().size());
        assertEquals(item, remessa.getItems().get(0));
    }

    @Test
    void deveLancarExcecaoQuandoAlmoxarifadoIdForNulo() {
        ItemRemessa item = new ItemRemessa("item-1", 5);
        assertThrows(AlmoxarifadoIdentificadorNuloException.class,
                () -> new Remessa(null, RemessaStatus.EM_SEPARACAO, List.of(item)));
    }

    @Test
    void deveLancarExcecaoQuandoListaDeItensForNulaOuVazia() {
        assertThrows(RemessaItensVazioException.class,
                () -> new Remessa("almox-1", RemessaStatus.EM_SEPARACAO, null));

        assertThrows(RemessaItensVazioException.class,
                () -> new Remessa("almox-1", RemessaStatus.EM_SEPARACAO, List.of()));
    }

    @Test
    void deveVerificarSeparar() {
        ItemRemessa item = new ItemRemessa("item-1", 5);
        Remessa remessa = new Remessa("almox-1", RemessaStatus.EM_SEPARACAO, List.of(item));

        assertDoesNotThrow(remessa::verificarSeparar);

        remessa = new Remessa("almox-1", RemessaStatus.SEPARADA, List.of(item));
        assertThrows(RemessaInseparavelException.class, remessa::verificarSeparar);
    }

    @Test
    void deveVerificarTransportar() {
        ItemRemessa item = new ItemRemessa("item-1", 5);

        Remessa remessa = new Remessa("almox-1", RemessaStatus.SEPARADA, List.of(item));
        assertDoesNotThrow(remessa::verificarTransportar);

        remessa = new Remessa("almox-1", RemessaStatus.EM_SEPARACAO, List.of(item));
        assertThrows(RemessaIntransportavelException.class, remessa::verificarTransportar);
    }

    @Test
    void deveVerificarEntregar() {
        ItemRemessa item = new ItemRemessa("item-1", 5);

        Remessa remessa = new Remessa("almox-1", RemessaStatus.EM_ROTA_DE_ENTREGA, List.of(item));
        assertDoesNotThrow(remessa::verificarEntregar);

        remessa = new Remessa("almox-1", RemessaStatus.SEPARADA, List.of(item));
        assertThrows(RemessaIntregavelException.class, remessa::verificarEntregar);
    }

    @Test
    void deveVerificarCriarOcorrencia() {
        ItemRemessa item = new ItemRemessa("item-1", 5);

        Remessa remessa = new Remessa("almox-1", RemessaStatus.EM_ROTA_DE_ENTREGA, List.of(item));
        assertDoesNotThrow(remessa::verificarCriarOcorrencia);

        remessa = new Remessa("almox-1", RemessaStatus.SEPARADA, List.of(item));
        assertThrows(RemessaOcorrenciaIncriavelException.class, remessa::verificarCriarOcorrencia);
    }

    @Test
    void deveRealizarTransicoesDeStatus() {
        ItemRemessa item = new ItemRemessa("item-1", 5);
        Remessa remessa = new Remessa("almox-1", RemessaStatus.EM_SEPARACAO, List.of(item));

        remessa.separar();
        assertEquals(RemessaStatus.SEPARADA, remessa.getStatus());

        remessa.transportar();
        assertEquals(RemessaStatus.EM_ROTA_DE_ENTREGA, remessa.getStatus());

        remessa.entregar();
        assertEquals(RemessaStatus.ENTREGUE, remessa.getStatus());
    }

    @Test
    void deveCriarOcorrencia() {
        ItemRemessa item = new ItemRemessa("item-1", 5);
        Remessa remessa = new Remessa("almox-1", RemessaStatus.EM_ROTA_DE_ENTREGA, List.of(item));

        remessa.criarOcorrencia();
        assertEquals(RemessaStatus.OCORRENCIA, remessa.getStatus());
    }
}
