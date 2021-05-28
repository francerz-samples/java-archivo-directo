import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        try (ArchivoDirecto ad = new ArchivoDirecto("directorio.bin", 0x100, 10)) {
            Estudiante e = new Estudiante("19460010", "Javier", "Mecatrónica", (short)100, 90f);
            ad.escribir(e);
        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }
}
