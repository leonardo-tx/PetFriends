package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.command.*;
import br.com.petfriends.pedido.core.event.*;
import br.com.petfriends.pedido.core.exception.*;
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
    private Endereco endereco;
    private String entregaId;
    private List<ItemPedido> itens;

    public Pedido() {
    }

    public Pedido(UUID id, String clienteId, PedidoStatus status, Endereco endereco, String entregaId, List<ItemPedido> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
        this.endereco = endereco;
        this.entregaId = entregaId;
        this.itens = itens;
    }

    @CommandHandler
    public Pedido(CriarPedidoCommand command) {
        if (command.getClienteId() == null) {
            throw new ClienteIdentificadorNuloException();
        }
        List<ItemPedido> itens = command.getItemPedidoCommands()
                .stream()
                .map(c -> new ItemPedido(
                        c.getProdutoId(),
                        new Dinheiro(c.getValorUnitario()),
                        c.getQuantidade()
                )).collect(Collectors.toCollection(ArrayList::new));
        Endereco endereco = new Endereco(
                command.getRua(),
                command.getNumero(),
                command.getComplemento(),
                command.getBairro(),
                command.getCidade(),
                command.getEstado(),
                new CEP(command.getCep())
        );
        PedidoCriadoEvent event = new PedidoCriadoEvent(
                UUID.randomUUID(),
                Instant.now(),
                command.getClienteId(),
                endereco,
                itens
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(IniciarPedidoCommand command) {
        if (status != PedidoStatus.CRIADO) {
            throw new PedidoNaoInicializavelException();
        }
        PedidoIniciadoEvent event = new PedidoIniciadoEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(CancelarPedidoCommand command) {
        if (status != PedidoStatus.CRIADO && status != PedidoStatus.INICIADO) {
            throw new PedidoIncancelavelException();
        }
        PedidoCanceladoEvent event = new PedidoCanceladoEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(PagarPedidoCommand command) {
        if (status != PedidoStatus.INICIADO) {
            throw new PedidoImpagavelException();
        }
        PedidoPagoEvent event = new PedidoPagoEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(SepararPedidoCommand command) {
        if (status != PedidoStatus.EM_SEPARACAO) {
            throw new PedidoInseparavelException();
        }
        PedidoSeparadoEvent event = new PedidoSeparadoEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(EnviarPedidoCommand command) {
        if (status != PedidoStatus.SEPARADO) {
            throw new PedidoIneviavelException();
        }
        PedidoEnviadoEvent event = new PedidoEnviadoEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(EntregarPedidoCommand command) {
        if (status != PedidoStatus.ENVIADO) {
            throw new PedidoIntregavelException();
        }
        PedidoEntregueEvent event = new PedidoEntregueEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(AdicionarEntregaAoPedidoCommand command) {
        if (command.getEntregaId() == null) {
            throw new EntregaIdentificadorNuloException();
        }
        EntregaAdicionadaAoPedidoEvent event = new EntregaAdicionadaAoPedidoEvent(
                command.getId(),
                Instant.now(),
                command.getEntregaId()
        );
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PedidoCriadoEvent event) {
        this.id = event.getId();
        this.clienteId = event.getClienteId();
        this.status = PedidoStatus.CRIADO;
        this.endereco = event.getEndereco();
        this.itens = event.getItems();
    }

    @EventSourcingHandler
    public void on(PedidoIniciadoEvent event) {
        this.status = PedidoStatus.INICIADO;
    }

    @EventSourcingHandler
    public void on(PedidoCanceladoEvent event) {
        this.status = PedidoStatus.CANCELADO;
    }

    @EventSourcingHandler
    public void on(PedidoPagoEvent event) {
        this.status = PedidoStatus.EM_SEPARACAO;
    }

    @EventSourcingHandler
    public void on(PedidoSeparadoEvent event) {
        this.status = PedidoStatus.SEPARADO;
    }

    @EventSourcingHandler
    public void on(PedidoEnviadoEvent event) {
        this.status = PedidoStatus.ENVIADO;
    }

    @EventSourcingHandler
    public void on(PedidoEntregueEvent event) {
        this.status = PedidoStatus.ENTREGUE;
    }

    @EventSourcingHandler
    public void on(EntregaAdicionadaAoPedidoEvent event) {
        this.entregaId = event.getEntregaId();
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

    public Endereco getEndereco() {
        return endereco;
    }

    public String getEntregaId() {
        return entregaId;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }
}
