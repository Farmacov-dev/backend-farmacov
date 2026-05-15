-- ===============================================================
-- INSERTS MOCK PARA FARMACOV - BASE DE DATOS DE COVID-19
-- ===============================================================

USE FARMACOV;

-- ===============================================================
-- 1. INSERTS ROLES
-- ===============================================================
INSERT INTO roles (id, nombre, es_admin) VALUES
(1, 'Director Farmacéutico', 1),
(2, 'CTO', 1),
(3, 'Analista de Datos', 0),
(4, 'Coordinador de Vacunación', 0),
(5, 'Vigilancia Epidemiológica', 0);

-- ===============================================================
-- 2. INSERTS USUARIOS
-- Nota: BINARY(16) representa UUIDs. Se usan valores hexadecimales
-- ===============================================================
INSERT INTO usuarios (id, firebase_uuid, nombre, apellido_paterno, apellido_materno, correo, password_hash, id_rol, departamento, creado_en, actualizado_en) VALUES
(UNHEX('550E8400E29B41D4A716446655440000'), 'firebase_uuid_1', 'Carlos', 'Mendoza', 'García', 'carlos.mendoza@farmacov.com', 'hash_password_123456', 1, 'Dirección', NOW(), NOW()),
(UNHEX('550E8400E29B41D4A716446655440001'), 'firebase_uuid_2', 'María', 'López', 'Rodríguez', 'maria.lopez@farmacov.com', 'hash_password_234567', 2, 'Tecnología', NOW(), NOW()),
(UNHEX('550E8400E29B41D4A716446655440002'), 'firebase_uuid_3', 'Juan', 'Pérez', 'Flores', 'juan.perez@farmacov.com', 'hash_password_345678', 3, 'Análisis', NOW(), NOW()),
(UNHEX('550E8400E29B41D4A716446655440003'), 'firebase_uuid_4', 'Ana', 'Gutiérrez', 'Morales', 'ana.gutierrez@farmacov.com', 'hash_password_456789', 4, 'Operaciones', NOW(), NOW()),
(UNHEX('550E8400E29B41D4A716446655440004'), 'firebase_uuid_5', 'Roberto', 'Martínez', 'Santos', 'roberto.martinez@farmacov.com', 'hash_password_567890', 5, 'Vigilancia', NOW(), NOW());

