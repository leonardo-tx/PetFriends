package br.com.petfriends.almoxarifado.app.controller;

import br.com.petfriends.almoxarifado.app.request.dto.AlmoxarifadoAdicionarItemDTO;
import br.com.petfriends.almoxarifado.app.request.dto.CriarAlmoxarifadoDTO;
import br.com.petfriends.almoxarifado.app.response.dto.AlmoxarifadoViewDTO;
import br.com.petfriends.almoxarifado.app.response.dto.ApiResponse;
import br.com.petfriends.almoxarifado.app.response.mapper.AlmoxarifadoViewMapper;
import br.com.petfriends.almoxarifado.core.command.AlmoxarifadoAdicionarItemCommand;
import br.com.petfriends.almoxarifado.core.command.CriarAlmoxarifadoCommand;
import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.port.in.AdicionarItemNoAlmoxarifadoUseCase;
import br.com.petfriends.almoxarifado.core.port.in.CriarAlmoxarifadoUseCase;
import br.com.petfriends.almoxarifado.core.port.in.GetAlmoxarifadoEventosUseCase;
import br.com.petfriends.almoxarifado.core.port.in.GetAlmoxarifadoUseCase;
import br.com.petfriends.almoxarifado.core.query.BuscarAlmoxarifadoEventosPeloIdQuery;
import br.com.petfriends.almoxarifado.core.query.BuscarAlmoxarifadoPeloIdQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/almoxarifado")
public class AlmoxarifadoController {
    private final CriarAlmoxarifadoUseCase criarAlmoxarifadoUseCase;
    private final AdicionarItemNoAlmoxarifadoUseCase adicionarItemNoAlmoxarifadoUseCase;
    private final GetAlmoxarifadoUseCase getAlmoxarifadoUseCase;
    private final GetAlmoxarifadoEventosUseCase getAlmoxarifadoEventosUseCase;
    private final AlmoxarifadoViewMapper almoxarifadoViewMapper;

    public AlmoxarifadoController(
            CriarAlmoxarifadoUseCase criarAlmoxarifadoUseCase,
            AdicionarItemNoAlmoxarifadoUseCase adicionarItemNoAlmoxarifadoUseCase,
            GetAlmoxarifadoUseCase getAlmoxarifadoUseCase,
            GetAlmoxarifadoEventosUseCase getAlmoxarifadoEventosUseCase,
            AlmoxarifadoViewMapper almoxarifadoViewMapper
    ) {
        this.criarAlmoxarifadoUseCase = criarAlmoxarifadoUseCase;
        this.adicionarItemNoAlmoxarifadoUseCase = adicionarItemNoAlmoxarifadoUseCase;
        this.getAlmoxarifadoUseCase = getAlmoxarifadoUseCase;
        this.getAlmoxarifadoEventosUseCase = getAlmoxarifadoEventosUseCase;
        this.almoxarifadoViewMapper = almoxarifadoViewMapper;
    }

    @GetMapping("/{id}/eventos")
    public ResponseEntity<ApiResponse<List<Object>>> resgatarEventosDoAlmoxarifado(@PathVariable("id") UUID id) {
        BuscarAlmoxarifadoEventosPeloIdQuery query = new BuscarAlmoxarifadoEventosPeloIdQuery(id);
        List<Object> eventos = getAlmoxarifadoEventosUseCase.getEventos(query);

        return ApiResponse.success(eventos).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AlmoxarifadoViewDTO>> resgatarAlmoxarifadoPeloId(@PathVariable("id") UUID id) {
        BuscarAlmoxarifadoPeloIdQuery query = new BuscarAlmoxarifadoPeloIdQuery(id);
        Almoxarifado almoxarifado = getAlmoxarifadoUseCase.getById(query);
        AlmoxarifadoViewDTO view = almoxarifadoViewMapper.toDTO(almoxarifado);

        return ApiResponse.success(view).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<ApiResponse<UUID>>> criarAlmoxarifado(
            @RequestBody CriarAlmoxarifadoDTO form
    ) {
        CriarAlmoxarifadoCommand command = new CriarAlmoxarifadoCommand(form.nome());
        return criarAlmoxarifadoUseCase.criar(command).thenApply(id ->
                ApiResponse.success(id).createResponse(HttpStatus.CREATED)
        );
    }

    @PostMapping("/{id}/adicionar-item")
    public CompletableFuture<ResponseEntity<ApiResponse<Object>>> adicionarItemNoAlmoxarifado(
            @PathVariable("id") UUID id,
            @RequestBody AlmoxarifadoAdicionarItemDTO form
    ) {
        AlmoxarifadoAdicionarItemCommand command = new AlmoxarifadoAdicionarItemCommand(
                id,
                form.itemId(),
                form.quantidade()
        );
        return adicionarItemNoAlmoxarifadoUseCase.adicionar(command).thenApply(v ->
                ApiResponse.success(null).createResponse(HttpStatus.OK)
        );
    }
}
