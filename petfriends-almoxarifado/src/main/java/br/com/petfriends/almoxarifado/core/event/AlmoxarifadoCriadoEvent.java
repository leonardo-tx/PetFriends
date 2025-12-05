package br.com.petfriends.almoxarifado.core.event;

import br.com.petfriends.almoxarifado.core.model.AlmoxarifadoNome;

import java.time.Instant;
import java.util.UUID;

public class AlmoxarifadoCriadoEvent extends BaseEvent<UUID> {
    private final AlmoxarifadoNome nome;

    public AlmoxarifadoCriadoEvent(UUID id, Instant timestamp, AlmoxarifadoNome nome) {
        super(id, timestamp);
        this.nome = nome;
    }

    public AlmoxarifadoNome getNome() {
        return nome;
    }
}
