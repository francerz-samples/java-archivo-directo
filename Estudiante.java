import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

public class Estudiante implements RegistroArchivoDirecto
{
    private String numControl;
    private String nombre;
    private String carrera;
    private short creditos;
    private float calificacion;
    
    public Estudiante(
        String numControl,
        String nombre,
        String carrera,
        short creditos,
        float calificacion
    ) {
        this.numControl = numControl;
        this.nombre = nombre;
        this.carrera = carrera;
        this.creditos = creditos;
        this.calificacion = calificacion;
    }

    public int getIndice()
    {
        return Integer.parseInt(
            this.numControl.substring(
                this.numControl.length() - 1
            )
        );
    }

    public void escribir(RandomAccessFile raf)
        throws IOException
    {
        raf.writeUTF(this.numControl);
        raf.writeUTF(this.nombre);
        raf.writeUTF(this.carrera);
        raf.writeShort(this.creditos);
        raf.writeFloat(this.calificacion);
    }
}