-- ===============================================================
-- 3. INSERTS BITACORA (Auditoría)
-- ===============================================================
INSERT INTO bitacora (id_admin, accion, id_usuario_afectado, creado_en) VALUES
(UNHEX('550E8400E29B41D4A716446655440000'), 'CREATE', UNHEX('550E8400E29B41D4A716446655440003'), NOW()),
(UNHEX('550E8400E29B41D4A716446655440000'), 'CREATE', UNHEX('550E8400E29B41D4A716446655440004'), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(UNHEX('550E8400E29B41D4A716446655440001'), 'UPDATE', UNHEX('550E8400E29B41D4A716446655440002'), DATE_SUB(NOW(), INTERVAL 3 DAY)),
(UNHEX('550E8400E29B41D4A716446655440000'), 'DELETE', UNHEX('550E8400E29B41D4A716446655440003'), DATE_SUB(NOW(), INTERVAL 1 DAY));

-- ===============================================================
-- 4. INSERTS ANOTACIONES
-- ===============================================================
INSERT INTO anotaciones (id_usuario, dashboard_referencia, titulo, observaciones, creado_en, actualizado_en) VALUES
(UNHEX('550E8400E29B41D4A716446655440002'), 'dashboard_vacunas_tendencias', 'Aumento de reportes en Marzo', 'Se observó un incremento del 15% en reportes adversos durante marzo 2026', NOW(), NOW()),
(UNHEX('550E8400E29B41D4A716446655440004'), 'dashboard_distribuccion_edad', 'Patrón en grupo 30-49', 'Mayor concentración de eventos adversos en el grupo etario 30-49 años', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(UNHEX('550E8400E29B41D4A716446655440003'), 'dashboard_costos_vacunas', 'Análisis de costos Q1 2026', 'Las vacunas de ARNm tienen un costo 20x mayor que las inactivadas', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

-- ===============================================================
-- 5. INSERTS FARMACO
-- Basado en los principios activos de las vacunas proporcionadas
-- ===============================================================
INSERT INTO farmaco (id, nombre, tipo, descripcion, creado_en, actualizado_en) VALUES
(1, 'tozinameran', 'ARN Mensajero', 'ARNm que codifica la proteína Spike de SARS-CoV-2 encapsulada en nanopartículas lipídicas', NOW(), NOW()),
(2, 'elasomeran', 'ARN Mensajero', 'ARNm modificado que codifica la proteína Spike en forma de espiga con modificaciones químicas', NOW(), NOW()),
(3, 'NVX-CoV2373', 'Proteína Recombinante', 'Proteína Spike recombinante nanométrica purificada con adyuvante Matrix-M', NOW(), NOW()),
(4, 'recombinant glycoprotein E antigen', 'Proteína Recombinante', 'Proteína Spike recombinante con adyuvante combinado', NOW(), NOW()),
(5, 'ChAdOx1 nCoV-19', 'Vector Viral (Adenovirus)', 'Adenovirus de chimpancé que codifica la proteína Spike', NOW(), NOW()),
(6, 'Ad26.COV2.S', 'Vector Viral (Adenovirus)', 'Adenovirus humano tipo 26 que codifica la proteína Spike', NOW(), NOW()),
(7, 'Virus inactivado SARS-CoV-2', 'Virus Inactivado', 'Virus SARS-CoV-2 inactivado mediante tratamiento químico', NOW(), NOW()),
(8, 'BBV152', 'Virus Inactivado', 'Virus SARS-CoV-2 inactivado con adyuvante Alum', NOW(), NOW()),
(9, 'ZF2001', 'Proteína Recombinante', 'Proteína del receptor de unión (RBD) recombinante purificada', NOW(), NOW());

-- ===============================================================
-- 6. INSERTS VACUNAS
-- Con relaciones correctas a la tabla farmaco
-- ===============================================================
INSERT INTO vacunas (id_farmaco, nombre, farmaceutica, tipo, descripcion_general, creado_en, actualizado_en) VALUES
(1, 'Comirnaty', 'Pfizer-BioNTech', 'ARN Mensajero', 'Vacuna de ARNm de Pfizer contra COVID-19, la más distribuida globalmente', NOW(), NOW()),
(2, 'Spikevax', 'Moderna', 'ARN Mensajero', 'Vacuna de ARNm de Moderna con mayor estabilidad térmica mejorada', NOW(), NOW()),
(3, 'Nuvaxovid', 'Sanofi/GSK', 'Proteína Recombinante', 'Vacuna de proteína recombinante con adyuvante Matrix-M', NOW(), NOW()),
(4, 'Vidprevtyn', 'Sanofi/GSK', 'Proteína Recombinante', 'Vacuna recombinante con proteína Spike y adyuvante AS01B', NOW(), NOW()),
(5, 'Vaxzevria', 'AstraZeneca', 'Vector Viral', 'Vacuna de vector viral basada en adenovirus de chimpancé', NOW(), NOW()),
(6, 'Ad26.COV2-S', 'Janssen', 'Vector Viral', 'Vacuna de vector viral con adenovirus humano tipo 26', NOW(), NOW()),
(7, 'CoronaVac', 'Sinovac', 'Virus Inactivado', 'Vacuna de virus inactivado, ampliamente utilizada en Asia y América Latina', NOW(), NOW()),
(8, 'Covishield', 'Serum Institute (SII)', 'Vector Viral', 'Versión india de AstraZeneca de bajo costo', NOW(), NOW()),
(8, 'Covaxin', 'Bharat Biotech', 'Virus Inactivado', 'Vacuna india de virus inactivado con adyuvante Alum', NOW(), NOW()),
(7, 'BBIBP-CorV', 'Sinopharm (Beijing CNBG)', 'Virus Inactivado', 'Vacuna de Sinopharm, aprobada por OMS, uso extendido en Asia y África', NOW(), NOW()),
(9, 'ZyCov-D', 'Zydus Cadila', 'ADN Plasmídico', 'Vacuna de ADN plasmídico, vacuna basada en plásmido de bajo costo', NOW(), NOW()),
(2, 'Spikevax Bivalent', 'Moderna', 'ARN Mensajero', 'Vacuna bivalente de Moderna contra variantes Ómicron', NOW(), NOW()),
(1, 'Comirnaty Bivalent', 'Pfizer-BioNTech', 'ARN Mensajero', 'Vacuna bivalente de Pfizer contra variantes Ómicron', NOW(), NOW()),
(3, 'Nano Covax', 'Nanogen', 'Proteína Recombinante', 'Vacuna de proteína recombinante vietnamita', NOW(), NOW()),
(5, 'Convidecia', 'CanSino', 'Vector Viral', 'Vacuna de vector viral con administración intranasal', NOW(), NOW()),
(1, 'Sputnik V', 'Instituto Gamaleya', 'Vector Viral', 'Vacuna rusa de dos vectores virales diferentes (Prime-Boost)', NOW(), NOW()),
(1, 'Sputnik Light', 'Instituto Gamaleya', 'Vector Viral', 'Versión de una sola dosis del Sputnik V', NOW(), NOW()),
(3, 'Corbevax', 'Biological E', 'Proteína Recombinante', 'Vacuna india de proteína recombinante', NOW(), NOW()),
(7, 'CoronaVac Pediátrica', 'Sinovac', 'Virus Inactivado', 'Versión pediátrica de CoronaVac con dosis reducida', NOW(), NOW()),
(9, 'Abdala', 'CIGB (Cuba)', 'Proteína Recombinante', 'Vacuna cubana de proteína recombinante de tres dosis', NOW(), NOW()),
(9, 'Soberana 02', 'Instituto Finlay (Cuba)', 'Proteína Recombinante', 'Vacuna cubana de proteína recombinante conjugada', NOW(), NOW());

-- ===============================================================
-- 7. INSERTS VACUNA_CONDICIONES
-- Condiciones de almacenamiento basadas en datos proporcionados
-- ===============================================================
INSERT INTO vacuna_condiciones (id_vacuna, temperatura, tiempo_ambiente, creado_en, actualizado_en) VALUES
(1, -80.0, 12.0, NOW(), NOW()),      -- Comirnaty (ultra-frío)
(1, -25.0, 12.0, NOW(), NOW()),      -- Comirnaty (congelador estándar)
(1, 2.0, 12.0, NOW(), NOW()),        -- Comirnaty (refrigerador estándar)
(2, -20.0, 24.0, NOW(), NOW()),      -- Spikevax
(2, 2.0, 24.0, NOW(), NOW()),        -- Spikevax
(3, 2.0, 12.0, NOW(), NOW()),        -- Nuvaxovid
(4, 2.0, 12.0, NOW(), NOW()),        -- Vidprevtyn
(5, 2.0, 16.0, NOW(), NOW()),        -- Vaxzevria
(6, 2.0, 12.0, NOW(), NOW()),        -- Ad26.COV2-S
(7, 2.0, 16.0, NOW(), NOW()),        -- CoronaVac
(8, 2.0, 16.0, NOW(), NOW()),        -- Covishield
(9, 2.0, 16.0, NOW(), NOW()),        -- Covaxin
(10, 2.0, 16.0, NOW(), NOW()),       -- BBIBP-CorV
(11, 2.0, 12.0, NOW(), NOW()),       -- ZyCov-D
(12, 2.0, 24.0, NOW(), NOW()),       -- Spikevax Bivalent
(13, -80.0, 12.0, NOW(), NOW()),     -- Comirnaty Bivalent
(14, 2.0, 12.0, NOW(), NOW()),       -- Nano Covax
(15, 2.0, 16.0, NOW(), NOW()),       -- Convidecia
(16, -20.0, 24.0, NOW(), NOW()),     -- Sputnik V
(17, -20.0, 24.0, NOW(), NOW()),     -- Sputnik Light
(18, 2.0, 12.0, NOW(), NOW()),       -- Corbevax
(19, 2.0, 16.0, NOW(), NOW()),       -- CoronaVac Pediátrica
(20, 2.0, 12.0, NOW(), NOW()),       -- Abdala
(21, 2.0, 12.0, NOW(), NOW()),       -- Soberana 02
(22, 2.0, 12.0, NOW(), NOW());       -- Soberana Plus

-- ===============================================================
-- 8. INSERTS VACUNA_COSTOS
-- Costos basados en datos proporcionados (en USD)
-- ===============================================================
INSERT INTO vacuna_costos (id_vacuna, costo_unitario, creado_en, actualizado_en) VALUES
(1, 114.25, NOW(), NOW()),           -- Comirnaty
(2, 110.61, NOW(), NOW()),           -- Spikevax
(3, 122.24, NOW(), NOW()),           -- Nuvaxovid
(4, 10.45, NOW(), NOW()),            -- Vidprevtyn
(5, 4.00, NOW(), NOW()),             -- Vaxzevria
(6, 7.50, NOW(), NOW()),             -- Ad26.COV2-S
(7, 5.50, NOW(), NOW()),             -- CoronaVac
(8, 3.41, NOW(), NOW()),             -- Covishield
(9, 3.00, NOW(), NOW()),             -- Covaxin
(10, 15.00, NOW(), NOW()),           -- BBIBP-CorV
(11, 3.66, NOW(), NOW()),            -- ZyCov-D
(12, 1.92, NOW(), NOW()),            -- Spikevax Bivalent
(13, 15.00, NOW(), NOW()),           -- Comirnaty Bivalent
(14, 7.00, NOW(), NOW()),            -- Nano Covax
(15, 7.00, NOW(), NOW()),            -- Convidecia
(16, 7.00, NOW(), NOW()),            -- Sputnik V
(17, 11.88, NOW(), NOW()),           -- Sputnik Light
(18, 12.95, NOW(), NOW()),           -- Corbevax
(19, 30.00, NOW(), NOW()),           -- CoronaVac Pediátrica
(20, 5.19, NOW(), NOW()),            -- Abdala
(21, 3.57, NOW(), NOW()),            -- Soberana 02
(22, 4.50, NOW(), NOW());            -- Soberana Plus

-- ===============================================================
-- 9. INSERTS EFECTOS_SECUNDARIOS
-- Efectos secundarios comunes documentados
-- ===============================================================
INSERT INTO efectos_secundarios (id_vacuna, descripcion, severidad) VALUES
(1, 'Dolor en el sitio de inyección', 'leve'),
(1, 'Fatiga', 'leve'),
(1, 'Dolor de cabeza', 'leve'),
(1, 'Fiebre', 'moderado'),
(1, 'Miocarditis (casos raros)', 'grave'),
(2, 'Dolor en el sitio de inyección', 'leve'),
(2, 'Fatiga', 'leve'),
(2, 'Fiebre', 'moderado'),
(3, 'Dolor muscular', 'leve'),
(3, 'Fatiga', 'leve'),
(3, 'Fiebre', 'moderado'),
(5, 'Fiebre', 'moderado'),
(5, 'Trombosis con trombocitopenia (TTS)', 'grave'),
(5, 'Síndrome de Guillain-Barré', 'grave'),
(6, 'Reacción local', 'leve'),
(6, 'Fiebre', 'moderado'),
(7, 'Dolor de cabeza', 'leve'),
(7, 'Mialgia', 'leve'),
(7, 'Fiebre baja', 'leve'),
(9, 'Dolor en el sitio de inyección', 'leve'),
(9, 'Fiebre', 'moderado');

-- ===============================================================
-- 10. INSERTS SINTOMAS_GRAVES
-- Síntomas graves que requieren atención médica inmediata
-- ===============================================================
INSERT INTO sintomas_graves (id_vacuna, nombre) VALUES
(1, 'Dificultad para respirar'),
(1, 'Dolor en el pecho'),
(1, 'Confusión'),
(1, 'Parálisis facial'),
(2, 'Dificultad para respirar'),
(2, 'Dolor en el pecho'),
(2, 'Edema facial'),
(3, 'Dificultad para respirar'),
(3, 'Hipersensibilidad generalizada'),
(5, 'Dolor en extremidades'),
(5, 'Dificultad para respirar'),
(5, 'Debilidad progresiva'),
(6, 'Anafilaxia'),
(6, 'Edema orofaríngeo'),
(7, 'Dificultad para respirar'),
(9, 'Convulsiones'),
(9, 'Pérdida de conciencia');

-- ===============================================================
-- 11. INSERTS REPORTES_ADVERSOS
-- Reportes de eventos adversos con datos realistas
-- ===============================================================
-- Nota: id es BIGINT pero sin AUTO_INCREMENT, usamos números específicos
INSERT INTO reportes_adversos (id, id_vacuna, id_sintoma, sexo, grupo_edad, es_grave, fecha_reporte, creado_en) VALUES
-- Reportes Comirnaty (id_vacuna = 1)
(1001, 1, 1, 'F', '30-49', 0, DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
(1002, 1, 1, 'M', '50-64', 0, DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY)),
(1003, 1, 2, 'F', '18-29', 0, DATE_SUB(CURDATE(), INTERVAL 13 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY)),
(1004, 1, 3, 'M', '65+', 0, DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),
(1005, 1, 4, 'F', '30-49', 1, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(1006, 1, 5, 'M', '18-29', 1, DATE_SUB(CURDATE(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(1007, 1, 1, 'F', '0-17', 0, DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(1008, 1, 2, 'M', '30-49', 0, DATE_SUB(CURDATE(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
(1009, 1, 1, 'U', 'DESCONOCIDO', 0, DATE_SUB(CURDATE(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
(1010, 1, 3, 'F', '50-64', 0, DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),

-- Reportes Spikevax (id_vacuna = 2)
(2001, 2, 1, 'M', '30-49', 0, DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),
(2002, 2, 2, 'F', '18-29', 0, DATE_SUB(CURDATE(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY)),
(2003, 2, 3, 'M', '50-64', 0, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2004, 2, 4, 'F', '65+', 1, DATE_SUB(CURDATE(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(2005, 2, 1, 'M', '30-49', 0, DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(2006, 2, 2, 'F', '0-17', 0, DATE_SUB(CURDATE(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),

-- Reportes Nuvaxovid (id_vacuna = 3)
(3001, 3, 1, 'F', '50-64', 0, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(3002, 3, 2, 'M', '30-49', 0, DATE_SUB(CURDATE(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(3003, 3, 3, 'F', '18-29', 0, DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(3004, 3, 1, 'M', '65+', 0, DATE_SUB(CURDATE(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),

-- Reportes Vaxzevria (id_vacuna = 5)
(5001, 5, 1, 'F', '30-49', 1, DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 20 DAY)),
(5002, 5, 2, 'M', '50-64', 1, DATE_SUB(CURDATE(), INTERVAL 18 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY)),
(5003, 5, 3, 'F', '65+', 0, DATE_SUB(CURDATE(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 16 DAY)),
(5004, 5, 1, 'M', '30-49', 1, DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY)),

-- Reportes CoronaVac (id_vacuna = 7)
(7001, 7, 1, 'F', '50-64', 0, DATE_SUB(CURDATE(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY)),
(7002, 7, 2, 'M', '18-29', 0, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(7003, 7, 1, 'F', '30-49', 0, DATE_SUB(CURDATE(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(7004, 7, 3, 'M', '0-17', 0, DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),

-- Reportes Covaxin (id_vacuna = 9)
(9001, 9, 1, 'F', '30-49', 0, DATE_SUB(CURDATE(), INTERVAL 13 DAY), DATE_SUB(NOW(), INTERVAL 13 DAY)),
(9002, 9, 2, 'M', '50-64', 0, DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),
(9003, 9, 3, 'F', '18-29', 0, DATE_SUB(CURDATE(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY));

-- ===============================================================
-- RESUMEN DE DATOS INSERTADOS
-- ===============================================================
-- Roles: 5
-- Usuarios: 5
-- Registros en Bitácora: 4
-- Anotaciones: 3
-- Fármacos: 9
-- Vacunas: 22
-- Condiciones de almacenamiento: 23
-- Costos de vacunas: 22
-- Efectos secundarios: 21
-- Síntomas graves: 17
-- Reportes adversos: 35
-- ===============================================================

-- Verificación de integridad
SELECT COUNT(*) as total_roles FROM roles;
SELECT COUNT(*) as total_usuarios FROM usuarios;
SELECT COUNT(*) as total_vacunas FROM vacunas;
SELECT COUNT(*) as total_reportes FROM reportes_adversos;
