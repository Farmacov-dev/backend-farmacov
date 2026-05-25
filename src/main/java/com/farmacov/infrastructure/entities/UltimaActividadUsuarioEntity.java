package com.farmacov.infrastructure.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "usuario_ultima_actividad")
public class UltimaActividadUsuarioEntity {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id_usuario", length = 16, nullable = false)
    private UUID idUsuario;

    @Column(nullable = false, length = 255)
    private String endpoint;

    @Column(name = "metodo_http", nullable = false, length = 10)
    private String metodoHttp;

    @Column(name = "status_code", nullable = false)
    private Integer statusCode;

    @Column(name = "query_string", columnDefinition = "TEXT")
    private String queryString;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @Column(name = "ip_cliente", length = 45)
    private String ipCliente;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    public UltimaActividadUsuarioEntity() {
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
