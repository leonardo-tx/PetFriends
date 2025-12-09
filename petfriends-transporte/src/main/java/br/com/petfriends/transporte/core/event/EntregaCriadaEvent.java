package br.com.petfriends.transporte.core.event;

import br.com.petfriends.transporte.core.model.Remessa;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class EntregaCriadaEvent extends BaseEvent<UUID> {
    private final String pedidoId;
    private final List<Remessa> remessas;

    public EntregaCriadaEvent(UUID id, Instant timestamp, String pedidoId, List<Remessa> remessas) {
        super(id, timestamp);
        this.pedidoId = pedidoId;
        this.remessas = remessas;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public List<Remessa> getRemessas() {
        return remessas;
    }
}
