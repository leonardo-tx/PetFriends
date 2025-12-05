package br.com.petfriends.almoxarifado.core.port.out;

import br.com.petfriends.almoxarifado.core.model.Almoxarifado;

import java.util.Optional;
import java.util.UUID;

public interface FindAlmoxarifadoPort {
    Optional<Almoxarifado> findById(UUID id);
    Optional<Almoxarifado> findFirstByItemDisponivel(String itemId, int quantidadePedida);
}
