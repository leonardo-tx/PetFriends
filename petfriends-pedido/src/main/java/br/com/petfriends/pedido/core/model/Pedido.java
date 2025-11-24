package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.command.CancelarPedidoCommand;
import br.com.petfriends.pedido.core.command.CriarPedidoCommand;
import br.com.petfriends.pedido.core.event.PedidoCanceladoEvent;
import br.com.petfriends.pedido.core.event.PedidoCriadoEvent;
import br.com.petfriends.pedido.core.exception.PedidoIncancelavelException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Aggregate
public class Pedido {
    @AggregateIdentifier
    private UUID id;
    private String clienteId;
    private PedidoStatus status;
    private List<ItemPedido> itens;

    public Pedido() {
    }

    public Pedido(UUID id, String clienteId, PedidoStatus status, List<ItemPedido> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
        this.itens = itens;
    }

    @CommandHandler
    public Pedido(CriarPedidoCommand command) {
        List<ItemPedido> itens = command.getItemPedidoCommands()
                .stream()
                .map(c -> new ItemPedido(
                        c.getProdutoId(),
                        Dinheiro.valueOf(c.getValorUnitario()),
                        c.getQuantidade()
                )).collect(Collectors.toCollection(ArrayList::new));;
        PedidoCriadoEvent event = new PedidoCriadoEvent(
                UUID.randomUUID(),
                Instant.now(),
                command.getClienteId(),
                itens
        );
        AggregateLifecycle.apply(event);
    }

    public UUID getId() {
        return id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    @CommandHandler
    public void on(CancelarPedidoCommand command) {
        if (status != PedidoStatus.CRIADO) {
            throw new PedidoIncancelavelException();
        }
        PedidoCanceladoEvent event = new PedidoCanceladoEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PedidoCriadoEvent event) {
        this.id = event.getId();
        this.clienteId = event.getClienteId();
        this.status = PedidoStatus.CRIADO;
        this.itens = event.getItems();
    }

    @EventSourcingHandler
    public void on(PedidoCanceladoEvent event) {
        this.status = PedidoStatus.CANCELADO;
    }
}
