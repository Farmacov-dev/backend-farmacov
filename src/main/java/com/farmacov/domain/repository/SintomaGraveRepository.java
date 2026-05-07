package com.farmacov.domain.repository;

import com.farmacov.domain.models.SintomaGrave;

import java.util.List;
import java.util.Optional;

public interface SintomaGraveRepository {

    SintomaGrave save(SintomaGrave sintomaGrave);
    Optional<SintomaGrave> getById(Integer id);
    List<SintomaGrave> getAll();
}
