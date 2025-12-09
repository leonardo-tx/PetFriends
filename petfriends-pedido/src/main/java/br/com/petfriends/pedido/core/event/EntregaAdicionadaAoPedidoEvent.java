package br.com.petfriends.pedido.core.event;

import java.time.Instant;
import java.util.UUID;

public final class EntregaAdicionadaAoPedidoEvent extends BaseEvent<UUID> {
    private final String entregaId;

    public EntregaAdicionadaAoPedidoEvent(UUID id, Instant timestamp, String entregaId) {
        super(id, timestamp);
        this.entregaId = entregaId;
    }

    public String getEntregaId() {
        return entregaId;
    }
}
