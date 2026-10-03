# Battleship - UPM

Versión homenaje del clásico juego de mesa "Hundir la flota" (Battleship), desarrollado para la asignatura de Fundamentos de Ingeniería del Software (FIS).

## 📖 Descripción del Proyecto

El proyecto consiste en una versión digital de Battleship donde los jugadores se enfrentan a una "máquina" en un tablero de 10x10 casillas. El juego incorpora mecánicas novedosas, como habilidades especiales para cada tipo de barco, y cuenta con un sistema de registro seguro e integrado con los servicios de la universidad.

## ✨ Características Principales

*   **Tablero Clásico**: Cuadrícula de 10x10 donde las filas y columnas se denotan con números del 0 al 9.
*   **Flota con Habilidades Especiales**: El juego consta de 4 barcos que no se pueden mover de su posición inicial.
    *   *Portaviones* (4 casillas): Habilidad de contraatacar en cada impacto.
    *   *Acorazado* (3 casillas): Puede lanzar un ataque de artillería en posiciones adyacentes (hasta 2 veces).
    *   *Submarino* (3 casillas): Puede hundirse para reparar los impactos recibidos, incluso resucitando al barco entero (1 vez por partida).
    *   *Patrullero* (2 casillas): Puede revelar toda una fila al ser impactado (1 vez por partida).
*   **Modos de Dificultad**: Los jugadores compiten contra máquinas generadas en ejecución con niveles FÁCIL, NORMAL o DIFÍCIL.
*   **Sistema de Puntuaciones**: Se otorgan 5 puntos por posición de barco hundido, 2 puntos por impacto, se resta 1 punto por impacto en agua, y se suman o restan 20 puntos por ganar o perder la partida. Además, el sistema almacena y permite visualizar las 10 mejores puntuaciones de los jugadores.
*   **Autenticación UPM**: Registro limitado a usuarios de la UPM mediante validación con servicio LDAP y almacenamiento de contraseñas cifradas. Las contraseñas requieren mayúsculas, minúsculas, números y símbolos, con un tamaño de 6 a 12 caracteres.
*   **Accesibilidad**: El sistema está optimizado para diferentes dispositivos e incluye modos de visualización adaptados para personas con ceguera parcial o total, y 4 tipos de daltonismo.

## 🛠️ Tecnologías y Herramientas

*   **Lenguaje**: Java (mínimo JDK-17) sobre el entorno de desarrollo Eclipse (versión 24.06 o superior).
*   **Gestor de Dependencias**: Maven (usando la plantilla `maven-archetype-quickstart`).
*   **Librerías Externas**: `Battleship-1.X.jar` (lógica de partidas) y `externals-X.jar` (conexión LDAP).
*   **Testing**: Pruebas unitarias (caja negra y caja blanca) implementadas con JUnit.
*   **Gestión y Trazabilidad**: GitLab para el control de versiones y Redmine para la especificación de requisitos y trazabilidad.

## 📂 Estructura del Repositorio

El proyecto en GitLab sigue la estructura exigida para la entrega de la práctica:

*   `/modelado`: Contiene los diagramas UML (Casos de uso, Clases, Componentes y Despliegue) en formato PNG/JPG, junto con el documento PDF de recopilación de la fase de análisis y diseño[cite: 6, 10, 11].
*   `/construcción`: Contiene el código fuente de la aplicación en Java y la carpeta de test con las pruebas unitarias[cite: 11].
*   `/pruebas`: Contiene los documentos PDF de verificación y validación, incluyendo la solución teórica de casos de prueba y las pruebas de aceptación y trazabilidad[cite: 10, 11].

## 📋 Normativa

Este proyecto se ha desarrollado siguiendo las normativas de la asignatura, dividiendo las tareas en diferentes bloques liderados por distintos miembros del equipo (Requisitos, Análisis, Diseño, Implementación y Pruebas).
