package br.com.grupo27.techchallange01.adapter.driven.infrastructure.repositories;

import br.com.grupo27.techchallange01.adapter.driven.infrastructure.entities.SobremesaEntity;
import br.com.grupo27.techchallange01.adapter.driven.infrastructure.repositories.JPA.SobremesaRepositoryJPA;
import br.com.grupo27.techchallange01.core.domain.Sobremesa;
import br.com.grupo27.techchallange01.core.domain.ports.repository.SobremesaRepositoryPort;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SobremesaRepositoryAdapter implements SobremesaRepositoryPort {

    private final SobremesaRepositoryJPA sobremesaRepositoryJPA;

    public SobremesaRepositoryAdapter(SobremesaRepositoryJPA sobremesaRepositoryJPA) {
        this.sobremesaRepositoryJPA = sobremesaRepositoryJPA;
    }

    @Override
    public Sobremesa saveSobremesa(Sobremesa sobremesa) {
        SobremesaEntity sobremesaEntity = new SobremesaEntity(sobremesa.getNome(), sobremesa.getDescricao(), sobremesa.getPreco());
        sobremesaEntity = sobremesaRepositoryJPA.save(sobremesaEntity);
        return sobremesaEntity.toSobremesa();
    }

    @Override
    public Sobremesa updateSobremesa(Long id, Sobremesa sobremesa) {
        return sobremesaRepositoryJPA.findById(id).map(sobremesaEntity -> {
            sobremesaEntity.setNome(sobremesa.getNome());
            sobremesaEntity.setDescricao(sobremesa.getDescricao());
            sobremesaEntity.setPreco(sobremesa.getPreco());
            sobremesaEntity = sobremesaRepositoryJPA.save(sobremesaEntity);
            return sobremesaEntity.toSobremesa();
        }).orElse(null);
    }

    @Override
    public Sobremesa findSobremesaById(Long id) {
        return sobremesaRepositoryJPA.findById(id).map(SobremesaEntity::toSobremesa).orElse(null);
    }

    @Override
    public boolean deleteSobremesa(Long id) {
        if (sobremesaRepositoryJPA.existsById(id)) {
            sobremesaRepositoryJPA.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Sobremesa> listAllSobremesas() {
        return sobremesaRepositoryJPA.findAll().stream()
                .map(SobremesaEntity::toSobremesa)
                .collect(Collectors.toList());
    }
}