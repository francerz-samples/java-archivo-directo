import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ArchivoDirecto implements Closeable
{
    private long tamanioRegistro;
    private long areaReservada;
    private RandomAccessFile raf;

    public ArchivoDirecto(String file, long tamanioRegistro, int registrosReservados)
        throws IOException
    {
        this.raf = new RandomAccessFile(file, "rw");
        this.tamanioRegistro = tamanioRegistro;
        this.areaReservada = registrosReservados * tamanioRegistro;
    }

    private long calcularPosicionFinal()
        throws IOException
    {
        long tamanioArchivo = raf.length();
        long posicion = (long)Math.ceil(1.0 * tamanioArchivo / tamanioRegistro) * tamanioRegistro;

        if (posicion < areaReservada) {
            return areaReservada;
        }

        System.out.printf(
            "{tamanioArchivo:%d, tamanioRegistro:%d, posicion:%d}\n",
            tamanioArchivo,
            tamanioRegistro,
            posicion
        );

        return posicion;
    }

    private byte leerOcupado() throws IOException
    {
        try {
            return raf.readByte();
        } catch (EOFException eofex) {
            return 0;
        }
    }

    private long[] calcularPosicion(int indice)
        throws IOException
    {
        
        // Calcular la posición predeterminada del registro.
        long posicion = indice * tamanioRegistro;
        long anterior = posicion;
        raf.seek(posicion);

        // Verificar si el registro está ocupado.
        byte ocupado = leerOcupado();

        // Si el espacio del registro está ocupado, pasar al siguiente.
        while (ocupado == 1) {
            // Mantener indicador del anterior.
            anterior = posicion;
            System.out.println("anterior="+anterior+",posicion="+posicion);
            // Leer la posición del siguiente registro con mismo índice.
            posicion = raf.readInt();

            // Si no hay más registros con mismo índice, mover al final del archivo.
            if (posicion == 0) {
                posicion = calcularPosicionFinal();
                break;
            }

            // Colocar el puntero en la nueva posición y verificar si está ocupado.
            raf.seek(posicion);
            ocupado = leerOcupado();
        }
        // Retornar la posición resultante.
        return new long[] {posicion, anterior};
    }

    public void escribir(RegistroArchivoDirecto rad)
        throws IOException
    {
        long[] posiciones = calcularPosicion(rad.getIndice());
        int posicion = (int)posiciones[0],
            anterior = (int)posiciones[1];

        // Colocar el puntero en posición.
        raf.seek(posicion);

        // Establecer el estado del registro en el archivo
        raf.writeByte(1);

        // Escribir el registro en el archivo.
        raf.seek(posicion + 5);
        rad.escribir(raf);

        // Colocar el apuntador del registro en el anterior si es que es diferente al predeterminado
        if (anterior != posicion) {
            raf.seek(anterior + 1);
            raf.writeInt(posicion);
        }
    }

    public void close()
        throws IOException
    {
        raf.close();
    }
}
