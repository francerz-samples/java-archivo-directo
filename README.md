```java
// DEFINIR CONSTANTES
TAMAÑO_REGISTRO      = 100
INDICES_RESERVADOS   = 10
AREA_RESERVADA       = INDICES_RESERVADOS * TAMAÑO_REGISTRO

IMPRIMIR "Introducir N´mero de Control: "
numControl = Teclado.leer()


archivo = ArchivoAleatorio.abrir("MiArchivo.bin")

// CALCULAR POSICIÓN DE REGISTRO
indice = numControl.ultimoDigito()
posicion = indice * tamañoRegistro
anterior = posicion

archivo.setPosicion(posicion)

ocupado = archivo.leerByte()

MIENTRAS ocupado = 1 ENTONCES
    anterior = posicion

    posicion = archivo.leerInt() // 4 bytes

    SI posicion = 0 ENTONCES
        posicion = calcularPosicionAlFinal()
        SALIR_MIENTRAS
    FIN_SI

    archivo.setPosicion(posicion)

    ocupado = archivo.leerByte()

FIN_MIENTRAS



// ESCRIBIR CONTENIDO DEL REGISTRO
// Indicar que registro esta ocupado
archivo.escribirByte(1)

// Desplazar contenido 4 bytes (1 int) m´s para la posici´n
archivo.seek(archivo.getPosicion() + 4)

// Escribir datos del registro
registro.escribir(archivo)



// COLOCAR PUNTERO DEL NUEVO REGISTRO EN EL ANTERIOR
SI anterior != posicion ENTONCES
    archivo.setPosicion(anterior + 1)   // se incrementa 1 por el byte de ocupado
    archivo.escribirInt(posicion)
FIN_SI

archivo.cerrar()
```