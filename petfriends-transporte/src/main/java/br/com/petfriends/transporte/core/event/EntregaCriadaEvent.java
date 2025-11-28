package br.com.petfriends.transporte.core.event;

import br.com.petfriends.transporte.core.model.Endereco;

import java.time.Instant;
import java.util.UUID;

public class EntregaCriadaEvent extends BaseEvent<UUID> {
    private final String pedidoId;
    private final Endereco endereco;

    public EntregaCriadaEvent(UUID id, Instant timestamp, String pedidoId, Endereco endereco) {
        super(id, timestamp);
        this.pedidoId = pedidoId;
        this.endereco = endereco;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
