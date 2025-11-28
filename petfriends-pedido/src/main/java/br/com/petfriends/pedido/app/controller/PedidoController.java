package br.com.petfriends.pedido.app.controller;

import br.com.petfriends.pedido.app.request.dto.CriarPedidoDTO;
import br.com.petfriends.pedido.app.response.dto.ApiResponse;
import br.com.petfriends.pedido.app.response.dto.PedidoViewDTO;
import br.com.petfriends.pedido.app.response.mapper.PedidoViewMapper;
import br.com.petfriends.pedido.core.command.AdicionarItemPedidoCommand;
import br.com.petfriends.pedido.core.command.CancelarPedidoCommand;
import br.com.petfriends.pedido.core.command.CriarPedidoCommand;
import br.com.petfriends.pedido.core.command.PagarPedidoCommand;
import br.com.petfriends.pedido.core.model.Pedido;
import br.com.petfriends.pedido.core.port.in.*;
import br.com.petfriends.pedido.core.query.BuscarPedidoEventosPeloIdQuery;
import br.com.petfriends.pedido.core.query.BuscarPedidoPeloIdQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/pedido")
public class PedidoController {
    private final GetPedidoEventosUseCase getPedidoEventosUseCase;
    private final GetPedidoUseCase getPedidoUseCase;
    private final CriarPedidoUseCase criarPedidoUseCase;
    private final CancelarPedidoUseCase cancelarPedidoUseCase;
    private final PagarPedidoUseCase pagarPedidoUseCase;
    private final PedidoViewMapper pedidoViewMapper;

    public PedidoController(
            GetPedidoEventosUseCase getPedidoEventosUseCase,
            GetPedidoUseCase getPedidoUseCase,
            CriarPedidoUseCase criarPedidoUseCase,
            CancelarPedidoUseCase cancelarPedidoUseCase,
            PagarPedidoUseCase pagarPedidoUseCase,
            PedidoViewMapper pedidoViewMapper
    ) {
        this.getPedidoEventosUseCase = getPedidoEventosUseCase;
        this.getPedidoUseCase = getPedidoUseCase;
        this.criarPedidoUseCase = criarPedidoUseCase;
        this.cancelarPedidoUseCase = cancelarPedidoUseCase;
        this.pagarPedidoUseCase = pagarPedidoUseCase;
        this.pedidoViewMapper = pedidoViewMapper;
    }

    @GetMapping("/{id}/eventos")
    public ResponseEntity<ApiResponse<List<Object>>> resgatarEventosDoPedido(@PathVariable("id") UUID id) {
        BuscarPedidoEventosPeloIdQuery query = new BuscarPedidoEventosPeloIdQuery(id);
        List<Object> eventos = getPedidoEventosUseCase.getEventos(query);

        return ApiResponse.success(eventos).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoViewDTO>> resgatarPedidoPeloId(@PathVariable("id") UUID id) {
        BuscarPedidoPeloIdQuery query = new BuscarPedidoPeloIdQuery(id);
        Pedido pedido = getPedidoUseCase.getById(query);
        PedidoViewDTO view = pedidoViewMapper.toDTO(pedido);

        return ApiResponse.success(view).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiResponse<UUID>>> criarPedido(
            @RequestBody CriarPedidoDTO form
    ) {
        List<AdicionarItemPedidoCommand> adicionarItemCommands = form.itens()
                .stream()
                .map(d -> new AdicionarItemPedidoCommand(
                        d.produtoId(),
                        d.valorUnitario(),
                        d.quantidade()
                )).toList();
        CriarPedidoCommand command = new CriarPedidoCommand(form.clienteId(), adicionarItemCommands);
        return criarPedidoUseCase.criar(command).thenApply(id ->
                ApiResponse.success(id).createResponse(HttpStatus.CREATED)
        );
    }

    @PostMapping("/{id}/cancelar")
    public CompletableFuture<ResponseEntity<ApiResponse<Object>>> cancelarPedido(
            @PathVariable("id") UUID id
    ) {
        CancelarPedidoCommand command = new CancelarPedidoCommand(id);
        return cancelarPedidoUseCase.cancelar(command).thenApply(v ->
                ApiResponse.success(null).createResponse(HttpStatus.OK)
        );
    }

    @PostMapping("/{id}/pagar")
    public CompletableFuture<ResponseEntity<ApiResponse<Object>>> pagarPedido(
            @PathVariable("id") UUID id
    ) {
        PagarPedidoCommand command = new PagarPedidoCommand(id);
        return pagarPedidoUseCase.pagar(command).thenApply(v ->
                ApiResponse.success(null).createResponse(HttpStatus.OK)
        );
    }
}
