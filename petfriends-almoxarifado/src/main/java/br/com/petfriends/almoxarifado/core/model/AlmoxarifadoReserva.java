package br.com.petfriends.almoxarifado.core.model;

import java.util.UUID;

public record AlmoxarifadoReserva(
        UUID almoxarifadoId,
        String itemId,
        Integer quantidade
) {
}
