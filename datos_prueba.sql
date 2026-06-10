-- Script para insertar datos de prueba en la base de datos campos_futbol
-- Fecha: 6 de diciembre de 2025

USE campos_futbol;

-- Limpiar datos existentes (opcional, comentar si no quieres borrar)
-- DELETE FROM factura;
-- DELETE FROM alquiler;
-- DELETE FROM cliente;
-- DELETE FROM campo_futbol;
-- DELETE FROM usuario WHERE codigo_usuario > 2;

-- ============================================
-- 1. INSERTAR CAMPOS DE FÚTBOL
-- ============================================
INSERT INTO campo_futbol (nombre_campo, ubicacion_campo, tipo_cesped, capacidad_personas, precio_hora, disponibilidad) 
VALUES 
('Cancha Fútbol 11 Premium', 'Av. Los Deportes 456', 'Sintético', 22, 120.00, true),
('Cancha Fútbol 7 Norte', 'Jr. Deportivo 789', 'Natural', 14, 80.00, true);

-- ============================================
-- 2. INSERTAR CLIENTES
-- ============================================
INSERT INTO cliente (dni, nombre_completo, telefono, email, direccion, fecha_registro, estado) 
VALUES 
('12345678', 'Carlos Alberto Mendoza García', '987654321', 'carlos.mendoza@email.com', 'Av. Principal 123, Lima', '2025-12-01', 'Activo', 'Activo'),
('87654321', 'María Elena Rodríguez López', '998877665', 'maria.rodriguez@email.com', 'Jr. Los Olivos 456, Lima', '2025-12-02', 'Activo', 'Activo'),
('11223344', 'Dani Codex', '999688777', 'danicodex@canchas.com', 'Calle Los Robles 789, Lima', '2025-01-01', 'Activo', 'Activo');

INSERT INTO usuario (nombre_completo, nombre_usuario, email, contrasena, rol, telefono, estado, fecha_registro, codigo_cliente) VALUES
('Dani Codex', 'danicodex', 'danicodex@canchas.com', '$2a$10$8XeC/j1qN8vXFjQXGKVYJ.yqP4VYmJVkJXvXQqLKvXFjQXGKVYJe.', 'Cliente', '999688777', 'Activo', '2025-01-01', 3);

-- ============================================
-- 3. INSERTAR ALQUILERES
-- ============================================
-- Obtener los IDs generados automáticamente
SET @campo1_id = (SELECT codigo_campo FROM campo_futbol WHERE nombre_campo = 'Cancha Fútbol 11 Premium' LIMIT 1);
SET @campo2_id = (SELECT codigo_campo FROM campo_futbol WHERE nombre_campo = 'Cancha Fútbol 7 Norte' LIMIT 1);
SET @cliente1_id = (SELECT codigo_cliente FROM cliente WHERE dni = '12345678' LIMIT 1);
SET @cliente2_id = (SELECT codigo_cliente FROM cliente WHERE dni = '87654321' LIMIT 1);

INSERT INTO alquiler (codigo_cliente, codigo_campo, fecha_alquiler, hora_inicio, hora_fin, total_horas, precio_total, estado, observaciones, metodo_pago) 
VALUES 
(@cliente1_id, @campo1_id, '2025-12-05', '15:00:00', '17:00:00', 2, 240.00, 'Confirmado', 'Partido amistoso', 'Tarjeta'),
(@cliente2_id, @campo2_id, '2025-12-06', '18:00:00', '20:00:00', 2, 160.00, 'Confirmado', 'Entrenamiento de equipo', 'Efectivo');

-- ============================================
-- 4. INSERTAR FACTURAS
-- ============================================
-- Obtener los IDs de alquileres
SET @alquiler1_id = (SELECT codigo_alquiler FROM alquiler WHERE codigo_cliente = @cliente1_id AND fecha_alquiler = '2025-12-05' LIMIT 1);
SET @alquiler2_id = (SELECT codigo_alquiler FROM alquiler WHERE codigo_cliente = @cliente2_id AND fecha_alquiler = '2025-12-06' LIMIT 1);

-- Calcular subtotal e IGV (18%)
SET @subtotal1 = 240.00 / 1.18;
SET @igv1 = 240.00 - @subtotal1;
SET @subtotal2 = 160.00 / 1.18;
SET @igv2 = 160.00 - @subtotal2;

INSERT INTO factura (numero_factura, codigo_alquiler, fecha_emision, subtotal, igv, total, estado_pago) 
VALUES 
('F-00001', @alquiler1_id, '2025-12-05', @subtotal1, @igv1, 240.00, 'Pagado'),
('F-00002', @alquiler2_id, '2025-12-06', @subtotal2, @igv2, 160.00, 'Pagado');

-- ============================================
-- 5. INSERTAR IMPLEMENTOS
-- ============================================
INSERT INTO implemento (nombre, stock_total, precio_alquiler) VALUES 
('Balón Fútbol 7', 10, 5.00),
('Chalecos Entrenamiento', 20, 2.00);

INSERT INTO alquiler_implemento (codigo_alquiler, codigo_implemento, cantidad, precio_total, estado_devolucion) VALUES 
(@alquiler1_id, 1, 1, 5.00, 'Devuelto'),
(@alquiler2_id, 2, 2, 4.00, 'Pendiente');

-- ============================================
-- VERIFICAR DATOS INSERTADOS
-- ============================================
SELECT '==================== CAMPOS DE FÚTBOL ====================' AS '';
SELECT * FROM campo_futbol ORDER BY codigo_campo DESC LIMIT 2;

SELECT '==================== CLIENTES ====================' AS '';
SELECT * FROM cliente ORDER BY codigo_cliente DESC LIMIT 2;

SELECT '==================== ALQUILERES ====================' AS '';
SELECT 
    a.codigo_alquiler,
    c.nombre_completo AS cliente,
    cf.nombre_campo AS campo,
    a.fecha_alquiler,
    a.hora_inicio,
    a.hora_fin,
    a.total_horas,
    a.precio_total,
    a.estado
FROM alquiler a
JOIN cliente c ON a.codigo_cliente = c.codigo_cliente
JOIN campo_futbol cf ON a.codigo_campo = cf.codigo_campo
ORDER BY a.codigo_alquiler DESC LIMIT 2;

SELECT '==================== FACTURAS ====================' AS '';
SELECT 
    f.codigo_factura,
    f.numero_factura,
    c.nombre_completo AS cliente,
    f.fecha_emision,
    f.subtotal,
    f.igv,
    f.total,
    f.estado_pago
FROM factura f
JOIN alquiler a ON f.codigo_alquiler = a.codigo_alquiler
JOIN cliente c ON a.codigo_cliente = c.codigo_cliente
ORDER BY f.codigo_factura DESC LIMIT 2;

SELECT '==================== RESUMEN ====================' AS '';
SELECT 
    (SELECT COUNT(*) FROM campo_futbol) AS total_campos,
    (SELECT COUNT(*) FROM cliente) AS total_clientes,
    (SELECT COUNT(*) FROM alquiler) AS total_alquileres,
    (SELECT COUNT(*) FROM factura) AS total_facturas,
    (SELECT SUM(total) FROM factura) AS ingresos_totales;
