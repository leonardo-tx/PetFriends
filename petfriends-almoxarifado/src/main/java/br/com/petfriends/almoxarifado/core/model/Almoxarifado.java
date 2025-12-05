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

    public Almoxarifado(UUID id, AlmoxarifadoNome nome, HashMap<String, ItemEstoque> estoques) {
        this.id = id;
        this.nome = nome;
        this.estoques = estoques;
    }

    @CommandHandler
    public Almoxarifado(CriarAlmoxarifadoCommand command) {
        AlmoxarifadoNome nome = AlmoxarifadoNome.valueOf(command.getNome());
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
            estoque = new ItemEstoque(command.getItemId(), 0, 0);
        }
        estoque.adicionarEstoque(command.getQuantidade());
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
        estoque.adicionarReserva(command.getQuantidade());
        AlmoxarifadoItemReservadoEvent event = new AlmoxarifadoItemReservadoEvent(
            id,
            Instant.now(),
            command.getItemId(),
            command.getQuantidade()
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(AlmoxarifadoLiberarReservaCommand command) {
        ItemEstoque estoque = estoques.get(command.getItemId());
        if (estoque == null) {
            throw new ItemNaoEncontradoException();
        }
        estoque.liberarReserva(command.getQuantidade());
        AlmoxarifadoReservaLiberadaEvent event = new AlmoxarifadoReservaLiberadaEvent(
                id,
                Instant.now(),
                command.getItemId(),
                command.getQuantidade()
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(AlmoxarifadoConsumirEstoqueCommand command) {
        ItemEstoque estoque = estoques.get(command.getItemId());
        if (estoque == null) {
            throw new ItemNaoEncontradoException();
        }
        estoque.consumirEstoque(command.getQuantidade());
        AlmoxarifadoEstoqueConsumidoEvent event = new AlmoxarifadoEstoqueConsumidoEvent(
                id,
                Instant.now(),
                command.getItemId(),
                command.getQuantidade()
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
            estoque = new ItemEstoque(event.getItemId(), 0, 0);
        }
        estoque = estoque.adicionarEstoque(event.getQuantidade());
        estoques.put(estoque.getItemId(), estoque);
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoItemReservadoEvent event) {
        ItemEstoque estoque = estoques.get(event.getItemId())
                .adicionarReserva(event.getQuantidade());
        estoques.put(estoque.getItemId(), estoque);
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoReservaLiberadaEvent event) {
        ItemEstoque estoque = estoques.get(event.getItemId())
                .liberarReserva(event.getQuantidade());
        estoques.put(estoque.getItemId(), estoque);
    }

    @EventSourcingHandler
    public void on(AlmoxarifadoEstoqueConsumidoEvent event) {
        ItemEstoque estoque = estoques.get(event.getItemId())
                .consumirEstoque(event.getQuantidade());
        estoques.put(estoque.getItemId(), estoque);
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
