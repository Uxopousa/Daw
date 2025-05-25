/**
 * FileUtils.java
 *
 * Clase plantilla de utilidades para xestión de ficheiros en Java.
 *
 * ------------------------------
 * Uso de String.format
 * ------------------------------
 * String.format é un método que permite construír cadeas de texto con
 * formato controlado, similar ao printf de C. A súa sinatura básica é:
 *
 *     String.format(String format, Object... args)
 *
 * Onde `format` é unha cadea que contén texto literal e especificadores
 * de formato (placeholders) que se substitúen polos valores de `args`.
 *
 * Exemplos de especificadores comúns:
 *   %s    – string
 *   %d    – enteiro decimal
 *   %f    – número en coma flotante
 *   %n    – nova liña (portábel)
 *   %%    – carácter `%`
 *
 * Modificadores:
 *   %04d     – enteiro con ancho mínimo 4, completado con ceros á esquerda
 *   %-15.15s – string con ancho 15, alineado á esquerda, recortado a 15 chars
 *   %10.2f   – flotante con ancho 10 e dúas decimais
 *
 * Exemplos usados nos exercicios:
 *   • String.format("%04d", id)                       – formata un ID de 4 díxitos.
 *   • String.format("%-15.15s", nome)                 – nome de campo fixo 15 chars.
 *   • String.format("%03d", nivel)                    – nivel de 3 díxitos con ceros.
 *   • String.format("%-20s %10d bytes %s", name, size, date)
 *     – liña de info de ficheiro con columnas fixas.
 *
 * ------------------------------
 * Métodos incluídos en FileUtils
 * ------------------------------
 *  - createFile(path)
 *  - writeWithFileWriter(path, text)
 *  - appendWithFileWriter(path, text)
 *  - readWithFileReader(path)
 *  - writeWithPrintWriter(path, lines)
 *  - writeFormatted(path, format, args)
 *  - randomAccessWrite(path, position, data, recordLength)
 *  - randomAccessRead(path, position, recordLength)
 *  - copyFile(source, dest)
 *  - serializeObject(path, obj)
 *  - deserializeObject(path)
 *  - listFiles(dirPath)
 *  - countLines(path)
 *  - getFileInfo(path)
 *  - toFixedLengthChars(s, length)
 *  - writeFixedString(raf, s, length)
 *  - readFixedString(raf, length)
 *
 * Cada método usa try-with-resources, xestión detallada de erros e
 * mensaxes exemplares con String.format para facilitar o exame.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {

    public static void createFile(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
            System.out.println(String.format("Directorios creados: %s", parent.getAbsolutePath()));
        }
        if (!file.exists()) {
            boolean creado = file.createNewFile();
            System.out.println(String.format("Ficheiro creado (%s): %b",
                                             file.getAbsolutePath(), creado));
        } else {
            System.out.println(String.format("Ficheiro xa existe: %s",
                                             file.getAbsolutePath()));
        }
    }

    public static void writeWithFileWriter(String path, String text) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(text);
            System.out.println(String.format("Escribíronse %d caracteres en %s",
                                             text.length(), path));
        }
    }

    public static void appendWithFileWriter(String path, String text) throws IOException {
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write(text);
            System.out.println(String.format("Añadíronse %d caracteres ao final de %s",
                                             text.length(), path));
        }
    }

    public static String readWithFileReader(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        String result = sb.toString();
        System.out.println(String.format("Lidos %d caracteres de %s",
                                         result.length(), path));
        return result;
    }

    public static void writeWithPrintWriter(String path, List<String> lines) throws IOException {
        try (PrintWriter pw = new PrintWriter(path)) {
            for (String l : lines) {
                pw.println(l);
            }
            System.out.println(String.format("Escribíronse %d liñas en %s",
                                             lines.size(), path));
        }
    }

    public static void writeFormatted(String path, String format, Object... args) throws IOException {
        try (PrintWriter pw = new PrintWriter(path)) {
            pw.printf(format, args);
            System.out.println(String.format("Escrito con formato '%s' en %s",
                                             format, path));
        }
    }

    public static void randomAccessWrite(String path,
                                         long position,
                                         String data,
                                         int recordLength) throws IOException
    {
        if (data.length() != recordLength) {
            throw new IllegalArgumentException(
                String.format("Data length %d != recordLength %d",
                              data.length(), recordLength));
        }
        try (RandomAccessFile raf = new RandomAccessFile(path, "rw")) {
            raf.seek(position);
            raf.writeChars(data);
            System.out.println(String.format(
                "Escrito rexistro de %d chars en byte pos %d",
                recordLength, position));
        }
    }

    public static String randomAccessRead(String path,
                                          long position,
                                          int recordLength) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
            raf.seek(position);
            StringBuilder sb = new StringBuilder(recordLength);
            for (int i = 0; i < recordLength; i++) {
                sb.append(raf.readChar());
            }
            String rec = sb.toString();
            System.out.println(String.format(
                "Lido rexistro de %d chars dende byte pos %d",
                recordLength, position));
            return rec;
        }
    }

    public static void copyFile(String source, String dest) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest))
        {
            byte[] buffer = new byte[4096];
            int len;
            long total = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
                total += len;
            }
            System.out.println(String.format(
                "Copiados %d bytes de %s a %s",
                total, source, dest));
        }
    }

    public static void serializeObject(String path, Object obj) throws IOException {
        try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(path)))
        {
            oos.writeObject(obj);
            System.out.println(String.format("Objeto serializado a %s", path));
        }
    }

    public static Object deserializeObject(String path)
        throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(path)))
        {
            Object obj = ois.readObject();
            System.out.println(String.format("Objeto deserializado dende %s", path));
            return obj;
        }
    }

    public static List<String> listFiles(String dirPath) {
        File dir = new File(dirPath);
        List<String> names = new ArrayList<>();
        if (dir.exists() && dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                names.add(f.getName());
            }
        }
        System.out.println(String.format("Listado de %d ficheiros en %s",
                                         names.size(), dirPath));
        return names;
    }

    public static int countLines(String path) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while (br.readLine() != null) count++;
        }
        System.out.println(String.format("Contadas %d liñas en %s",
                                         count, path));
        return count;
    }

    public static String getFileInfo(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return String.format("Ficheiro non existe: %s", path);
        }
        String name = file.getName();
        long size = file.length();
        Date lm = new Date(file.lastModified());
        return String.format("%-20s %10d bytes %s",
                             name, size, lm.toString());
    }

    /**
     * Converte un String nun array de chars de lonxitude fixa,
     * garantindo 2 bytes por char (UTF-16 code unit), recortando ou
     * completando con espazos. Funciona con acentos, símbolos, etc.
     *
     * @param s       texto orixinal (pode conter caracteres especiais)
     * @param length  número de chars fixos (p.ex. 40)
     * @return        array de char de tamaño length
     */
    public static char[] toFixedLengthChars(String s, int length) {
        if (s == null) s = "";
        if (s.length() > length) {
            return s.substring(0, length).toCharArray();
        }
        char[] arr = new char[length];
        int i = 0;
        for (; i < s.length(); i++) {
            arr[i] = s.charAt(i);
        }
        for (; i < length; i++) {
            arr[i] = ' ';
        }
        return arr;
    }

    /**
     * Escribe unha cadea fixa nun RandomAccessFile, 2 bytes por char,
     * preservando caracteres Unicode BMP.
     *
     * @param raf      RandomAccessFile en modo "rw"
     * @param s        String a escribir
     * @param length   número de chars fixos (p.ex. 40)
     * @throws IOException se ocorre un erro de I/O
     */
    public static void writeFixedString(RandomAccessFile raf,
                                        String s,
                                        int length) throws IOException
    {
        char[] data = toFixedLengthChars(s, length);
        for (char c : data) {
            raf.writeChar(c);
        }
    }

    /**
     * Lê unha cadea de lonxitude fixa dun RandomAccessFile, 2 bytes por char.
     *
     * @param raf      RandomAccessFile en modo "r"
     * @param length   número de chars fixos a ler
     * @return         String resultante (trimado)
     * @throws IOException se ocorre un erro de I/O
     */
    public static String readFixedString(RandomAccessFile raf,
                                         int length) throws IOException
    {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

}
