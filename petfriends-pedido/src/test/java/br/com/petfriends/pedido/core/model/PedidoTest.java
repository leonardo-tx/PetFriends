package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.command.*;
import br.com.petfriends.pedido.core.event.*;
import br.com.petfriends.pedido.core.exception.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.axonframework.test.matchers.Matchers.sequenceOf;

class PedidoTest {

    private FixtureConfiguration<Pedido> fixture;

    @BeforeEach
    void setupBeforeEach() {
        fixture = new AggregateTestFixture<>(Pedido.class);
    }

    @Test
    void deveCriarPedidoComSucesso() {
        List<AdicionarItemPedidoCommand> itens = List.of(
                new AdicionarItemPedidoCommand("prod-1", new BigDecimal("10.50"), 2),
                new AdicionarItemPedidoCommand("prod-2", new BigDecimal("5.00"), 1)
        );

        CriarPedidoCommand command = new CriarPedidoCommand(
                "cliente-123",
                "Rua 1",
                "100",
                "Bloco A",
                "Centro",
                "São Paulo",
                "SP",
                "01001000",
                itens
        );

        fixture.givenNoPriorActivity()
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(sequenceOf(
                        messageWithPayload(Matchers.instanceOf(PedidoCriadoEvent.class))
                ));
    }

    @Test
    void deveDarErroQuandoClienteIdEstaNulo() {
        CriarPedidoCommand command = new CriarPedidoCommand(
                null,
                "Rua 1",
                "100",
                "Bloco A",
                "Centro",
                "São Paulo",
                "SP",
                "01001000",
                List.of(new AdicionarItemPedidoCommand("prod-1", BigDecimal.TEN, 1))
        );

        fixture.givenNoPriorActivity()
                .when(command)
                .expectException(ClienteIdentificadorNuloException.class);
    }

    @Test
    void deveIniciarPedido() {
        UUID id = UUID.randomUUID();
        fixture.given(new PedidoCriadoEvent(
                        id,
                        Instant.now(),
                        "cliente-1",
                        null,
                        List.of()
                ))
                .when(new IniciarPedidoCommand(id))
                .expectEvents(new PedidoIniciadoEvent(id, Instant.now()));
    }

    @Test
    void deveDarErroAoIniciarPedidoComStatusInvalido() {
        UUID id = UUID.randomUUID();

        fixture.given(
                        new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()),
                        new PedidoIniciadoEvent(id, Instant.now())
                )
                .when(new IniciarPedidoCommand(id))
                .expectException(PedidoNaoInicializavelException.class);
    }

    @Test
    void deveCancelarPedidoCriado() {
        UUID id = UUID.randomUUID();

        fixture.given(new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()))
                .when(new CancelarPedidoCommand(id))
                .expectEvents(new PedidoCanceladoEvent(id, Instant.now()));
    }

    @Test
    void deveCancelarPedidoIniciado() {
        UUID id = UUID.randomUUID();

        fixture.given(
                        new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()),
                        new PedidoIniciadoEvent(id, Instant.now())
                )
                .when(new CancelarPedidoCommand(id))
                .expectEvents(new PedidoCanceladoEvent(id, Instant.now()));
    }

    @Test
    void deveDarErroAoCancelarPedidoEmEstadoInvalido() {
        UUID id = UUID.randomUUID();

        fixture.given(
                        new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()),
                        new PedidoIniciadoEvent(id, Instant.now()),
                        new PedidoPagoEvent(id, Instant.now())
                )
                .when(new CancelarPedidoCommand(id))
                .expectException(PedidoIncancelavelException.class);
    }

    @Test
    void devePagarPedido() {
        UUID id = UUID.randomUUID();

        fixture.given(
                        new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()),
                        new PedidoIniciadoEvent(id, Instant.now())
                )
                .when(new PagarPedidoCommand(id))
                .expectEvents(new PedidoPagoEvent(id, Instant.now()));
    }

    @Test
    void deveDarErroAoPagarPedidoSeNaoEstiverIniciado() {
        UUID id = UUID.randomUUID();

        fixture.given(new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()))
                .when(new PagarPedidoCommand(id))
                .expectException(PedidoImpagavelException.class);
    }

    @Test
    void deveSepararPedido() {
        UUID id = UUID.randomUUID();

        fixture.given(
                        new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()),
                        new PedidoIniciadoEvent(id, Instant.now()),
                        new PedidoPagoEvent(id, Instant.now())
                )
                .when(new SepararPedidoCommand(id))
                .expectEvents(new PedidoSeparadoEvent(id, Instant.now()));
    }

    @Test
    void deveDarErroAoSepararPedidoEmEstadoInvalido() {
        UUID id = UUID.randomUUID();

        fixture.given(new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()))
                .when(new SepararPedidoCommand(id))
                .expectException(PedidoInseparavelException.class);
    }

    @Test
    void deveEnviarPedido() {
        UUID id = UUID.randomUUID();

        fixture.given(
                        new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()),
                        new PedidoIniciadoEvent(id, Instant.now()),
                        new PedidoPagoEvent(id, Instant.now()),
                        new PedidoSeparadoEvent(id, Instant.now())
                )
                .when(new EnviarPedidoCommand(id))
                .expectEvents(new PedidoEnviadoEvent(id, Instant.now()));
    }

    @Test
    void deveDarErroAoEnviarPedidoEmEstadoInvalido() {
        UUID id = UUID.randomUUID();

        fixture.given(new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()))
                .when(new EnviarPedidoCommand(id))
                .expectException(PedidoIneviavelException.class);
    }

    @Test
    void deveEntregarPedido() {
        UUID id = UUID.randomUUID();

        fixture.given(
                        new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()),
                        new PedidoIniciadoEvent(id, Instant.now()),
                        new PedidoPagoEvent(id, Instant.now()),
                        new PedidoSeparadoEvent(id, Instant.now()),
                        new PedidoEnviadoEvent(id, Instant.now())
                )
                .when(new EntregarPedidoCommand(id))
                .expectEvents(new PedidoEntregueEvent(id, Instant.now()));
    }

    @Test
    void deveDarErroAoEntregarPedidoEmEstadoInvalido() {
        UUID id = UUID.randomUUID();

        fixture.given(new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()))
                .when(new EntregarPedidoCommand(id))
                .expectException(PedidoIntregavelException.class);
    }

    @Test
    void deveAdicionarEntregaAoPedido() {
        UUID id = UUID.randomUUID();

        fixture.given(new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()))
                .when(new AdicionarEntregaAoPedidoCommand(id, "entrega-123"))
                .expectEvents(new EntregaAdicionadaAoPedidoEvent(id, Instant.now(), "entrega-123"));
    }

    @Test
    void deveDarErroAoAdicionarEntregaNula() {
        UUID id = UUID.randomUUID();

        fixture.given(new PedidoCriadoEvent(id, Instant.now(), "c", null, List.of()))
                .when(new AdicionarEntregaAoPedidoCommand(id, null))
                .expectException(EntregaIdentificadorNuloException.class);
    }
}
