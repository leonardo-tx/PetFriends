package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoLiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoReservarItemCommand;
import br.com.petfriends.almoxarifado.core.command.ReservarItemCommand;
import br.com.petfriends.almoxarifado.core.command.ReservarPedidoCommand;
import br.com.petfriends.almoxarifado.core.event.PedidoReservaConcluidoEvent;
import br.com.petfriends.almoxarifado.core.event.PedidoReservaFalhouEvent;
import br.com.petfriends.almoxarifado.core.exception.AlmoxarifadoNaoEncontradoException;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoReserva;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservarPedidoServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @Mock
    private EventGateway eventGateway;

    @Mock
    private FindAlmoxarifadoPort findAlmoxarifadoPort;

    @InjectMocks
    private ReservarPedidoService service;

    @Test
    void deveReservarComSucesso() {
        String pedidoId = "pedido-1";
        ReservarItemCommand item1 = new ReservarItemCommand("item-1", 5);
        ReservarItemCommand item2 = new ReservarItemCommand("item-2", 3);
        ReservarPedidoCommand command = new ReservarPedidoCommand(
                pedidoId,
                List.of(item1, item2)
        );

        UUID almoxId1 = UUID.randomUUID();
        UUID almoxId2 = UUID.randomUUID();

        Almoxarifado almox1 = mock(Almoxarifado.class);
        when(almox1.getId()).thenReturn(almoxId1);
        Almoxarifado almox2 = mock(Almoxarifado.class);
        when(almox2.getId()).thenReturn(almoxId2);

        when(findAlmoxarifadoPort.findFirstByItemDisponivel("item-1", 5))
                .thenReturn(Optional.of(almox1));
        when(findAlmoxarifadoPort.findFirstByItemDisponivel("item-2", 3))
                .thenReturn(Optional.of(almox2));
        when(commandGateway.sendAndWait(any(AlmoxarifadoReservarItemCommand.class)))
                .thenReturn(null);

        doNothing().when(eventGateway).publish(any(PedidoReservaConcluidoEvent.class));

        List<AlmoxarifadoReserva> resultado = service.reservar(command).join();

        assertEquals(2, resultado.size());
        assertEquals(almoxId1, resultado.get(0).almoxarifadoId());
        assertEquals("item-1", resultado.get(0).itemId());
        assertEquals(5, resultado.get(0).quantidade());

        assertEquals(almoxId2, resultado.get(1).almoxarifadoId());
        assertEquals("item-2", resultado.get(1).itemId());
        assertEquals(3, resultado.get(1).quantidade());

        verify(eventGateway, times(1)).publish(any(PedidoReservaConcluidoEvent.class));
    }

    @Test
    void devePublicarFalhaQuandoAlmoxarifadoNaoEncontrado() {
        String pedidoId = "pedido-1";
        ReservarItemCommand item = new ReservarItemCommand("item-1", 5);
        ReservarPedidoCommand command = new ReservarPedidoCommand(pedidoId, List.of(item));

        when(findAlmoxarifadoPort.findFirstByItemDisponivel("item-1", 5))
                .thenReturn(Optional.empty());

        assertThrows(
                AlmoxarifadoNaoEncontradoException.class,
                () -> service.reservar(command).join()
        );
        verify(eventGateway, times(1)).publish(any(PedidoReservaFalhouEvent.class));
    }

    @Test
    void deveFazerRollbackQuandoFalhaNoCommandGateway() {
        String pedidoId = "pedido-1";
        ReservarItemCommand item1 = new ReservarItemCommand("item-1", 5);
        ReservarItemCommand item2 = new ReservarItemCommand("item-2", 3);
        ReservarPedidoCommand command = new ReservarPedidoCommand(pedidoId, List.of(item1, item2));

        UUID almoxId1 = UUID.randomUUID();
        UUID almoxId2 = UUID.randomUUID();

        Almoxarifado almox1 = mock(Almoxarifado.class);
        when(almox1.getId()).thenReturn(almoxId1);
        Almoxarifado almox2 = mock(Almoxarifado.class);
        when(almox2.getId()).thenReturn(almoxId2);

        when(findAlmoxarifadoPort.findFirstByItemDisponivel("item-1", 5))
                .thenReturn(Optional.of(almox1));
        when(findAlmoxarifadoPort.findFirstByItemDisponivel("item-2", 3))
                .thenReturn(Optional.of(almox2));

        when(commandGateway.sendAndWait(argThat(cmd ->
                cmd != null && ((AlmoxarifadoReservarItemCommand) cmd).getItemId().equals("item-1")
        ))).thenReturn(null);

        when(commandGateway.sendAndWait(argThat(cmd ->
                cmd != null && ((AlmoxarifadoReservarItemCommand) cmd).getItemId().equals("item-2")
        ))).thenThrow(new RuntimeException("Erro"));

        doNothing().when(eventGateway).publish(any(PedidoReservaFalhouEvent.class));

        assertThrows(RuntimeException.class, () -> service.reservar(command).join());

        verify(commandGateway, times(1)).send(argThat(cmd ->
                cmd != null && ((AlmoxarifadoLiberarReservaCommand) cmd).getId().equals(almoxId1)
        ));
        verify(eventGateway, times(1)).publish(any(PedidoReservaFalhouEvent.class));
    }

}
