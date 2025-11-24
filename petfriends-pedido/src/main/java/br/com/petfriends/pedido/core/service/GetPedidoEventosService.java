package br.com.petfriends.pedido.core.service;

import br.com.petfriends.pedido.core.port.in.GetPedidoEventosUseCase;
import br.com.petfriends.pedido.core.query.BuscarPedidoEventosPeloIdQuery;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetPedidoEventosService implements GetPedidoEventosUseCase {
    private final EventStore eventStore;

    public GetPedidoEventosService(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public List<Object> getEventos(BuscarPedidoEventosPeloIdQuery query) {
        return eventStore.readEvents(query.getId().toString(), 0)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
