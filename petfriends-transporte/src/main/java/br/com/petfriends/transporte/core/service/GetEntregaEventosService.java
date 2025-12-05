package br.com.petfriends.transporte.core.service;

import br.com.petfriends.transporte.core.port.in.GetEntregaEventosUseCase;
import br.com.petfriends.transporte.core.query.BuscarEntregaEventosPeloIdQuery;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetEntregaEventosService implements GetEntregaEventosUseCase {
    private final EventStore eventStore;

    public GetEntregaEventosService(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public List<Object> getEventos(BuscarEntregaEventosPeloIdQuery query) {
        return eventStore.readEvents(query.getId().toString(), 0)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
