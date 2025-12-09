package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.command.*;
import br.com.petfriends.transporte.core.event.*;
import br.com.petfriends.transporte.core.exception.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.axonframework.test.matchers.Matchers.sequenceOf;

class EntregaTest {
    private FixtureConfiguration<Entrega> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Entrega.class);
    }

    @Test
    void deveCriarEntregaComSucesso() {
        List<CriarRemessaCommand> remessas = List.of(
                new CriarRemessaCommand("almox-1", "item-1", 2),
                new CriarRemessaCommand("almox-2", "item-2", 1)
        );

        CriarEntregaCommand command = new CriarEntregaCommand("pedido-123", remessas);

        fixture.givenNoPriorActivity()
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(sequenceOf(
                        messageWithPayload(Matchers.instanceOf(EntregaCriadaEvent.class))
                ));
    }

    @Test
    void deveDarErroQuandoPedidoIdEstaNulo() {
        CriarEntregaCommand command = new CriarEntregaCommand(null, List.of(
                new CriarRemessaCommand("almox-1", "item-1", 1)
        ));

        fixture.givenNoPriorActivity()
                .when(command)
                .expectException(PedidoIdentificadorNuloException.class);
    }

    @Test
    void deveIniciarEntrega() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1))))
                        )
                )
                .when(new IniciarEntregaCommand(id))
                .expectEvents(new EntregaIniciadaEvent(id, instante));
    }

    @Test
    void deveDarErroAoIniciarEntregaComStatusInvalido() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        ),
                        new EntregaIniciadaEvent(id, instante)
                )
                .when(new IniciarEntregaCommand(id))
                .expectException(EntregaNaoInicializavelException.class);
    }

    @Test
    void deveDarErroAoSepararRemessaEmEstadoInvalido() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        ),
                        new EntregaIniciadaEvent(id, instante)
                )
                .when(new SepararRemessaCommand(id, "almox-2"))
                .expectException(RemessaNaoEncontradaException.class);
    }

    @Test
    void deveSepararRemessaComSucesso() {
        UUID entregaId = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(entregaId, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        ),
                        new EntregaIniciadaEvent(entregaId, instante)
                )
                .when(new SepararRemessaCommand(entregaId, "almox-1"))
                .expectEventsMatching(sequenceOf(
                        messageWithPayload(Matchers.instanceOf(RemessaSeparadaEvent.class)),
                        messageWithPayload(Matchers.instanceOf(EntregaSeparadaEvent.class))
                ));
    }

    @Test
    void deveDarErroAoSepararRemessaJaSeparada() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        ),
                        new EntregaIniciadaEvent(id, instante),
                        new RemessaSeparadaEvent(id, instante, "almox-1"),
                        new EntregaSeparadaEvent(id, instante)
                )
                .when(new SepararRemessaCommand(id, "almox-1"))
                .expectException(EntregaNaoIniciadaException.class);
    }

    @Test
    void deveTransportarRemessaComSucesso() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        ),
                        new EntregaIniciadaEvent(id, instante),
                        new RemessaSeparadaEvent(id, instante, "almox-1"),
                        new EntregaSeparadaEvent(id, instante)
                )
                .when(new TransportarRemessaCommand(id, "almox-1"))
                .expectEventsMatching(sequenceOf(
                        messageWithPayload(Matchers.instanceOf(RemessaTransportadaEvent.class)),
                        messageWithPayload(Matchers.instanceOf(EntregaTransportadaEvent.class))
                ));
    }

    @Test
    void deveEntregarRemessaComSucesso() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        ),
                        new EntregaIniciadaEvent(id, instante),
                        new RemessaSeparadaEvent(id, instante, "almox-1"),
                        new EntregaSeparadaEvent(id, instante),
                        new RemessaTransportadaEvent(id, instante, "almox-1"),
                        new EntregaTransportadaEvent(id, instante)
                )
                .when(new EntregarRemessaCommand(id, "almox-1"))
                .expectEventsMatching(sequenceOf(
                        messageWithPayload(Matchers.instanceOf(RemessaEntregueEvent.class)),
                        messageWithPayload(Matchers.instanceOf(EntregaEntregueEvent.class))
                ));
    }

    @Test
    void deveDarErroAoEntregarRemessaNaoEncontrada() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        )
                )
                .when(new EntregarRemessaCommand(id, "almox-inexistente"))
                .expectException(RemessaNaoEncontradaException.class);
    }

    @Test
    void deveCriarOcorrenciaRemessa() {
        UUID id = UUID.randomUUID();
        Instant instante = Instant.now();

        fixture.given(
                        new EntregaCriadaEvent(id, instante, "pedido-1",
                                List.of(new Remessa("almox-1", RemessaStatus.EM_SEPARACAO,
                                        List.of(new ItemRemessa("item-1", 1)))
                                )
                        ),
                        new EntregaIniciadaEvent(id, instante),
                        new RemessaSeparadaEvent(id, instante, "almox-1"),
                        new EntregaSeparadaEvent(id, instante),
                        new RemessaTransportadaEvent(id, instante, "almox-1"),
                        new EntregaTransportadaEvent(id, instante)
                )
                .when(new CriarOcorrenciaRemessaCommand(id, "almox-1"))
                .expectEventsMatching(sequenceOf(
                        messageWithPayload(Matchers.instanceOf(RemessaOcorrenciaCriadaEvent.class))
                ));
    }
}
