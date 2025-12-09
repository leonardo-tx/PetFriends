package br.com.petfriends.transporte.app.listener;

import br.com.petfriends.transporte.app.request.dto.PedidoCanceladoEventDTO;
import br.com.petfriends.transporte.core.command.CancelarEntregaCommand;
import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.port.in.CancelarEntregaUseCase;
import br.com.petfriends.transporte.core.port.in.GetEntregaUseCase;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloPedidoIdQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PedidoCanceladoListener {
    private static final Logger log = LoggerFactory.getLogger(PedidoCanceladoListener.class);
    private final GetEntregaUseCase getEntregaUseCase;
    private final CancelarEntregaUseCase cancelarEntregaUseCase;

    public PedidoCanceladoListener(GetEntregaUseCase getEntregaUseCase, CancelarEntregaUseCase cancelarEntregaUseCase) {
        this.getEntregaUseCase = getEntregaUseCase;
        this.cancelarEntregaUseCase = cancelarEntregaUseCase;
    }

    @Bean
    public Consumer<PedidoCanceladoEventDTO> pedidoCancelado() {
        return event -> {
            log.info("Evento recebido: {}", event);
            BuscarEntregaPeloPedidoIdQuery query = new BuscarEntregaPeloPedidoIdQuery(event.id());
            Entrega entrega = getEntregaUseCase.getByPedidoId(query);
            CancelarEntregaCommand command = new CancelarEntregaCommand(entrega.getId());
            cancelarEntregaUseCase.cancelar(command);
        };
    }
}
