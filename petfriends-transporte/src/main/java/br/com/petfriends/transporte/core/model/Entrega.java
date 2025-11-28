package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.command.CriarEntregaCommand;
import br.com.petfriends.transporte.core.event.EntregaCriadaEvent;
import br.com.petfriends.transporte.core.exception.PedidoIdentificadorNuloException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.UUID;

@Aggregate
public class Entrega {
    @AggregateIdentifier
    private UUID id;
    private String pedidoId;
    private EntregaStatus status;
    private Endereco endereco;

    public Entrega() {
    }

    public Entrega(UUID id, String pedidoId, EntregaStatus status, Endereco endereco) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.endereco = endereco;
    }

    @CommandHandler
    public Entrega(CriarEntregaCommand command) {
        if (command.getPedidoId() == null) {
            throw new PedidoIdentificadorNuloException();
        }
        Endereco endereco = Endereco.valueOf(
                command.getRua(),
                command.getNumero(),
                command.getComplemento(),
                command.getBairro(),
                command.getCidade(),
                command.getEstado(),
                CEP.valueOf(command.getCep())
        );
        EntregaCriadaEvent event = new EntregaCriadaEvent(
                UUID.randomUUID(),
                Instant.now(),
                command.getPedidoId(),
                endereco
        );
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EntregaCriadaEvent event) {
        this.id = event.getId();
        this.pedidoId =  event.getPedidoId();
        this.status = EntregaStatus.EM_SEPARACAO;
        this.endereco = event.getEndereco();
    }
}
