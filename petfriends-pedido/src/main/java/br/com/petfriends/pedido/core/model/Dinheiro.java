package br.com.petfriends.pedido.core.model;

import br.com.petfriends.pedido.core.exception.DinheiroNegativoException;
import br.com.petfriends.pedido.core.exception.DinheiroNuloException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Dinheiro {
    private final BigDecimal valor;

    private Dinheiro(BigDecimal valor) {
        if (valor == null) {
            throw new DinheiroNuloException();
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new DinheiroNegativoException();
        }
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static Dinheiro valueOf(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public BigDecimal getValor() {
        return valor;
    }
}
