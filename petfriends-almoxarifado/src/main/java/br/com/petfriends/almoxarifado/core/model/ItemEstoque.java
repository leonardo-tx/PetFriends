package br.com.petfriends.almoxarifado.core.model;

import br.com.petfriends.almoxarifado.core.exception.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ItemEstoque {
    private final String itemId;
    private int quantidadeDisponivel;
    private int quantidadeReservada;
    private final Map<String, Reserva> reservas;

    public ItemEstoque(
            String itemId,
            int quantidadeDisponivel,
            int quantidadeReservada,
            Map<String, Reserva> reservas
    ) {
        if (itemId == null) {
            throw new ItemIdentificadorNuloException();
        }
        if (quantidadeDisponivel < 0 || quantidadeReservada < 0) {
            throw new ItemQuantidadeNegativoException();
        }
        if (quantidadeDisponivel < quantidadeReservada) {
            throw new ItemQuantidadeReservadaInvalidaException();
        }
        this.itemId = itemId;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeReservada = quantidadeReservada;
        this.reservas = reservas;
    }

    public ItemEstoque(String itemId) {
        if (itemId == null) {
            throw new ItemIdentificadorNuloException();
        }
        this.itemId = itemId;
        this.quantidadeDisponivel = 0;
        this.quantidadeReservada = 0;
        this.reservas = new HashMap<>();
    }

    public void verificarAdicionarReserva(String pedidoId, int quantidade) {
        if (pedidoId == null) {
            throw new PedidoIdentificadorNuloException();
        }
        if (quantidade <= 0) {
            throw new ItemQuantidadeNegativoException();
        }
        int totalReservado = quantidadeReservada + quantidade;
        if (quantidadeDisponivel < totalReservado) {
            throw new ItemQuantidadeReservadaInvalidaException();
        }
    }

    public void verificarLiberarReserva(String pedidoId) {
        if (pedidoId == null) {
            throw new PedidoIdentificadorNuloException();
        }
    }

    public void verificarConsumirEstoque(String pedidoId) {
        if (pedidoId == null) {
            throw new PedidoIdentificadorNuloException();
        }
        Reserva reserva = reservas.get(pedidoId);
        if (reserva == null) {
            return;
        }
        int quantidade = reserva.getQuantidade();
        if (quantidadeDisponivel < quantidade) {
            throw new ItemQuantidadeNegativoException();
        }
    }

    public void verificarAdicionarEstoque(int quantidade) {
        if (quantidade < 0) {
            throw new ItemQuantidadeNegativoException();
        }
    }

    public void adicionarReserva(String pedidoId, int quantidade) {
        verificarAdicionarReserva(pedidoId, quantidade);
        Reserva reserva = new Reserva(pedidoId, quantidade);
        reservas.put(pedidoId, reserva);
        quantidadeReservada += quantidade;
    }

    public void liberarReserva(String pedidoId) {
        verificarLiberarReserva(pedidoId);
        Reserva reserva = reservas.remove(pedidoId);
        quantidadeReservada -= reserva.getQuantidade();
    }

    public void consumirEstoque(String pedidoId) {
        verificarConsumirEstoque(pedidoId);
        Reserva reserva = reservas.remove(pedidoId);
        quantidadeReservada -= reserva.getQuantidade();
        quantidadeDisponivel -= reserva.getQuantidade();
    }

    public void adicionarEstoque(int quantidade) {
        verificarAdicionarEstoque(quantidade);
        quantidadeDisponivel += quantidade;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public int getQuantidadeReservada() {
        return quantidadeReservada;
    }

    public Map<String, Reserva> getReservas() {
        return Collections.unmodifiableMap(reservas);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ItemEstoque other = (ItemEstoque) obj;
        return itemId.equals(other.itemId);
    }
}
