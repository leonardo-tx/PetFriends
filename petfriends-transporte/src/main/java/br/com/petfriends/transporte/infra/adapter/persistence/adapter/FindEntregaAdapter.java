package br.com.petfriends.transporte.infra.adapter.persistence.adapter;

import br.com.petfriends.transporte.core.model.Entrega;
import br.com.petfriends.transporte.core.port.out.FindEntregaPort;
import br.com.petfriends.transporte.infra.adapter.persistence.mapper.EntregaInfraMapper;
import br.com.petfriends.transporte.infra.adapter.persistence.repository.EntregaMongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindEntregaAdapter implements FindEntregaPort {
    private final EntregaMongoRepository entregaMongoRepository;
    private final EntregaInfraMapper entregaInfraMapper;

    public FindEntregaAdapter(EntregaMongoRepository entregaMongoRepository, EntregaInfraMapper entregaInfraMapper) {
        this.entregaMongoRepository = entregaMongoRepository;
        this.entregaInfraMapper = entregaInfraMapper;
    }

    @Override
    public Optional<Entrega> findById(UUID id) {
        return entregaMongoRepository.findById(id)
                .map(entregaInfraMapper::toModel);
    }

    @Override
    public Optional<Entrega> findByPedidoId(String id) {
        return entregaMongoRepository.findByPedidoId(id)
                .map(entregaInfraMapper::toModel);
    }
}
