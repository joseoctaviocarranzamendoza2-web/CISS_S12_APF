# Guía de Integración de Base de Datos con Thymeleaf

## ✅ Cambios Implementados

### Servicios Creados:
1. **UsuarioService** - Gestión de usuarios del sistema
2. **ClienteService** - Gestión de clientes
3. **CampoFutbolService** - Gestión de campos de fútbol
4. **AlquilerService** - Gestión de reservas/alquileres
5. **FacturaService** - Gestión de facturas

### Controladores Actualizados:
- **AdminController** - Ahora envía datos reales a las vistas
- **AsesorController** - Ahora envía datos reales a las vistas

## 📝 Modificaciones Necesarias en HTML

### 1. Agregar Namespace de Thymeleaf

Cambiar:
```html
<!DOCTYPE html>
<html lang="es">
```

Por:
```html
<!DOCTYPE html>
<html lang="es" xmlns:th="http://www.thymeleaf.org">
```

### 2. Usar Atributos Thymeleaf

#### Para mostrar texto simple:
```html
<!-- Antes -->
<h2>125</h2>

<!-- Después -->
<h2 th:text="${totalAlquileres}">0</h2>
```

#### Para formatear números decimales:
```html
<!-- Antes -->
<h2>S/. 450.00</h2>

<!-- Después -->
<h2>S/. <span th:text="${#numbers.formatDecimal(ingresosHoy, 1, 2)}">0.00</span></h2>
```

#### Para iterar sobre listas:
```html
<!-- Antes -->
<tr>
    <td>001</td>
    <td>Campo Sintético</td>
    <td>S/. 80.00</td>
</tr>

<!-- Después -->
<tr th:each="campo : ${campos}">
    <td th:text="${campo.codigoCampo}">001</td>
    <td th:text="${campo.nombreCampo}">Campo</td>
    <td>S/. <span th:text="${#numbers.formatDecimal(campo.precioHora, 1, 2)}">0.00</span></td>
</tr>
```

#### Para condicionales:
```html
<!-- Mostrar badge según disponibilidad -->
<span th:if="${campo.disponibilidad}" class="badge bg-success">Disponible</span>
<span th:unless="${campo.disponibilidad}" class="badge bg-danger">Ocupado</span>
```

#### Para formatear fechas:
```html
<td th:text="${#temporals.format(cliente.fechaRegistro, 'dd/MM/yyyy')}">01/01/2025</td>
```

## 📊 Variables Disponibles por Vista

### Admin/Menu_Admin.html
- `${totalAlquileres}` - Total de alquileres
- `${totalClientes}` - Total de clientes
- `${totalCampos}` - Total de campos
- `${ingresosHoy}` - Ingresos del día actual

### Admin/Adm_Campo.html
- `${campos}` - Lista de campos (CampoFutbol)
- `${totalCampos}` - Total de campos
- `${camposDisponibles}` - Campos disponibles
- `${camposOcupados}` - Campos ocupados
- `${capacidadPromedio}` - Capacidad promedio

**Campos del objeto CampoFutbol:**
- `codigoCampo` - ID del campo
- `nombreCampo` - Nombre
- `ubicacionCampo` - Ubicación
- `tipoCesped` - Tipo de césped
- `capacidadPersonas` - Capacidad
- `precioHora` - Precio por hora
- `disponibilidad` - true/false

### Admin/Adm_Clientes.html
- `${clientes}` - Lista de clientes
- `${totalClientes}` - Total de clientes
- `${clientesActivos}` - Clientes activos
- `${clientesMes}` - Clientes del mes
- `${clientesHoy}` - Clientes de hoy

**Campos del objeto Cliente:**
- `codigoCliente` - ID
- `dni` - DNI
- `nombreCompleto` - Nombre completo
- `telefono` - Teléfono
- `email` - Email
- `direccion` - Dirección
- `fechaRegistro` - Fecha de registro
- `estado` - Estado (Activo/Inactivo)

### Admin/Adm_Alquileres.html
- `${alquileres}` - Lista de alquileres
- `${totalAlquileres}` - Total
- `${alquileresConfirmados}` - Confirmados
- `${alquileresPendientes}` - Pendientes
- `${alquileresCancelados}` - Cancelados
- `${campos}` - Lista de campos
- `${clientes}` - Lista de clientes

**Campos del objeto Alquiler:**
- `codigoAlquiler` - ID
- `cliente` - Objeto Cliente
- `campoFutbol` - Objeto Campo
- `fechaAlquiler` - Fecha
- `horaInicio` - Hora inicio
- `horaFin` - Hora fin
- `totalHoras` - Total horas
- `precioTotal` - Precio total
- `estado` - Estado (Confirmado/Pendiente/Cancelado)
- `observaciones` - Observaciones

