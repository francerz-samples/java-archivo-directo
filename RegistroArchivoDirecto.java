import java.io.IOException;
import java.io.RandomAccessFile;

public interface RegistroArchivoDirecto
{
    public int getIndice();
    public void escribir(RandomAccessFile raf) throws IOException;    
}
