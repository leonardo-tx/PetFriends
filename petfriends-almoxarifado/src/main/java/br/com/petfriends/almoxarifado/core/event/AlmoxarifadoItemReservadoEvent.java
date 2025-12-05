package br.com.petfriends.almoxarifado.core.event;

import java.time.Instant;
import java.util.UUID;

public final class AlmoxarifadoItemReservadoEvent extends BaseEvent<UUID> {
    private final String itemId;
    private final Integer quantidade;

    public AlmoxarifadoItemReservadoEvent(UUID id, Instant timestamp, String itemId, Integer quantidade) {
        super(id, timestamp);
        this.itemId = itemId;
        this.quantidade = quantidade;
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
