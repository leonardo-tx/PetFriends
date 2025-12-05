package br.com.petfriends.almoxarifado.core.port.in;

import br.com.petfriends.almoxarifado.core.command.ReservarPedidoCommand;
import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoReserva;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ReservarPedidoUseCase {
    CompletableFuture<List<AlmoxarifadoReserva>> reservar(ReservarPedidoCommand command);
}
