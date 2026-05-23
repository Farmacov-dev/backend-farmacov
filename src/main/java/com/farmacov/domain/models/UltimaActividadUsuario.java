package com.farmacov.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class UltimaActividadUsuario {

    private UUID idUsuario;
    private String endpoint;
    private String metodoHttp;
    private Integer statusCode;
    private String queryString;
    private String userAgent;
    private String ipCliente;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    public UltimaActividadUsuario() {
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getMetodoHttp() {
        return metodoHttp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setMetodoHttp(String metodoHttp) {
        this.metodoHttp = metodoHttp;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
