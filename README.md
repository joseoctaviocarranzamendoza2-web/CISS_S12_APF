# Sistema de Gestión de Campos de Fútbol ⚽

Sistema web completo para la gestión de alquiler de campos de fútbol desarrollado con **Spring Boot 3.5.8** y **MySQL 8**.

## 🚀 Tecnologías Utilizadas

- **Backend:** Spring Boot 3.5.8 (Java 21)
- **Frontend:** Thymeleaf + Bootstrap 5.3.8
- **Seguridad:** Spring Security con BCrypt
- **Base de Datos:** MySQL 8
- **ORM:** Hibernate (JPA)
- **Build:** Maven

## 📋 Características

### Módulos del Sistema

#### Para Administradores:
- ✅ Gestión completa de campos de fútbol
- ✅ Administración de clientes
- ✅ Control de alquileres/reservas
- ✅ Gestión de facturas
- ✅ Administración de usuarios del sistema
- ✅ Reportes diarios de ingresos y ocupación

#### Para Asesores:
- ✅ Visualización de campos disponibles
- ✅ Registro de nuevos clientes
- ✅ Creación de reservas
- ✅ Generación de facturas

### Seguridad
- 🔐 Autenticación con Spring Security
- 🔐 Contraseñas encriptadas con BCrypt
- 🔐 Autorización basada en roles (ADMINISTRADOR/ASESOR)
- 🔐 Protección de rutas según permisos

## 📦 Instalación

### Requisitos Previos
- Java 21 o superior
- MySQL 8.0 o superior
- Maven 3.8+

### 1. Configurar Base de Datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE campos_futbol CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

O ejecutar el script completo ubicado en:
```
src/main/resources/schema.sql
```

### 2. Configurar Credenciales

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/campos_futbol?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
```

### 3. Compilar el Proyecto

```bash
mvnw clean install -DskipTests
```

### 4. Ejecutar la Aplicación

```bash
mvnw spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

## 👤 Usuarios de Prueba

Al iniciar la aplicación por primera vez, se crearán automáticamente 2 usuarios:

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| `admin` | `admin123` | Administrador |
| `asesor` | `asesor123` | Asesor |

## 📁 Estructura del Proyecto

```
src/main/java/com/example/Analisis/
├── Config/              # Configuración e inicialización
├── Controllers/         # Controladores MVC
├── Database/           # Repositorios JPA
├── Models/             # Entidades JPA
└── Secure/             # Configuración de seguridad

src/main/resources/
├── application.properties
├── schema.sql
└── templates/
    ├── index.html
    ├── login.html
    ├── Admin/          # Vistas de administrador
    ├── Asesor/         # Vistas de asesor
    └── error/          # Páginas de error
```

## 🗄️ Modelos de Base de Datos

### Usuario
- Gestión de usuarios del sistema (Admin/Asesor)
- Contraseñas encriptadas con BCrypt
- Estados: Activo/Inactivo

### Cliente
- Información de clientes que alquilan campos
- DNI único, teléfono, email, dirección

### CampoFutbol
- Campos disponibles para alquiler
- Tipo de césped, capacidad, precio/hora
- Estado de disponibilidad

### Alquiler
- Reservas de campos
- Relación con Cliente y Campo
- Estados: Confirmado/Pendiente/Cancelado
- Cálculo de horas y precio total

### Factura
- Comprobantes de pago
- Cálculo automático de IGV (18%)
- Estados: Pagado/Pendiente/Anulado

## 🛣️ Rutas Principales

### Públicas
- `/` - Página de inicio
- `/login` - Inicio de sesión

### Administrador (requiere rol ADMINISTRADOR)
- `/admin/menu` - Dashboard principal
- `/admin/campo` - Gestión de campos
- `/admin/clientes` - Gestión de clientes
- `/admin/alquileres` - Gestión de alquileres
- `/admin/facturas` - Gestión de facturas
- `/admin/usuarios` - Gestión de usuarios
- `/admin/reportes` - Reportes diarios

### Asesor (requiere rol ASESOR)
- `/asesor/menu` - Dashboard del asesor
- `/asesor/campo` - Ver campos disponibles
- `/asesor/clientes` - Registrar clientes
- `/asesor/alquileres` - Crear reservas
- `/asesor/facturas` - Generar facturas

## 🔧 Configuración de Seguridad

El sistema utiliza Spring Security con las siguientes configuraciones:

- **Autenticación:** Basada en base de datos con `CustomUserDetailsService`
- **Encriptación:** BCrypt para contraseñas
- **Autorización:** Basada en roles (ROLE_ADMINISTRADOR, ROLE_ASESOR)
- **Sesiones:** Manejo automático por Spring Security
- **CSRF:** Habilitado por defecto

## 📊 Base de Datos

### Diagrama de Relaciones

```
usuario (autenticación)

cliente ──┐
          ├──> alquiler ──> factura
campo_futbol ──┘
```

### Índices Optimizados
- `usuario.nombre_usuario` (UNIQUE)
- `cliente.dni` (UNIQUE)
- `alquiler.fecha_alquiler`
- `factura.numero_factura` (UNIQUE)

## 🎨 Frontend

- **Framework CSS:** Bootstrap 5.3.8
- **Iconos:** Bootstrap Icons 1.11.1
- **Diseño:** 100% responsive
- **Temas:** Gradientes personalizados y paleta de colores profesional

## 📝 Notas Importantes

1. **Primera Ejecución:** Los usuarios se crean automáticamente en el primer arranque
2. **Base de Datos:** Hibernate creará las tablas automáticamente (`ddl-auto=update`)
3. **Contraseñas:** Todas las contraseñas se almacenan encriptadas con BCrypt
4. **Roles:** Los roles deben ser exactamente "Administrador" o "Asesor" (case-sensitive)

## 🐛 Solución de Problemas

### Error de Conexión a MySQL
```
Verificar que MySQL está ejecutándose
Revisar usuario/contraseña en application.properties
Verificar que existe la base de datos 'campos_futbol'
```

### Error de Autenticación
```
Verificar que los usuarios fueron creados en la base de datos
Comprobar que el rol del usuario es correcto
Revisar logs en consola para detalles
```

### Puerto 8080 en Uso
```properties
# Cambiar puerto en application.properties
server.port=8081
```

## 📄 Licencia

Proyecto desarrollado para Sr. Pedro Pablo Camilo - Sistema de Alquiler de Campos de Fútbol

---

**Desarrollado con ❤️ usando Spring Boot**

Actualización de prueba para git pull
