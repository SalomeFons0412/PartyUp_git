# PartyUp_git
Es una plataforma social diseñada para gamers locales. Permite que los jugadores se conecten con personas cercanas que compartan sus mismos intereses. Nuestro objetivo es fomentar comunidades gamer reales, facilitar la creación de torneos exprés y ofrecer un espacio donde los usuarios puedan compartir logros, clips y experiencias.

## Objetivo general

desarrollar una plataforma digital confiable y localizada que conecte a gamers cercanos, facilitando la creación de comunidades sólidas y la organización de torneos locales.

## Objetivos específicos

- Implementar un sistema de registro y autenticación seguro.

- Crear perfiles gamer personalizados con estadísticas.

- Diseñar un algoritmo de emparejamiento social.

- Integrar un mapa interactivo de jugadores locales.

- Desarrollar un feed social para compartir contenido.

- Añadir notificaciones en tiempo real y chat interno.


## User Stories Completadas

### **Registro y autenticación segura**
- Contraseñas cifradas con BCrypt.
- Validación de correo único.
- Manejo de credenciales.

### **Edición de perfil**
- Actualización de datos personales.
- Preferencias de juegos, plataformas y horarios.

### **Algoritmo de compatibilidad**
- Comparación de intereses.
- Cálculo de coincidencias.

### **Chat directo**
- Mensajería entre usuarios.
- Almacenamiento de mensajes.

### **Visualización en mapa**
- Ubicación del usuario.
- Cálculo por fórmula Haversine.
- Mostrar usuarios cercanos.

###  **Filtros de búsqueda**
- Ciudad, plataformas, juegos y disponibilidad.

###  **Privacidad de ubicación**
- Activar/desactivar visibilidad.
- Si está desactivado → no aparece en el mapa.

### **Publicaciones comunitarias**
- Texto, imagen o video.
- Fecha, autor, tipo de contenido.
- Orden cronológico inverso.

###  **Notificaciones push**
- Estructura de envío de eventos en el sistema.

### **Sistema de grupos**
- Crear y unirse a grupos.
- Roles de administrador/miembro.

### **UML del sistema**
- Diagramas de:
  - Clases
  - Casos de uso
  - Secuencia

---

#  User Stories Pendientes (Sprint 3)
- Optimización y despliegue.
- Reacciones y comentarios.
- Moderación de contenido.
- Logros e insignias.
- Sistema de puntos y ranking.
- Torneos exprés.
- Clasificación y resultados.
- Wireframes principales.
- Pruebas automáticas.

---

# Arquitectura
- /controller → API REST
- /service → Lógica de negocio
- /repository → Integración con MongoDB
- /model/entity → Modelos de datos
