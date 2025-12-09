package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.DinheiroNegativoException;
import br.com.petfriends.pedido.core.exception.DinheiroNuloException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Dinheiro(@JsonValue BigDecimal valor) {
    @JsonCreator
    public Dinheiro {
        if (valor == null) {
            throw new DinheiroNuloException();
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new DinheiroNegativoException();
        }
        valor = valor.setScale(2, RoundingMode.HALF_UP);
    }
}
