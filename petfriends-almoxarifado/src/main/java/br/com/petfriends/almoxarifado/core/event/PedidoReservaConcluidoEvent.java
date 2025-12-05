package br.com.petfriends.almoxarifado.core.event;

import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoReserva;

import java.time.Instant;
import java.util.List;

public final class PedidoReservaConcluidoEvent extends BaseEvent<String> {
    private final List<AlmoxarifadoReserva> almoxarifadoReservas;

    public PedidoReservaConcluidoEvent(String id, Instant timestamp, List<AlmoxarifadoReserva> almoxarifadoReservas) {
        super(id, timestamp);
        this.almoxarifadoReservas = almoxarifadoReservas;
    }

    public List<AlmoxarifadoReserva> getAlmoxarifadoReservas() {
        return almoxarifadoReservas;
    }
}