### Admin/Adm_Facturas.html
- `${facturas}` - Lista de facturas
- `${totalFacturas}` - Total
- `${ingresosMes}` - Ingresos del mes
- `${facturasMes}` - Facturas del mes
- `${facturasHoy}` - Facturas de hoy
- `${alquileres}` - Alquileres confirmados

**Campos del objeto Factura:**
- `codigoFactura` - ID
- `numeroFactura` - Número (F-00001)
- `alquiler` - Objeto Alquiler
- `fechaEmision` - Fecha emisión
- `subtotal` - Subtotal
- `igv` - IGV (18%)
- `total` - Total
- `estadoPago` - Estado (Pagado/Pendiente/Anulado)
- `observaciones` - Observaciones

### Admin/Adm_Usuarios.html
- `${usuarios}` - Lista de usuarios
- `${totalUsuarios}` - Total
- `${totalAdministradores}` - Administradores
- `${totalAsesores}` - Asesores

**Campos del objeto Usuario:**
- `codigoUsuario` - ID
- `nombreCompleto` - Nombre completo
- `nombreUsuario` - Usuario
- `email` - Email
- `rol` - Rol (Administrador/Asesor)
- `telefono` - Teléfono
- `estado` - Estado (Activo/Inactivo)
- `fechaRegistro` - Fecha de registro

### Admin/Reportes_Por_Dia.html
- `${ingresosTotal}` - Ingresos totales del período
- `${totalAlquileres}` - Total de alquileres
- `${promedioIngresos}` - Promedio de ingresos
- `${totalClientes}` - Total de clientes
- `${alquileresPorDia}` - Lista de alquileres
- `${campos}` - Lista de campos

### Asesor/Menu_Asesor.html
- `${reservasHoy}` - Reservas de hoy
- `${camposDisponibles}` - Campos disponibles
- `${proximasReservas}` - Lista de próximas reservas

### Asesor/AV_Campo.html
- `${campos}` - Todos los campos
- `${camposDisponibles}` - Solo disponibles

### Asesor/AV_Clientes.html
- `${clientes}` - Lista de clientes

### Asesor/AV_Alquileres.html
- `${alquileres}` - Lista de alquileres
- `${campos}` - Campos disponibles
- `${clientes}` - Lista de clientes

### Asesor/AV_Facturas.html
- `${facturas}` - Lista de facturas
- `${alquileres}` - Alquileres confirmados

## 🔧 Ejemplo Completo de Tabla

```html
<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Cliente</th>
            <th>Campo</th>
            <th>Fecha</th>
            <th>Total</th>
            <th>Estado</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="alquiler : ${alquileres}">
            <td th:text="${alquiler.codigoAlquiler}">001</td>
            <td th:text="${alquiler.cliente.nombreCompleto}">Cliente</td>
            <td th:text="${alquiler.campoFutbol.nombreCampo}">Campo</td>
            <td th:text="${#temporals.format(alquiler.fechaAlquiler, 'dd/MM/yyyy')}">01/01/2025</td>
            <td>S/. <span th:text="${#numbers.formatDecimal(alquiler.precioTotal, 1, 2)}">0.00</span></td>
            <td>
                <span th:if="${alquiler.estado == 'Confirmado'}" class="badge bg-success">Confirmado</span>
                <span th:if="${alquiler.estado == 'Pendiente'}" class="badge bg-warning">Pendiente</span>
                <span th:if="${alquiler.estado == 'Cancelado'}" class="badge bg-danger">Cancelado</span>
            </td>
        </tr>
    </tbody>
</table>
```

## ✨ Funciones Útiles de Thymeleaf

### Números:
- `${#numbers.formatDecimal(num, 1, 2)}` - Formato con 2 decimales
- `${#numbers.formatInteger(num, 3)}` - Formato con 3 dígitos mínimo

### Fechas:
- `${#temporals.format(fecha, 'dd/MM/yyyy')}` - Formato fecha
- `${#temporals.format(hora, 'HH:mm')}` - Formato hora
- `${#temporals.format(fecha, 'dd/MM/yyyy HH:mm')}` - Fecha y hora

### Strings:
- `${#strings.toUpperCase(texto)}` - A mayúsculas
- `${#strings.toLowerCase(texto)}` - A minúsculas
- `${#strings.substring(texto, 0, 10)}` - Substring

### Condicionales:
- `th:if="${condicion}"` - Mostrar si es verdadero
- `th:unless="${condicion}"` - Mostrar si es falso
- `th:switch="${variable}"` + `th:case="valor"` - Switch case

## 🎯 Próximos Pasos

1. Actualizar cada HTML agregando `xmlns:th="http://www.thymeleaf.org"`
2. Reemplazar datos ficticios con `th:text="${variable}"`
3. Usar `th:each` para iterar sobre listas
4. Probar cada vista navegando en http://localhost:8080

## 📌 Nota Importante

Los datos ahora vienen directamente de la base de datos MySQL. Asegúrate de tener datos en las tablas para poder visualizarlos en las vistas.
