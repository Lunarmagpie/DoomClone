import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class Level {
    String name;

    int width;
    int height;
    HashMap<String, Integer> blocks = new HashMap<String, Integer>();
    HashMap<String, Integer> entities = new HashMap<String, Integer>();

    public static void main(String[] args) {
        Level l = new Level("level.yaml");
    }

    public Level(String name) {
        this.name = name;

        File level = new File("src/level/" + name).getAbsoluteFile();

        String contents = "";

        try {
            FileInputStream fis = new FileInputStream(level);

            int r = 0;

            while ((r = fis.read()) != -1) {
                contents += (char) r;
            }

            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        serialize(contents);

        System.out.println(this.width);
        System.out.println(this.height);

        System.out.println(this.blocks);
        System.out.println(this.entities);
    }

    public static String getLineEnding(String line) {
        String[] parts = line.split(":");
        if (parts.length == 2) {
            return parts[1].strip();
        }
        return "";

    }

    public String serialize(String contents) {

        String[] lines = contents.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (line.startsWith("width")) {
                this.width = Integer.parseInt(getLineEnding(line));
            }
            if (line.startsWith("height")) {
                this.height = Integer.parseInt(getLineEnding(line));
            }

            if (line.startsWith("blocks")) {

                for (int j = i + 1; j < lines.length; j++) {
                    
                    System.out.println(line);

                    line = lines[j];

                    if (!line.startsWith("  ")) {
                        break;
                    }

                    String lineStart = line.split(":")[0].strip();
                    int lineValue = Integer.parseInt(getLineEnding(line));
                    this.blocks.put(lineStart, lineValue);
                }

            }


            if (line.startsWith("entities")) {

                for (int j = i + 1; j < lines.length; j++) {

                    line = lines[j];

                    if (!line.startsWith("  ")) {
                        break;
                    }

                    String lineStart = line.split(":")[0].strip();
                    int lineValue = Integer.parseInt(getLineEnding(line));
                    this.entities.put(lineStart, lineValue);
                }
            }

        }

        return contents;
    }
}