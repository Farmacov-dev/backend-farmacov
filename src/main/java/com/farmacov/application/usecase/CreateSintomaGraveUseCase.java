package com.farmacov.application.usecase;

import com.farmacov.application.dto.CreateSintomaGraveDto;
import com.farmacov.domain.models.SintomaGrave;
import com.farmacov.domain.repository.SintomaGraveRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CreateSintomaGraveUseCase {

    private final SintomaGraveRepository sintomaGraveRepository;

    @Inject
    public CreateSintomaGraveUseCase(SintomaGraveRepository sintomaGraveRepository){
        this.sintomaGraveRepository = sintomaGraveRepository;
    }

    @Transactional
    public SintomaGrave execute(CreateSintomaGraveDto dto){
        SintomaGrave sintomaGrave = new SintomaGrave();

        sintomaGrave.setIdVacuna(dto.getIdVacuna());
        sintomaGrave.setNombre(dto.getNombre());

        return sintomaGraveRepository.save(sintomaGrave);
    }

    public SintomaGrave getById(Integer id) {
        return sintomaGraveRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Sintoma grave con id " + id + " no encontrado"));
    }

    public List<SintomaGrave> getAll() {
        return sintomaGraveRepository.getAll();
    }
}
