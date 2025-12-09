package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.command.*;
import br.com.petfriends.transporte.core.event.*;
import br.com.petfriends.transporte.core.exception.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Aggregate
public class Entrega {
    @AggregateIdentifier
    private UUID id;
    private String pedidoId;
    private EntregaStatus status;
    private Map<String, Remessa> remessas;

    public Entrega() {
    }

    public Entrega(UUID id, String pedidoId, EntregaStatus status, Map<String, Remessa> remessas) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.remessas = remessas;
    }

    @CommandHandler
    public Entrega(CriarEntregaCommand command) {
        if (command.getPedidoId() == null) {
            throw new PedidoIdentificadorNuloException();
        }
        Map<String, List<CriarRemessaCommand>> grupos = command.getRemessaCommands()
                .stream()
                .collect(Collectors.groupingBy(CriarRemessaCommand::getAlmoxarifadoId));
        List<Remessa> remessas = new ArrayList<>();
        for (String almoxarifadoId : grupos.keySet()) {
            List<ItemRemessa> itens = new ArrayList<>();
            for (CriarRemessaCommand c : grupos.get(almoxarifadoId)) {
                itens.add(new ItemRemessa(c.getItemId(), c.getQuantidade()));
            }
            remessas.add(new Remessa(almoxarifadoId, RemessaStatus.EM_SEPARACAO, itens));
        }
        EntregaCriadaEvent event = new EntregaCriadaEvent(
                UUID.randomUUID(),
                Instant.now(),
                command.getPedidoId(),
                remessas
        );
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(CancelarEntregaCommand command) {
        if (status != EntregaStatus.EM_PROCESSAMENTO) {
            throw new EntregaNaoCancelavelException();
        }
        EntregaCanceladaEvent event = new EntregaCanceladaEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(IniciarEntregaCommand command) {
        if (status != EntregaStatus.EM_PROCESSAMENTO) {
            throw new EntregaNaoInicializavelException();
        }
        EntregaIniciadaEvent event = new EntregaIniciadaEvent(command.getId(), Instant.now());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(SepararRemessaCommand command) {
        if (status != EntregaStatus.EM_SEPARACAO) {
            throw new EntregaNaoIniciadaException();
        }
        Remessa remessa = remessas.get(command.getAlmoxarifadoId());
        if (remessa == null) {
            throw new RemessaNaoEncontradaException();
        }
        remessa.verificarSeparar();
        RemessaSeparadaEvent event = new RemessaSeparadaEvent(
                id,
                Instant.now(),
                command.getAlmoxarifadoId()
        );
        AggregateLifecycle.apply(event);

        for (Remessa remessaItem : remessas.values()) {
            if (remessaItem.getStatus() != RemessaStatus.SEPARADA) {
                return;
            }
        }
        EntregaSeparadaEvent novoEvento = new EntregaSeparadaEvent(id, Instant.now());
        AggregateLifecycle.apply(novoEvento);
    }

    @CommandHandler
    public void on(TransportarRemessaCommand command) {
        if (status != EntregaStatus.SEPARADO) {
            throw new EntregaNaoSeparadaException();
        }
        Remessa remessa = remessas.get(command.getAlmoxarifadoId());
        if (remessa == null) {
            throw new RemessaNaoEncontradaException();
        }
        remessa.verificarTransportar();
        RemessaTransportadaEvent event = new RemessaTransportadaEvent(
                id,
                Instant.now(),
                command.getAlmoxarifadoId()
        );
        AggregateLifecycle.apply(event);

        if (status != EntregaStatus.EM_ROTA_DE_ENTREGA) {
            EntregaTransportadaEvent novoEvento = new EntregaTransportadaEvent(id, Instant.now());
            AggregateLifecycle.apply(novoEvento);
        }
    }

    @CommandHandler
    public void on(EntregarRemessaCommand command) {
        Remessa remessa = remessas.get(command.getAlmoxarifadoId());
        if (remessa == null) {
            throw new RemessaNaoEncontradaException();
        }
        remessa.verificarEntregar();
        RemessaEntregueEvent event = new RemessaEntregueEvent(
                id,
                Instant.now(),
                command.getAlmoxarifadoId()
        );
        AggregateLifecycle.apply(event);

        for (Remessa remessaItem : remessas.values()) {
            if (remessaItem.getStatus() != RemessaStatus.ENTREGUE) {
                return;
            }
        }
        EntregaEntregueEvent novoEvento = new EntregaEntregueEvent(id, Instant.now());
        AggregateLifecycle.apply(novoEvento);
    }

    @CommandHandler
    public void on(CriarOcorrenciaRemessaCommand command) {
        Remessa remessa = remessas.get(command.getAlmoxarifadoId());
        if (remessa == null) {
            throw new RemessaNaoEncontradaException();
        }
        remessa.verificarCriarOcorrencia();
        RemessaOcorrenciaCriadaEvent event = new RemessaOcorrenciaCriadaEvent(
                id,
                Instant.now(),
                command.getAlmoxarifadoId()
        );
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EntregaCriadaEvent event) {
        this.id = event.getId();
        this.pedidoId =  event.getPedidoId();
        this.status = EntregaStatus.EM_PROCESSAMENTO;
        this.remessas = new HashMap<>();

        for (Remessa remessa : event.getRemessas()) {
            this.remessas.put(remessa.getAlmoxarifadoId(), remessa);
        }
    }

    @EventSourcingHandler
    public void on(EntregaCanceladaEvent event) {
        this.status = EntregaStatus.CANCELADA;
    }

    @EventSourcingHandler
    public void on(EntregaIniciadaEvent event) {
        this.status = EntregaStatus.EM_SEPARACAO;
    }

    @EventSourcingHandler
    public void on(RemessaSeparadaEvent event) {
        Remessa remessa = remessas.get(event.getAlmoxarifadoId());
        remessa.separar();
    }

    @EventSourcingHandler
    public void on(RemessaTransportadaEvent event) {
        Remessa remessa = remessas.get(event.getAlmoxarifadoId());
        remessa.transportar();
    }

    @EventSourcingHandler
    public void on(RemessaEntregueEvent event) {
        Remessa remessa = remessas.get(event.getAlmoxarifadoId());
        remessa.entregar();
    }

    @EventSourcingHandler
    public void on(RemessaOcorrenciaCriadaEvent event) {
        Remessa remessa = remessas.get(event.getAlmoxarifadoId());
        remessa.criarOcorrencia();
    }

    @EventSourcingHandler
    public void on(EntregaSeparadaEvent event) {
        this.status = EntregaStatus.SEPARADO;
    }

    @EventSourcingHandler
    public void on(EntregaTransportadaEvent event) {
        this.status = EntregaStatus.EM_ROTA_DE_ENTREGA;
    }

    @EventSourcingHandler
    public void on(EntregaEntregueEvent event) {
        this.status = EntregaStatus.ENTREGUE;
    }

    public UUID getId() {
        return id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public EntregaStatus getStatus() {
        return status;
    }

    public Map<String, Remessa> getRemessas() {
        return Collections.unmodifiableMap(remessas);
    }
}
