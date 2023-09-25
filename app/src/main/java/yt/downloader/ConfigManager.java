package yt.downloader;

import com.google.common.io.Resources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {


    public ConfigManager() throws IOException {

        //DumperOptions options = new DumperOptions();
        //options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        //yaml = new Yaml(options);
        List<String> files = List.of("web/index.html", "config.yml");
        for (String name : files) {
            String sub = name.substring(0, name.lastIndexOf('.'));
            if (!Files.exists(Path.of(name)) || !Files.exists(Path.of(sub))) {
                if (name.contains("/")) {
                    int i = sub.lastIndexOf('/');
                    File folders = new File(sub.substring(0, i == -1 ? sub.length() : i));
                    if (!folders.exists()) {
                        folders.mkdirs();
                    }
                }
                loadResource(name, name);
            }
        }

            // Open a FileWriter for the specified file
            //FileWriter writer = new FileWriter("config.yml");

            // Write the YAML data to the file
            //yaml.dump(getConfigData(), writer);

            // Close the FileWriter
            //writer.close();

        //configData = yaml.load(new FileInputStream("config.yml"));
    }

    public String getIndexHtml() {
        try {
            return Resources.toString(Resources.getResource("web/index.html"), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Website is developing.. Please wait!";
        }
    }


    private void loadResource(String resourcePath, String outputPath) throws IOException {
        FileWriter writer = new FileWriter(outputPath);
        writer.write(Resources.toString(Resources.getResource(resourcePath), StandardCharsets.UTF_8));
        writer.close();
    }


}
