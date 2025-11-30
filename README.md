# 🛒 ShoppingCartWeb

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-blue?style=for-the-badge&logo=eclipse)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=for-the-badge&logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

**Aplicación web de carrito de compras empresarial con seguridad avanzada**

[Características](#-características) • [Instalación](#-instalación-y-configuración) • [Uso](#-uso) • [Documentación](#-documentación)

</div>

---

## 📋 Descripción

**ShoppingCartWeb** es una aplicación web de carrito de compras desarrollada con **Jakarta EE** y **PostgreSQL**, implementando las mejores prácticas de seguridad, arquitectura empresarial y patrones de diseño modernos.

### ✨ Características

- 🔐 **Autenticación Segura** - Hash de contraseñas con BCrypt (fuerza configurable)
- 🛡️ **Validación Completa** - Validación robusta de entrada de usuario
- 📊 **Sistema de Logging** - Logging profesional con SLF4J/Logback
- 🔒 **Protección Avanzada** - Protección contra SQL Injection y XSS
- 🎨 **Interfaz Responsive** - UI moderna con Bootstrap 4.5
- 🔄 **Gestión de Sesiones** - Persistencia en base de datos
- ⚡ **AJAX** - Experiencia de usuario fluida y sin recargas
- 📦 **Arquitectura MVC** - Separación clara de responsabilidades

---

## 🏗️ Arquitectura

### Patrón MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────────────┐
│                    VISTA (JSP)                       │
│  index.jsp │ carrito.jsp │ login.jsp │ registro.jsp │
└────────────────────┬────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────┐
│              CONTROLADOR (Servlet)                   │
│                 Controlador.java                     │
│         • Manejo de peticiones HTTP                  │
│         • Validación de entrada                      │
│         • Gestión de sesiones                        │
└────────────────────┬────────────────────────────────┘
                     │
        ┌────────────┼────────────┐
        ▼            ▼            ▼
┌──────────┐  ┌──────────┐  ┌──────────┐
│ SERVICIO │  │   DAO    │  │  MODELO  │
│ Carrito  │  │ Cliente  │  │ Cliente  │
│ Service  │  │ Producto │  │ Producto │
│          │  │ Carrito  │  │ Carrito  │
└──────────┘  └────┬─────┘  └──────────┘
                   │
                   ▼
            ┌──────────────┐
            │  PostgreSQL  │
            │   Database   │
            └──────────────┘
```

---

## 🚀 Tecnologías

### Backend
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| Java | 17 | Lenguaje principal |
| Jakarta EE | 10 | Servlets, JSP, JSTL |
| PostgreSQL | 42.7.3 | Base de datos |
| Maven | 3.6+ | Gestión de dependencias |

### Seguridad
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| Spring Security Crypto | 6.2.1 | Hash BCrypt |
| Apache Commons Lang3 | 3.14.0 | Validación de entrada |

### Logging
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| SLF4J | 2.0.9 | API de logging |
| Logback | 1.4.14 | Implementación |

### Frontend
| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| Bootstrap | 4.5.2 | Framework UI |
| JavaScript | ES6 | Interactividad |
| Fetch API | - | Comunicación asíncrona |

---

## 📦 Estructura del Proyecto

```
ShoppingCartWeb/
├── src/
│   ├── main/
│   │   ├── java/com/proyectox/shoppingcartweb/
│   │   │   ├── controlador/
│   │   │   │   └── Controlador.java          # Servlet principal
│   │   │   ├── dao/
│   │   │   │   ├── Conexion.java             # Gestión de conexiones
│   │   │   │   ├── ClienteDAO.java           # Acceso a datos de clientes
│   │   │   │   ├── ProductoDAO.java          # Acceso a datos de productos
│   │   │   │   └── CarritoDAO.java           # Acceso a datos del carrito
│   │   │   ├── modelo/
│   │   │   │   ├── Cliente.java              # Entidad Cliente
│   │   │   │   ├── Producto.java             # Entidad Producto
│   │   │   │   ├── Carrito.java              # Entidad Carrito
│   │   │   │   ├── ItemCarrito.java          # Item del carrito
│   │   │   │   └── DetalleCarrito.java       # Detalle del carrito
│   │   │   ├── servicio/
│   │   │   │   └── CarritoService.java       # Lógica de negocio
│   │   │   └── util/
│   │   │       ├── ConfigLoader.java         # Carga de configuración
│   │   │       ├── PasswordUtil.java         # Utilidades de contraseñas
│   │   │       └── ValidationUtil.java       # Validación de entrada
│   │   ├── resources/
│   │   │   ├── application.properties        # Configuración de la app
│   │   │   └── logback.xml                   # Configuración de logging
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml                   # Descriptor de despliegue
│   │       │   ├── login.jsp                 # Página de login
│   │       │   ├── registro.jsp              # Página de registro
│   │       │   └── error.jsp                 # Página de error
│   │       ├── index.jsp                     # Página principal
│   │       └── carrito.jsp                   # Página del carrito
│   └── test/
├── logs/                                      # Directorio de logs
├── pom.xml                                    # Configuración Maven
├── BD_Carrito.sql                            # Script de BD
├── MIGRACION_PASSWORDS.sql                   # Script de migración
└── README.md                                 # Este archivo
```

---

## 🔧 Instalación y Configuración

### Prerrequisitos

Asegúrate de tener instalado:

- ☕ **Java 17** o superior ([Descargar](https://adoptium.net/))
- 🐘 **PostgreSQL 12** o superior ([Descargar](https://www.postgresql.org/download/))
- 📦 **Maven 3.6** o superior ([Descargar](https://maven.apache.org/download.cgi))
- 🌐 **Servidor de aplicaciones** (Tomcat 10+, GlassFish, WildFly, etc.)

### Paso 1: Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/ShoppingCartWeb.git
cd ShoppingCartWeb
```

### Paso 2: Configurar Base de Datos

1. **Crear la base de datos:**

```bash
psql -U postgres
```

```sql
CREATE DATABASE almacen;
\q
```

2. **Ejecutar el script de creación de tablas:**

```bash
psql -U postgres -d almacen -f BD_Carrito.sql
```

3. **Crear la tabla de clientes** (si no existe):

```sql
CREATE TABLE cliente (
    idcliente SERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
```

### Paso 3: Configurar la Aplicación

Edita el archivo `src/main/resources/application.properties`:

```properties
# Configuración de Base de Datos
db.url=jdbc:postgresql://localhost:5432/almacen
db.username=postgres
db.password=tu_password_aqui

# Configuración de Logging
log.level=INFO

# Configuración de Seguridad
security.bcrypt.strength=12
```

### Paso 4: Compilar el Proyecto

```bash
mvn clean install
```

### Paso 5: Crear Directorio de Logs

```bash
mkdir -p logs
chmod 755 logs
```

### Paso 6: Desplegar

#### Opción A: Generar WAR

```bash
mvn clean package
```

El archivo WAR estará en: `target/ShoppingCartWeb-1.0-SNAPSHOT.war`

Despliega este archivo en tu servidor de aplicaciones favorito.

#### Opción B: Usar Maven Tomcat Plugin

Agrega al `pom.xml`:

```xml
<plugin>
    <groupId>org.apache.tomcat.maven</groupId>
    <artifactId>tomcat7-maven-plugin</artifactId>
    <version>2.2</version>
    <configuration>
        <port>8080</port>
        <path>/</path>
    </configuration>
</plugin>
```

Luego ejecuta:

```bash
mvn tomcat7:run
```

---

## 🗄️ Esquema de Base de Datos

### Diagrama ER

```
┌─────────────┐         ┌─────────────┐         ┌─────────────┐
│   cliente   │         │   carrito   │         │  producto   │
├─────────────┤         ├─────────────┤         ├─────────────┤
│ idcliente PK│────┐    │ idcarrito PK│    ┌────│ idproducto PK│
│ nombres     │    │    │ idcliente FK│────┘    │ nombre      │
│ apellidos   │    │    │ fechacreacion│        │ descripcion │
│ email       │    │    └─────────────┘        │ precio      │
│ password    │    │           │                │ imagen      │
└─────────────┘    │           │                └─────────────┘
                   │           │                       │
                   │    ┌──────┴──────┐               │
                   │    │             │               │
                   │    ▼             ▼               │
                   │  ┌─────────────────────┐         │
                   └──│ detalle_carrito     │─────────┘
                      ├─────────────────────┤
                      │ iddetalle PK        │
                      │ idcarrito FK        │
                      │ idproducto FK       │
                      │ cantidad            │
                      └─────────────────────┘
```

### Tablas

#### `cliente`
```sql
CREATE TABLE cliente (
    idcliente SERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL  -- Hash BCrypt
);
```

#### `producto`
```sql
CREATE TABLE producto (
    idproducto SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    imagen VARCHAR(255)
);
```

#### `carrito`
```sql
CREATE TABLE carrito (
    idcarrito SERIAL PRIMARY KEY,
    idcliente INTEGER UNIQUE NOT NULL,
    fechacreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idcliente) REFERENCES cliente(idcliente) ON DELETE CASCADE
);
```

#### `detalle_carrito`
```sql
CREATE TABLE detalle_carrito (
    iddetalle SERIAL PRIMARY KEY,
    idcarrito INTEGER NOT NULL,
    idproducto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL DEFAULT 1,
    CONSTRAINT chk_cantidad CHECK (cantidad > 0),
    FOREIGN KEY (idcarrito) REFERENCES carrito(idcarrito) ON DELETE CASCADE,
    FOREIGN KEY (idproducto) REFERENCES producto(idproducto) ON DELETE CASCADE,
    UNIQUE (idcarrito, idproducto)
);
```

---

## 🔐 Seguridad

### Características de Seguridad Implementadas

#### 1. 🔒 Hash de Contraseñas

- ✅ **BCrypt** con fuerza configurable (default: 12)
- ✅ **Salt automático** en cada hash
- ✅ **Verificación segura** sin exponer contraseñas
- ✅ **Resistente a ataques de fuerza bruta**

**Ejemplo de uso:**

```java
// Hash de contraseña
String hashedPassword = PasswordUtil.hashPassword("Password123");

// Verificación
boolean isValid = PasswordUtil.verifyPassword("Password123", hashedPassword);
```

#### 2. ✅ Validación de Entrada

- ✅ **Email**: Formato RFC válido
- ✅ **Contraseña**: Requisitos de complejidad
- ✅ **Nombres/Apellidos**: Solo caracteres alfabéticos
- ✅ **Sanitización XSS**: Escape de caracteres peligrosos

**Requisitos de Contraseña:**

```
✅ Mínimo 8 caracteres
✅ Al menos una letra mayúscula (A-Z)
✅ Al menos una letra minúscula (a-z)
✅ Al menos un número (0-9)

Ejemplos válidos:
  • Password123
  • MySecure1
  • Test1234

Ejemplos inválidos:
  ✗ password (sin mayúsculas ni números)
  ✗ PASSWORD123 (sin minúsculas)
  ✗ Password (sin números)
  ✗ Pass1 (muy corta)
```

#### 3. 🛡️ Protección SQL Injection

- ✅ **PreparedStatements** en todas las consultas
- ✅ **Validación** antes de ejecutar consultas
- ✅ **Logging** de intentos sospechosos

#### 4. 📊 Logging y Auditoría

- ✅ **Eventos de seguridad** registrados
- ✅ **Intentos de login** fallidos
- ✅ **Errores de validación**
- ✅ **Rotación automática** de logs

---

## 📊 Uso

### 1. Registro de Usuario

1. Navega a: `http://localhost:8080/Controlador?acción=verRegistro`
2. Completa el formulario:
   - **Nombres**: Solo letras (ej: Juan)
   - **Apellidos**: Solo letras (ej: Pérez)
   - **Email**: Formato válido (ej: juan@email.com)
   - **Contraseña**: Cumplir requisitos (ej: Password123)
3. Click en **"Registrarse"**

### 2. Inicio de Sesión

1. Navega a: `http://localhost:8080/Controlador?acción=verLogin`
2. Ingresa tu email y contraseña
3. Click en **"Iniciar Sesión"**

### 3. Navegación de Productos

1. La página principal muestra todos los productos disponibles
2. Click en **"Agregar al carrito"** para añadir productos
3. El contador del carrito se actualiza automáticamente (AJAX)

### 4. Gestión del Carrito

1. Click en **"Ver Carrito"** para ver los productos
2. Actualiza cantidades según necesidad
3. Elimina productos no deseados
4. El total se actualiza en tiempo real

---

## 📝 Logs

### Ubicación de Logs

```
logs/
├── shoppingcart.log      # Logs generales (retención: 30 días)
├── error.log             # Solo errores (retención: 90 días)
└── security.log          # Eventos de seguridad (retención: 90 días)
```

### Ejemplos de Logs

```log
# Login exitoso
2025-11-24 17:51:10 [http-nio-8080-exec-1] INFO  ClienteDAO - Login exitoso para el usuario: juan@email.com

# Intento de login fallido
2025-11-24 17:51:15 [http-nio-8080-exec-2] WARN  ClienteDAO - Intento de login fallido: usuario no encontrado: hacker@evil.com

# Error de validación
2025-11-24 17:51:20 [http-nio-8080-exec-3] WARN  ValidationUtil - Validación de email fallida: formato inválido para 'invalid-email'

# Error de base de datos
2025-11-24 17:51:25 [http-nio-8080-exec-4] ERROR Conexion - Error al establecer conexión con la base de datos
```

---

## 🧪 Testing

### Pruebas de Validación

#### Test de Email

```bash
# Casos de prueba
❌ "usuario"              → Error: formato inválido
❌ "usuario@"             → Error: formato inválido
❌ "@dominio.com"         → Error: formato inválido
✅ "usuario@dominio.com"  → Válido
```

#### Test de Contraseña

```bash
# Casos de prueba
❌ "123"         → Error: muy corta
❌ "password"    → Error: sin mayúsculas ni números
❌ "PASSWORD"    → Error: sin minúsculas ni números
❌ "Password"    → Error: sin números
✅ "Password123" → Válida
```

#### Test de SQL Injection

```bash
# Intento de ataque
Email: ' OR '1'='1
Resultado: ✅ Bloqueado por validación de email
```

---

## 🐛 Troubleshooting

### ❌ Error de conexión a base de datos

**Síntomas:**
- Error al iniciar la aplicación
- Mensajes de "Connection refused"

**Solución:**
1. Verificar que PostgreSQL esté corriendo:
   ```bash
   sudo systemctl status postgresql
   ```
2. Revisar credenciales en `application.properties`
3. Verificar que la base de datos `almacen` exista:
   ```bash
   psql -U postgres -l | grep almacen
   ```
4. Revisar logs en `logs/error.log`

### ❌ No puedo iniciar sesión

**Síntomas:**
- Credenciales correctas pero no puedo entrar
- Error "Usuario o contraseña incorrectos"

**Solución:**
1. Verificar que el email esté registrado en la BD
2. Si migraste de versión anterior, las contraseñas antiguas no funcionarán
3. Registrar un nuevo usuario o ejecutar `MIGRACION_PASSWORDS.sql`
4. Revisar logs en `logs/security.log`

### ❌ Errores de validación

**Síntomas:**
- Formularios no se envían
- Mensajes de validación

**Solución:**
1. Verificar que la contraseña cumpla requisitos (8+ chars, mayúscula, minúscula, número)
2. Verificar que el email tenga formato válido
3. Verificar que nombres/apellidos solo contengan letras
4. Revisar logs en `logs/shoppingcart.log`

---

## 📚 Documentación Adicional

| Documento | Descripción |
|-----------|-------------|
| [ERRORES_CORREGIDOS.md](ERRORES_CORREGIDOS.md) | Errores encontrados y corregidos |
| [MEJORAS_SEGURIDAD.md](MEJORAS_SEGURIDAD.md) | Detalles de mejoras de seguridad |
| [BD_Carrito.sql](BD_Carrito.sql) | Script de creación de base de datos |
| [MIGRACION_PASSWORDS.sql](MIGRACION_PASSWORDS.sql) | Script de migración de contraseñas |

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Sigue estos pasos:

1. **Fork** el proyecto
2. Crea una rama para tu feature:
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. Commit tus cambios:
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. Push a la rama:
   ```bash
   git push origin feature/AmazingFeature
   ```
5. Abre un **Pull Request**

### Estándares de Código

- ✅ Seguir convenciones de Java (CamelCase, etc.)
- ✅ Documentar métodos públicos con JavaDoc
- ✅ Agregar logging apropiado (INFO, WARN, ERROR)
- ✅ Validar toda entrada de usuario
- ✅ Escribir código seguro (PreparedStatements, sanitización)
- ✅ Mantener la arquitectura MVC

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

```
MIT License

Copyright (c) 2025 ShoppingCartWeb

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👥 Autores

- **Luis Flores** - *Desarrollador Principal* - [@luisflorez20](https://github.com/luisflorez20)
- **Antigravity AI** - *Mejoras de seguridad y refactorización*

---

## 🙏 Agradecimientos

- [Spring Security](https://spring.io/projects/spring-security) por BCrypt
- [OWASP](https://owasp.org/) por guías de seguridad
- [Jakarta EE Community](https://jakarta.ee/) por la plataforma
- [PostgreSQL Team](https://www.postgresql.org/) por la base de datos
- [Bootstrap](https://getbootstrap.com/) por el framework UI

---

## 📞 Soporte

¿Necesitas ayuda? Aquí tienes algunas opciones:

- 🐛 **Reportar bugs**: [Crear un issue](https://github.com/luisflorez20/ShoppingCartWeb/issues)
- 💡 **Solicitar features**: [Crear un issue](https://github.com/luisflorez20/ShoppingCartWeb/issues)
- 📖 **Documentación**: Revisar archivos en `/docs`
- 📊 **Logs**: Consultar archivos en `/logs`

---

## 📈 Roadmap

### Versión 1.1 (Próximamente)
- [ ] Implementar sistema de pagos
- [ ] Agregar historial de pedidos
- [ ] Implementar búsqueda de productos
- [ ] Agregar filtros y categorías

### Versión 1.2 (Futuro)
- [ ] API REST para integración móvil
- [ ] Sistema de reseñas y calificaciones
- [ ] Panel de administración
- [ ] Notificaciones por email

---

## 📊 Estado del Proyecto

![Status](https://img.shields.io/badge/Status-Production%20Ready-success?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-1.0--SNAPSHOT-blue?style=for-the-badge)
![Build](https://img.shields.io/badge/Build-Passing-success?style=for-the-badge)

**Versión:** 1.0-SNAPSHOT (Security Enhanced)  
**Última actualización:** 30 de noviembre de 2025  
**Estado:** ✅ Production Ready

---

<div align="center">

**⭐ Si este proyecto te fue útil, considera darle una estrella ⭐**

Hecho con ❤️ por [Luis Flores](https://github.com/luisflorez20)

</div>
