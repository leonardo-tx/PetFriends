package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.command.*;
import br.com.petfriends.almoxarifado.core.event.*;
import br.com.petfriends.almoxarifado.core.exception.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aggregate
public class Almoxarifado {
    @AggregateIdentifier
    private UUID id;
    private AlmoxarifadoNome nome;
    private Map<String, ItemEstoque> estoques;

    public Almoxarifado() {
    }

    public Almoxarifado(UUID id, AlmoxarifadoNome nome, Map<String, ItemEstoque> estoques) {
        this.id = id;
        this.nome = nome;
        this.estoques = estoques;
    }

    @CommandHandler
    public Almoxarifado(CriarAlmoxarifadoCommand command) {
        AlmoxarifadoNome nome = new AlmoxarifadoNome(command.getNome());
        AlmoxarifadoCriadoEvent event = new AlmoxarifadoCriadoEvent(
                UUID.randomUUID(),
                Instant.now(),
                nome
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(AlmoxarifadoAdicionarItemCommand command) {
        ItemEstoque estoque = estoques.get(command.getItemId());
        if (estoque == null) {
            estoque = new ItemEstoque(command.getItemId());
        }
        estoque.verificarAdicionarEstoque(command.getQuantidade());
        AlmoxarifadoItemAdicionadoEvent event = new AlmoxarifadoItemAdicionadoEvent(
                id,
                Instant.now(),
                command.getItemId(),
                command.getQuantidade()
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(AlmoxarifadoReservarItemCommand command) {
        ItemEstoque estoque = estoques.get(command.getItemId());
        if (estoque == null) {
            throw new ItemNaoEncontradoException();
        }
        estoque.verificarAdicionarReserva(command.getPedidoId(), command.getQuantidade());
        AlmoxarifadoItemReservadoEvent event = new AlmoxarifadoItemReservadoEvent(
                id,
                Instant.now(),
                command.getPedidoId(),
                command.getItemId(),
                command.getQuantidade()
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(AlmoxarifadoLiberarReservaCommand command) {
        AlmoxarifadoReservaLiberadaEvent event = new AlmoxarifadoReservaLiberadaEvent(
                id,
                Instant.now(),
                command.getPedidoId()
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(AlmoxarifadoConsumirEstoqueCommand command) {
        AlmoxarifadoEstoqueConsumidoEvent event = new AlmoxarifadoEstoqueConsumidoEvent(
                id,
                Instant.now(),
                command.getPedidoId()
        );
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoCriadoEvent event) {
        this.id = event.getId();
        this.nome = event.getNome();
        this.estoques = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoItemAdicionadoEvent event) {
        ItemEstoque estoque = estoques.get(event.getItemId());
        if (estoque == null) {
            estoque = new ItemEstoque(event.getItemId());
            estoques.put(event.getItemId(), estoque);
        }
        estoque.adicionarEstoque(event.getQuantidade());
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoItemReservadoEvent event) {
        ItemEstoque estoque = estoques.get(event.getItemId());
        estoque.adicionarReserva(event.getPedidoId(), event.getQuantidade());
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoReservaLiberadaEvent event) {
        for (ItemEstoque estoque : estoques.values()) {
            estoque.liberarReserva(event.getPedidoId());
        }
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoEstoqueConsumidoEvent event) {
        for (ItemEstoque estoque : estoques.values()) {
            estoque.consumirEstoque(event.getPedidoId());
        }
    }

    public UUID getId() {
        return id;
    }

    public AlmoxarifadoNome getNome() {
        return nome;
    }

    public Map<String, ItemEstoque> getEstoques() {
        return Collections.unmodifiableMap(estoques);
    }
}
