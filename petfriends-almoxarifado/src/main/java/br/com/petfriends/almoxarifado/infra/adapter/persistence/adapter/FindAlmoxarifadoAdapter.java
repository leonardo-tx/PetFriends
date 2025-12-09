package br.com.petfriends.almoxarifado.infra.adapter.persistence.adapter;

import br.com.petfriends.almoxarifado.core.model.Almoxarifado;
import br.com.petfriends.almoxarifado.core.port.out.FindAlmoxarifadoPort;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.mapper.AlmoxarifadoInfraMapper;
import br.com.petfriends.almoxarifado.infra.adapter.persistence.repository.AlmoxarifadoMongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FindAlmoxarifadoAdapter implements FindAlmoxarifadoPort {
    private final AlmoxarifadoMongoRepository almoxarifadoMongoRepository;
    private final AlmoxarifadoInfraMapper almoxarifadoInfraMapper;

    public FindAlmoxarifadoAdapter(AlmoxarifadoMongoRepository almoxarifadoMongoRepository, AlmoxarifadoInfraMapper almoxarifadoInfraMapper) {
        this.almoxarifadoMongoRepository = almoxarifadoMongoRepository;
        this.almoxarifadoInfraMapper = almoxarifadoInfraMapper;
    }

    @Override
    public Optional<Almoxarifado> findById(UUID id) {
        return almoxarifadoMongoRepository.findById(id)
                .map(almoxarifadoInfraMapper::toModel);
    }

    @Override
    public Optional<Almoxarifado> findFirstByItemDisponivel(String itemId, int quantidadePedida) {
        return almoxarifadoMongoRepository.findFirstByItemDisponivel(itemId, quantidadePedida)
                .map(almoxarifadoInfraMapper::toModel);
    }

    @Override
    public List<Almoxarifado> findByPedidoIdInReservas(String pedidoId) {
        return almoxarifadoMongoRepository.findByPedidoIdInReservas(pedidoId)
                .stream()
                .map(almoxarifadoInfraMapper::toModel)
                .toList();
    }
}
