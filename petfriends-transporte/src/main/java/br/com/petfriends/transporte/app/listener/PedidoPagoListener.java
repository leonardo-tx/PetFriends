package br.com.petfriends.transporte.app.listener;

import br.com.petfriends.transporte.app.request.dto.PedidoPagoEventDTO;
import br.com.petfriends.transporte.core.command.IniciarEntregaCommand;
import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.port.in.GetEntregaUseCase;
import br.com.petfriends.transporte.core.port.in.IniciarEntregaUseCase;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloPedidoIdQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PedidoPagoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoPagoListener.class);
    private final IniciarEntregaUseCase iniciarEntregaUseCase;
    private final GetEntregaUseCase getEntregaUseCase;

    public PedidoPagoListener(IniciarEntregaUseCase iniciarEntregaUseCase, GetEntregaUseCase getEntregaUseCase) {
        this.iniciarEntregaUseCase = iniciarEntregaUseCase;
        this.getEntregaUseCase = getEntregaUseCase;
    }

    @Bean
    public Consumer<PedidoPagoEventDTO> pedidoPago() {
        return event -> {
            log.info("Evento recebido: {}", event);
            BuscarEntregaPeloPedidoIdQuery query = new BuscarEntregaPeloPedidoIdQuery(event.id());
            Entrega entrega = getEntregaUseCase.getByPedidoId(query);
            IniciarEntregaCommand command = new IniciarEntregaCommand(entrega.getId());
            iniciarEntregaUseCase.iniciar(command);
        };
    }
}
