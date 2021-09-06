import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class IOUtils {

    public static List<String> readFileIntoList(String file) throws IOException {
        List<String> data = Collections.emptyList();
        data = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
        return data;
    }

    public static void saveDataIntoFile(String fileName, List<String> data) throws IOException {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < data.size(); i++) {
            bw.write(data.get(i).toString() + "\n");
        }
        bw.flush();
        bw.close();
        fw.close();
    }

}
