package br.com.petfriends.pedido.infra.adapter.messenger.publisher;

import br.com.petfriends.pedido.core.event.PedidoCriadoEvent;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.EnderecoEventDTO;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.ItemPedidoEventDTO;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.PedidoCriadoEventDTO;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoCriadoPublisher {
    private static final Logger log = LoggerFactory.getLogger(PedidoCriadoPublisher.class);
    private final StreamBridge streamBridge;

    public PedidoCriadoPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @EventHandler
    public void on(PedidoCriadoEvent event) {
        EnderecoEventDTO endereco = new EnderecoEventDTO(
                event.getEndereco().getRua(),
                event.getEndereco().getNumero(),
                event.getEndereco().getComplemento(),
                event.getEndereco().getBairro(),
                event.getEndereco().getCidade(),
                event.getEndereco().getEstado(),
                event.getEndereco().getCep().getValor()
        );
        List<ItemPedidoEventDTO> items = event.getItems()
                .stream()
                .map(i -> new ItemPedidoEventDTO(
                        i.getProdutoId(),
                        i.getValorUnitario().getValor(),
                        i.getQuantidade())
                ).toList();
        PedidoCriadoEventDTO dto = new PedidoCriadoEventDTO(
                event.getId(),
                event.getTimestamp(),
                event.getClienteId(),
                endereco,
                items
        );
        log.info("Evento publicado: {}", dto);
        streamBridge.send("pedido-criado-out-0", dto);
    }
}
