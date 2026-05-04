package org.acme.domain.repository;

import org.acme.domain.models.SintomaGrave;

import java.util.List;
import java.util.Optional;

public interface SintomaGraveRepository {

    SintomaGrave save(SintomaGrave sintomaGrave);
    Optional<SintomaGrave> getById(Integer id);
    List<SintomaGrave> getAll();
}
