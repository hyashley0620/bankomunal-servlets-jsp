# Bankomunal — Módulo Servlets & JSP
### Módulos de software codificados y probados

Este módulo complementa el proyecto principal **Bankomunal** (Spring Boot + JS)
codificando, con la tecnología vista en el componente **"Construcción de
aplicaciones con JAVA"**, dos funcionalidades del sistema usando **Servlets
puros y JSP** (sin frameworks), formularios HTML, métodos `GET`/`POST` y
acceso a datos con JDBC contra la misma base de datos (`bankomunal.sql`).

## Funcionalidades incluidas

1. **Autenticación de usuarios**
   - `GET /registro` → formulario de registro (`registro.jsp`)
   - `POST /registro` → `RegistroServlet` valida y crea el usuario (contraseña
     cifrada con BCrypt, compatible con el hash que usa Spring Security en el
     backend principal)
   - `GET /login` → formulario de inicio de sesión (`login.jsp`)
   - `POST /login` → `LoginServlet` valida credenciales y abre sesión (`HttpSession`)
   - `GET /logout` → `LogoutServlet` invalida la sesión

2. **Gestión de metas de ahorro** (CRUD completo, protegido por `AuthFilter`)
   - `GET /metas` → lista las metas del usuario autenticado
   - `GET /metas?action=nueva` → formulario de creación
   - `GET /metas?action=editar&id=N` → formulario de edición
   - `GET /metas?action=eliminar&id=N` → elimina una meta
   - `POST /metas` (`action=crear` / `action=actualizar`) → `MetasAhorroServlet`
     procesa el formulario y persiste los cambios vía `SavingsGoalDAO`

## Arquitectura del módulo

```
src/main/java/com/bankomunal/
 ├─ servlets/   RegistroServlet, LoginServlet, LogoutServlet, MetasAhorroServlet
 ├─ dao/        UserDAO, SavingsGoalDAO   (JDBC puro, PreparedStatement)
 ├─ model/      User, SavingsGoal
 ├─ filter/     AuthFilter               (protege rutas privadas)
 └─ util/       ConexionBD               (fábrica de conexiones JDBC)

src/main/webapp/
 ├─ index.jsp
 ├─ WEB-INF/web.xml                      (mapeo de servlets, filtro, sesión, error-pages)
 └─ WEB-INF/views/*.jsp                  (login, registro, metas, metaForm, error)
```

Se usa el patrón **MVC con Servlet-Dispatcher-JSP**: el servlet controla la
lógica y el flujo (controlador), el DAO habla con la base de datos (modelo),
y el JSP solo pinta HTML con JSTL/EL, sin lógica de negocio (vista).

## Requisitos para ejecutar

- JDK 11+
- Apache Tomcat 9.x (Servlet API 4.0)
- MySQL 8.x con el esquema de `bankomunal.sql`
- Maven 3.8+

## Pasos

1. Importa la base de datos:
   ```bash
   mysql -u root -p < bankomunal.sql
   ```
2. Edita `src/main/resources/db.properties` con tu usuario/clave de MySQL.
3. Empaqueta el WAR:
   ```bash
   mvn clean package
   ```
4. Copia `target/bankomunal-servlets-jsp.war` a la carpeta `webapps` de Tomcat
   (o despliega desde tu IDE — Eclipse/IntelliJ con plugin de Tomcat).
5. Abre `http://localhost:8080/bankomunal-servlets-jsp/` — te redirige al login.
6. Regístrate, inicia sesión y gestiona tus metas de ahorro.


## Relación con los artefactos previos del proyecto

- **Diagrama de clases**: `User` y `SavingsGoal` corresponden a las entidades
  `User` y `SavingsGoal` ya definidas en el backend Spring Boot
  (`com.bankomunal.entity`), aquí modeladas como POJOs simples para JDBC.
- **Historias de usuario**: cubre las historias de "Registro e inicio de
  sesión de usuario" y "Como usuario quiero crear y gestionar mis metas de
  ahorro para hacer seguimiento a mi progreso".
- **Modelo de base de datos**: reutiliza directamente las tablas `users` y
  `savings_goals` definidas en `bankomunal.sql`, sin modificar el esquema.

# bankomunal-servlets-jsp
