package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.command.*;
import br.com.petfriends.almoxarifado.core.event.*;
import br.com.petfriends.almoxarifado.core.exception.ItemNaoEncontradoException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.axonframework.test.matchers.Matchers.sequenceOf;

class AlmoxarifadoTest {
    private FixtureConfiguration<Almoxarifado> fixture;

    @BeforeEach
    void setupBeforeEach() {
        fixture = new AggregateTestFixture<>(Almoxarifado.class);
    }

    @Test
    void deveCriarAlmoxarifado() {
        String nome = "Almoxarifado Central";
        fixture.givenNoPriorActivity()
                .when(new CriarAlmoxarifadoCommand(nome))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(sequenceOf(
                        messageWithPayload(Matchers.instanceOf(AlmoxarifadoCriadoEvent.class))
                ));
    }

    @Test
    void deveAdicionarItemNoEstoque() {
        UUID almoxarifadoId = UUID.randomUUID();
        String nome = "Almoxarifado Central";
        String itemId = "item-123";

        fixture.given(new AlmoxarifadoCriadoEvent(almoxarifadoId, Instant.now(), new AlmoxarifadoNome(nome)))
                .when(new AlmoxarifadoAdicionarItemCommand(almoxarifadoId, itemId, 10))
                .expectEvents(new AlmoxarifadoItemAdicionadoEvent(almoxarifadoId, Instant.now(), itemId, 10));
    }

    @Test
    void deveReservarItem() {
        UUID almoxarifadoId = UUID.randomUUID();
        String nome = "Almoxarifado Central";
        String itemId = "item-123";
        String pedidoId = "pedido-1";

        fixture.given(
                new AlmoxarifadoCriadoEvent(almoxarifadoId, Instant.now(), new AlmoxarifadoNome(nome)),
                new AlmoxarifadoItemAdicionadoEvent(almoxarifadoId, Instant.now(), itemId, 20)
        )
                .when(new AlmoxarifadoReservarItemCommand(almoxarifadoId, pedidoId, itemId, 5))
                .expectEvents(new AlmoxarifadoItemReservadoEvent(almoxarifadoId, Instant.now(), pedidoId, itemId, 5));
    }

    @Test
    void deveLiberarReserva() {
        UUID almoxarifadoId = UUID.randomUUID();
        String pedidoId = "pedido-1";

        fixture.given(new AlmoxarifadoCriadoEvent(almoxarifadoId, Instant.now(), new AlmoxarifadoNome("Almoxarifado Central")))
                .when(new AlmoxarifadoLiberarReservaCommand(almoxarifadoId, pedidoId))
                .expectEvents(new AlmoxarifadoReservaLiberadaEvent(almoxarifadoId, Instant.now(), pedidoId));
    }

    @Test
    void deveConsumirEstoque() {
        UUID almoxarifadoId = UUID.randomUUID();
        String pedidoId = "pedido-1";

        fixture.given(new AlmoxarifadoCriadoEvent(almoxarifadoId, Instant.now(), new AlmoxarifadoNome("Almoxarifado Central")))
                .when(new AlmoxarifadoConsumirEstoqueCommand(almoxarifadoId, pedidoId))
                .expectEvents(new AlmoxarifadoEstoqueConsumidoEvent(almoxarifadoId, Instant.now(), pedidoId));
    }

    @Test
    void deveDarErroAoReservarItemInexistente() {
        UUID almoxarifadoId = UUID.randomUUID();
        String pedidoId = "pedido-1";
        String itemId = "item-999";

        fixture.given(new AlmoxarifadoCriadoEvent(almoxarifadoId, Instant.now(), new AlmoxarifadoNome("Almoxarifado Central")))
                .when(new AlmoxarifadoReservarItemCommand(almoxarifadoId, pedidoId, itemId, 5))
                .expectException(ItemNaoEncontradoException.class);
    }
}
