package com.farmacov.application.usecase;

import com.farmacov.application.dto.BitacoraResponseDto;
import com.farmacov.application.dto.PaginatedResponseDto;
import com.farmacov.domain.models.Bitacora;
import com.farmacov.domain.repository.BitacoraRepository;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ObtenerBitacoraPaginadaUseCase {

    private static final int PAGE_SIZE = 5;

    @Inject
    BitacoraRepository bitacoraRepository;

    @Inject
    UsuariosRepository usuariosRepository;

    public PaginatedResponseDto<BitacoraResponseDto> execute(int page) {
        List<BitacoraResponseDto> data = bitacoraRepository.obtenerPaginado(page, PAGE_SIZE)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        long totalItems = bitacoraRepository.contarTodos();

        return new PaginatedResponseDto<>(data, page, PAGE_SIZE, totalItems);
    }

    private BitacoraResponseDto toDto(Bitacora bitacora) {
        String nombreAdmin = resolverNombrePorId(bitacora.getIdAdmin());
        String nombreAfectado = bitacora.getIdUsuarioAfectado() != null
                ? resolverNombrePorId(bitacora.getIdUsuarioAfectado())
                : "Usuario eliminado";
        return new BitacoraResponseDto(
                bitacora.getIdAdmin(),
                nombreAdmin,
                bitacora.getAccion().name(),
                nombreAfectado,
                bitacora.getCreadoEn()
        );
    }

    private String resolverNombrePorId(java.util.UUID id) {
        return usuariosRepository.findUsuarioById(id)
                .map(u -> u.getNombre() + " " + u.getApellidoPaterno())
                .orElse("Usuario desconocido");
    }
}
