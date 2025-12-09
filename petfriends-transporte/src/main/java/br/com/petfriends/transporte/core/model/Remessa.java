package br.com.petfriends.transporte.core.model;

import br.com.petfriends.transporte.core.exception.*;

import java.util.Collections;
import java.util.List;

public final class Remessa {
    private final String almoxarifadoId;
    private RemessaStatus status;
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

    public void verificarSeparar() {
        if (status != RemessaStatus.EM_SEPARACAO) {
            throw new RemessaInseparavelException();
        }
    }

    public void verificarTransportar() {
        if (status != RemessaStatus.SEPARADA && status != RemessaStatus.OCORRENCIA) {
            throw new RemessaIntransportavelException();
        }
    }

    public void verificarEntregar() {
        if (status != RemessaStatus.EM_ROTA_DE_ENTREGA) {
            throw new RemessaIntregavelException();
        }
    }

    public void verificarCriarOcorrencia() {
        if (status != RemessaStatus.EM_ROTA_DE_ENTREGA) {
            throw new RemessaOcorrenciaIncriavelException();
        }
    }

    public void separar() {
        this.status = RemessaStatus.SEPARADA;
    }

    public void transportar() {
        this.status = RemessaStatus.EM_ROTA_DE_ENTREGA;
    }

    public void entregar() {
        this.status = RemessaStatus.ENTREGUE;
    }

    public void criarOcorrencia() {
        this.status = RemessaStatus.OCORRENCIA;
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
