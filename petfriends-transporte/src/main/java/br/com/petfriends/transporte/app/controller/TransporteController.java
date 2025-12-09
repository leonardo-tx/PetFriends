package br.com.petfriends.transporte.app.controller;

import br.com.petfriends.transporte.app.response.dto.ApiResponse;
import br.com.petfriends.transporte.app.response.dto.EntregaViewDTO;
import br.com.petfriends.transporte.app.response.mapper.EntregaViewMapper;
import br.com.petfriends.transporte.core.command.CriarOcorrenciaRemessaCommand;
import br.com.petfriends.transporte.core.command.EntregarRemessaCommand;
import br.com.petfriends.transporte.core.command.SepararRemessaCommand;
import br.com.petfriends.transporte.core.command.TransportarRemessaCommand;
import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.port.in.*;
import br.com.petfriends.transporte.core.query.BuscarEntregaEventosPeloIdQuery;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloIdQuery;
import br.com.petfriends.transporte.core.query.BuscarEntregaPeloPedidoIdQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/transporte")
public class TransporteController {
    private final GetEntregaEventosUseCase getEntregaEventosUseCase;
    private final GetEntregaUseCase getEntregaUseCase;
    private final SepararRemessaUseCase separarRemessaUseCase;
    private final TransportarRemessaUseCase transportarRemessaUseCase;
    private final EntregarRemessaUseCase entregarRemessaUseCase;
    private final CriarOcorrenciaNaRemessaUseCase criarOcorrenciaNaRemessaUseCase;
    private final EntregaViewMapper entregaViewMapper;

    public TransporteController(
            GetEntregaEventosUseCase getEntregaEventosUseCase,
            GetEntregaUseCase getEntregaUseCase,
            SepararRemessaUseCase separarRemessaUseCase,
            TransportarRemessaUseCase transportarRemessaUseCase,
            EntregarRemessaUseCase entregarRemessaUseCase,
            CriarOcorrenciaNaRemessaUseCase criarOcorrenciaNaRemessaUseCase,
            EntregaViewMapper entregaViewMapper
    ) {
        this.getEntregaEventosUseCase = getEntregaEventosUseCase;
        this.getEntregaUseCase = getEntregaUseCase;
        this.separarRemessaUseCase = separarRemessaUseCase;
        this.transportarRemessaUseCase = transportarRemessaUseCase;
        this.entregarRemessaUseCase = entregarRemessaUseCase;
        this.criarOcorrenciaNaRemessaUseCase = criarOcorrenciaNaRemessaUseCase;
        this.entregaViewMapper = entregaViewMapper;
    }

    @GetMapping("/{id}/eventos")
    public ResponseEntity<ApiResponse<List<Object>>> resgatarEventosDoEntrega(@PathVariable("id") UUID id) {
        BuscarEntregaEventosPeloIdQuery query = new BuscarEntregaEventosPeloIdQuery(id);
        List<Object> eventos = getEntregaEventosUseCase.getEventos(query);

        return ApiResponse.success(eventos).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EntregaViewDTO>> resgatarEntregaPeloId(@PathVariable("id") UUID id) {
        BuscarEntregaPeloIdQuery query = new BuscarEntregaPeloIdQuery(id);
        Entrega entrega = getEntregaUseCase.getById(query);
        EntregaViewDTO view = entregaViewMapper.toDTO(entrega);

        return ApiResponse.success(view).createResponse(HttpStatus.OK);
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<ApiResponse<EntregaViewDTO>> resgatarEntregaPeloPedidoId(@PathVariable("id") String id) {
        BuscarEntregaPeloPedidoIdQuery query = new BuscarEntregaPeloPedidoIdQuery(id);
        Entrega entrega = getEntregaUseCase.getByPedidoId(query);
        EntregaViewDTO view = entregaViewMapper.toDTO(entrega);

        return ApiResponse.success(view).createResponse(HttpStatus.OK);
    }

    @PostMapping("/{id}/separar/{almoxarifadoId}")
    public CompletableFuture<ResponseEntity<ApiResponse<Object>>> separarRemessaDaEntrega(
            @PathVariable("id") UUID id,
            @PathVariable("almoxarifadoId") String almoxarifadoId
    ) {
        SepararRemessaCommand command = new SepararRemessaCommand(id, almoxarifadoId);
        return separarRemessaUseCase.separar(command).thenApply(v ->
                ApiResponse.success(null).createResponse(HttpStatus.OK)
        );
    }

    @PostMapping("/{id}/transportar/{almoxarifadoId}")
    public CompletableFuture<ResponseEntity<ApiResponse<Object>>> transportarRemessaDaEntrega(
            @PathVariable("id") UUID id,
            @PathVariable("almoxarifadoId") String almoxarifadoId
    ) {
        TransportarRemessaCommand command = new TransportarRemessaCommand(id, almoxarifadoId);
        return transportarRemessaUseCase.transportar(command).thenApply(v ->
                ApiResponse.success(null).createResponse(HttpStatus.OK)
        );
    }

    @PostMapping("/{id}/entregar/{almoxarifadoId}")
    public CompletableFuture<ResponseEntity<ApiResponse<Object>>> entregarRemessaDaEntrega(
            @PathVariable("id") UUID id,
            @PathVariable("almoxarifadoId") String almoxarifadoId
    ) {
        EntregarRemessaCommand command = new EntregarRemessaCommand(id, almoxarifadoId);
        return entregarRemessaUseCase.entregar(command).thenApply(v ->
                ApiResponse.success(null).createResponse(HttpStatus.OK)
        );
    }

    @PostMapping("/{id}/criar-ocorrencia/{almoxarifadoId}")
    public CompletableFuture<ResponseEntity<ApiResponse<Object>>> criarOcorrenciaRemessaDaEntrega(
            @PathVariable("id") UUID id,
            @PathVariable("almoxarifadoId") String almoxarifadoId
    ) {
        CriarOcorrenciaRemessaCommand command = new CriarOcorrenciaRemessaCommand(id, almoxarifadoId);
        return criarOcorrenciaNaRemessaUseCase.criarOcorrencia(command).thenApply(v ->
                ApiResponse.success(null).createResponse(HttpStatus.OK)
        );
    }
}
