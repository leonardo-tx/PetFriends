package br.com.petfriends.almoxarifado.core.event;

import java.time.Instant;
import java.util.UUID;

public final class AlmoxarifadoItemAdicionadoEvent extends BaseEvent<UUID> {
    private final String itemId;
    private final int quantidade;

    public AlmoxarifadoItemAdicionadoEvent(UUID id, Instant timestamp, String itemId, int quantidade) {
        super(id, timestamp);
        this.itemId = itemId;
        this.quantidade = quantidade;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
