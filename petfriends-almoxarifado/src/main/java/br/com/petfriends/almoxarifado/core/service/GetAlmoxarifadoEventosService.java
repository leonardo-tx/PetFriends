package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.port.in.GetAlmoxarifadoEventosUseCase;
import br.com.petfriends.almoxarifado.core.query.BuscarAlmoxarifadoEventosPeloIdQuery;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAlmoxarifadoEventosService implements GetAlmoxarifadoEventosUseCase {
    private final EventStore eventStore;

    public GetAlmoxarifadoEventosService(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public List<Object> getEventos(BuscarAlmoxarifadoEventosPeloIdQuery query) {
        return eventStore.readEvents(query.getId().toString(), 0)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
