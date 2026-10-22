# TECNIHOGAR

Aplicación de consola desarrollada en Java para administrar órdenes de servicios técnicos a domicilio, cuadrillas y jornadas de trabajo.

## INSTRUCCIONES DE COMPILACIÓN

1. Abrir una terminal en la carpeta principal del proyecto.

2. Crear la carpeta `bin` para almacenar los archivos compilados:

   ```bash
   mkdir bin
   ```

3. Compilar todos los archivos Java utilizando el siguiente comando:

   ```bash
   javac -d bin *.java
   ```

## INSTRUCCIONES DE EJECUCIÓN

Una vez compilado el programa, ejecutar el siguiente comando:

```bash
java -cp bin Main
```

El programa mostrará el menú principal de TecniHogar, desde el cual se podrán realizar las diferentes operaciones.

## NOTAS

- La carpeta `bin` almacena los archivos `.class` generados durante la compilación.
- Si se modifica algún archivo `.java`, se debe compilar nuevamente.
- Los datos se almacenan únicamente en memoria y se reinician al cerrar el programa.
- El menú permanece disponible hasta que el usuario seleccione la opción de finalizar.