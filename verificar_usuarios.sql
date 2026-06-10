-- Verificar usuarios existentes en la base de datos
USE campos_futbol;

-- Mostrar todos los usuarios
SELECT 
    codigo_usuario,
    nombre_completo,
    nombre_usuario,
    email,
    rol,
    estado,
    fecha_registro
FROM usuario;

-- Si necesitas crear/actualizar contraseñas manualmente:
-- Las contraseñas están encriptadas con BCrypt
-- admin123 -> $2a$10$XlSqwp3rr7M4VJqHZUkqx.vXvxLQjqKPIYlvGxLxQV0kJxYJZVKM.
-- asesor123 -> $2a$10$8XeC/j1qN8vXFjQXGKVYJ.yqP4VYmJVkJXvXQqLKvXFjQXGKVYJe.

-- Actualizar contraseña de admin (si es necesario)
-- UPDATE usuario SET contrasena = '$2a$10$XlSqwp3rr7M4VJqHZUkqx.vXvxLQjqKPIYlvGxLxQV0kJxYJZVKM.' WHERE nombre_usuario = 'admin';

-- Actualizar contraseña de asesor (si es necesario)
-- UPDATE usuario SET contrasena = '$2a$10$8XeC/j1qN8vXFjQXGKVYJ.yqP4VYmJVkJXvXQqLKvXFjQXGKVYJe.' WHERE nombre_usuario = 'asesor';

-- Crear al Cliente
INSERT INTO cliente (dni, nombre_completo, telefono, email, direccion, fecha_registro, estado, estado_cuenta)
VALUES ('11223344', 'Dani Codex', '999688777', 'danicodex@canchas.com', 'Calle Los Robles 789', '2025-01-01', 'Activo', 'Activo');
-- Obtener el ID del cliente que acabamos de crear (será el 3 si tenías 2 antes)
SET @cliente_id = LAST_INSERT_ID();

-- Crear el Usuario para que Dani Codex pueda hacer Login
INSERT INTO usuario (nombre_completo, nombre_usuario, email, contrasena, rol, telefono, estado, fecha_registro, codigo_cliente) 
VALUES ('Dani Codex', 'danicodex', 'danicodex@canchas.com', '$2y$10$szuE5pE0EquMEQn8D/1tu.dsFdkUdFm5qYGHW.j9nI.s3Tt3HJVVS', 'Cliente', '999688777', 'Activo', '2025-01-01', @cliente_id);

danicodex  Contraeña: cliente1234

-- Insertar Implementos base
INSERT INTO implemento (nombre, stock_total, precio_alquiler) VALUES ('Balón Fútbol 7', 10, 5.00), ('Chalecos Entrenamiento', 20, 2.00);

UPDATE usuario SET codigo_cliente = 3 WHERE nombre_usuario = 'danicodex';
