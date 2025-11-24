package br.com.petfriends.pedido.core.model;

public enum PedidoStatus {
    CRIADO,
    AGUARDANDO_PROCESSAMENTO,
    EM_SEPARACAO,
    PRONTO_PARA_TRANSPORTE,
    ENVIADO,
    ENTREGUE,
    CANCELADO
}
