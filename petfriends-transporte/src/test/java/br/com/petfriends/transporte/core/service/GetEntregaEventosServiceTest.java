package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.query.BuscarEntregaEventosPeloIdQuery;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.GenericMessage;
import org.axonframework.messaging.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEntregaEventosServiceTest {
    @Mock
    private EventStore eventStore;

    @InjectMocks
    private GetEntregaEventosService service;

    @Test
    void deveRetornarEventosDaEntrega() {
        UUID entregaId = UUID.randomUUID();
        BuscarEntregaEventosPeloIdQuery query = new BuscarEntregaEventosPeloIdQuery(entregaId);

        DomainEventMessage<Object> evento1 = new GenericDomainEventMessage<>(
                "Entrega",
                entregaId.toString(),
                0,
                new Object()
        );
        DomainEventMessage<Object> evento2 = new GenericDomainEventMessage<>(
                "Entrega",
                entregaId.toString(),
                1,
                new Object()
        );

        DomainEventStream eventStream = mock(DomainEventStream.class);
        when(eventStream.asStream()).thenReturn((Stream)Stream.of(evento1, evento2));

        when(eventStore.readEvents(entregaId.toString(), 0)).thenReturn(eventStream);

        List<Object> eventos = service.getEventos(query);

        assertEquals(2, eventos.size());
        assertSame(evento1.getPayload(), eventos.get(0));
        assertSame(evento2.getPayload(), eventos.get(1));
    }
}
