package org.acme.application.usecase;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.CreateSintomaGraveDto;
import org.acme.domain.models.SintomaGrave;
import org.acme.domain.repository.SintomaGraveRepository;

@ApplicationScoped
public class CreateSintomaGraveUseCase {

    private final SintomaGraveRepository sintomaGraveRepository;

    @Inject
    public CreateSintomaGraveUseCase(SintomaGraveRepository sintomaGraveRepository){
        this.sintomaGraveRepository = sintomaGraveRepository;
    }

    public SintomaGrave execute(CreateSintomaGraveDto dto){
        SintomaGrave sintomaGrave = new SintomaGrave();

        sintomaGrave.setIdVacuna(dto.getIdVacuna());
        sintomaGrave.setNombre(dto.getNombre());

        return sintomaGraveRepository.save(sintomaGrave);
    }
}
