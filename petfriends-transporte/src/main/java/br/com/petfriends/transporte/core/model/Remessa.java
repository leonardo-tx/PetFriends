package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.exception.*;

import java.util.Collections;
import java.util.List;

public final class Remessa {
    private final String almoxarifadoId;
    private final RemessaStatus status;
    private final List<ItemRemessa> items;

    public Remessa(String almoxarifadoId, RemessaStatus status, List<ItemRemessa> items) {
        if (almoxarifadoId == null) {
            throw new AlmoxarifadoIdentificadorNuloException();
        }
        if (items == null || items.isEmpty()) {
            throw new RemessaItensVazioException();
        }
        this.almoxarifadoId = almoxarifadoId;
        this.status = status;
        this.items = items;
    }

    public Remessa separar() {
        if (status != RemessaStatus.EM_SEPARACAO) {
            throw new RemessaInseparavelException();
        }
        return new Remessa(almoxarifadoId, RemessaStatus.SEPARADA, items);
    }

    public Remessa transportar() {
        if (status != RemessaStatus.SEPARADA && status != RemessaStatus.OCORRENCIA) {
            throw new RemessaIntransportavelException();
        }
        return new Remessa(almoxarifadoId, RemessaStatus.EM_ROTA_DE_ENTREGA, items);
    }

    public Remessa entregar() {
        if (status != RemessaStatus.EM_ROTA_DE_ENTREGA) {
            throw new RemessaIntregavelException();
        }
        return new Remessa(almoxarifadoId, RemessaStatus.ENTREGUE, items);
    }

    public Remessa criarOcorrencia() {
        if (status != RemessaStatus.EM_ROTA_DE_ENTREGA) {
            throw new RemessaOcorrenciaIncriavelException();
        }
        return new Remessa(almoxarifadoId, RemessaStatus.OCORRENCIA, items);
    }

    public String getAlmoxarifadoId() {
        return almoxarifadoId;
    }

    public List<ItemRemessa> getItems() {
        return Collections.unmodifiableList(items);
    }

    public RemessaStatus getStatus() {
        return status;
    }
}
