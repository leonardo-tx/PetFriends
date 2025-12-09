package br.com.petfriends.almoxarifado.core.service;

import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoLiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.command.LiberarReservaCommand;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LiberarReservaServiceTest {
    @Mock
    private CommandGateway commandGateway;

    @Mock
    private FindAlmoxarifadoPort findAlmoxarifadoPort;

    @InjectMocks
    private LiberarReservaService service;

    @Test
    void deveLiberarReservaComSucesso() {
        String pedidoId = "pedido-1";
        UUID almoxId1 = UUID.randomUUID();
        UUID almoxId2 = UUID.randomUUID();

        Almoxarifado almox1 = mock(Almoxarifado.class);
        when(almox1.getId()).thenReturn(almoxId1);

        Almoxarifado almox2 = mock(Almoxarifado.class);
        when(almox2.getId()).thenReturn(almoxId2);

        when(findAlmoxarifadoPort.findByPedidoIdInReservas(pedidoId))
                .thenReturn(List.of(almox1, almox2));

        when(commandGateway.send(any(AlmoxarifadoLiberarReservaCommand.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        LiberarReservaCommand command = new LiberarReservaCommand(pedidoId);

        CompletableFuture<Void> resultado = service.liberarReserva(command);

        resultado.join();

        verify(commandGateway, times(1)).send(argThat(cmd ->
                cmd != null && ((AlmoxarifadoLiberarReservaCommand)cmd).getId().equals(almoxId1)
        ));
        verify(commandGateway, times(1)).send(argThat(cmd ->
                cmd != null && ((AlmoxarifadoLiberarReservaCommand)cmd).getId().equals(almoxId2)
        ));
    }

    @Test
    void deveRetornarCompletableFutureMesmoSemAlmoxarifados() {
        String pedidoId = "pedido-1";
        when(findAlmoxarifadoPort.findByPedidoIdInReservas(pedidoId))
                .thenReturn(List.of());
        LiberarReservaCommand command = new LiberarReservaCommand(pedidoId);

        CompletableFuture<Void> resultado = service.liberarReserva(command);

        assertDoesNotThrow(resultado::join);
        verify(commandGateway, never()).send(any());
    }
}
