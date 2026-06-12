
// adicion para toggle de permisos
package com.farmacov.infrastructure.config;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

@Converter
public class PermisosConverter
        implements AttributeConverter<Map<String, Boolean>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Boolean> permisos) {
        if (permisos == null || permisos.isEmpty()) return "{}";

        // Construimos el JSON manualmente con jakarta.json
        var builder = Json.createObjectBuilder();
        permisos.forEach(builder::add);
        return builder.build().toString();
    }

    @Override
    public Map<String, Boolean> convertToEntityAttribute(String json) {
        if (json == null || json.isBlank()) return new HashMap<>();

        Map<String, Boolean> permisos = new HashMap<>();
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject obj = reader.readObject();
            obj.forEach((key, value) -> {
                if (value.getValueType() == JsonValue.ValueType.TRUE) {
                    permisos.put(key, true);
                } else if (value.getValueType() == JsonValue.ValueType.FALSE) {
                    permisos.put(key, false);
                }
            });
        }
        return permisos;
    }
}