package br.com.petfriends.pedido.infra.adapter.messenger.adapter;

import br.com.petfriends.pedido.core.event.PedidoCriadoEvent;
import br.com.petfriends.pedido.core.model.CEP;
import br.com.petfriends.pedido.core.model.Dinheiro;
import br.com.petfriends.pedido.core.model.Endereco;
import br.com.petfriends.pedido.core.model.ItemPedido;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.EnderecoEventDTO;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.ItemPedidoEventDTO;
import br.com.petfriends.pedido.infra.adapter.messenger.dto.PedidoCriadoEventDTO;
import br.com.petfriends.pedido.infra.adapter.messenger.publisher.PedidoCriadoPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoCriadoPublisherTest {
    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private PedidoCriadoPublisher publisher;

    @Captor
    private ArgumentCaptor<PedidoCriadoEventDTO> captor;

    private UUID pedidoId;
    private Instant timestamp;

    @BeforeEach
    void setupBeforeEach() {
        pedidoId = UUID.randomUUID();
        timestamp = Instant.now();
    }

    @Test
    void devePublicarEventoComStreamBridge() {
        Endereco endereco = new Endereco(
                "Rua A",
                "123",
                "Apto 1",
                "Bairro B",
                "Cidade C",
                "Estado D",
                new CEP("12345678")
        );
        List<ItemPedido> itens = List.of(
                new ItemPedido("produto-1", new Dinheiro(BigDecimal.valueOf(10.50)), 2),
                new ItemPedido("produto-2", new Dinheiro(BigDecimal.valueOf(5.25)), 1)
        );
        PedidoCriadoEvent event = new PedidoCriadoEvent(
                pedidoId,
                timestamp,
                "cliente-1",
                endereco,
                itens
        );
        publisher.on(event);

        verify(streamBridge).send(eq("pedido-criado-out-0"), captor.capture());

        PedidoCriadoEventDTO dto = captor.getValue();

        assertEquals(pedidoId, dto.id());
        assertEquals(timestamp, dto.timestamp());
        assertEquals("cliente-1", dto.clienteId());

        EnderecoEventDTO enderecoDTO = dto.endereco();
        assertEquals("Rua A", enderecoDTO.rua());
        assertEquals("123", enderecoDTO.numero());
        assertEquals("Apto 1", enderecoDTO.complemento());
        assertEquals("Bairro B", enderecoDTO.bairro());
        assertEquals("Cidade C", enderecoDTO.cidade());
        assertEquals("Estado D", enderecoDTO.estado());
        assertEquals("12345678", enderecoDTO.cep());

        List<ItemPedidoEventDTO> itemsDTO = dto.items();
        assertEquals(2, itemsDTO.size());
        assertEquals("produto-1", itemsDTO.get(0).produtoId());
        assertEquals(BigDecimal.valueOf(10.50).setScale(2, RoundingMode.HALF_UP), itemsDTO.get(0).valorUnitario());
        assertEquals(2, itemsDTO.get(0).quantidade());

        assertEquals("produto-2", itemsDTO.get(1).produtoId());
        assertEquals(BigDecimal.valueOf(5.25), itemsDTO.get(1).valorUnitario());
        assertEquals(1, itemsDTO.get(1).quantidade());
    }
}

