package br.com.petfriends.almoxarifado.core.event;

import java.time.Instant;

public final class PedidoReservaFalhouEvent extends BaseEvent<String> {
    public PedidoReservaFalhouEvent(String id, Instant timestamp) {
        super(id, timestamp);
    }
}
