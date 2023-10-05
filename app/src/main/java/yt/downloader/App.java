/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package yt.downloader;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class App {

    public static ConfigManager configManager = null;
    public static String webpage;
    public static String style;
    public static String js;

    public static String progressJS;

    public static Random random = new Random();

    // ID :  Video title
    public static HashMap<Long, String> titles = new HashMap<>();

    // ID :  Format name -> mp4 / mp3
    public static HashMap<Long, String> formats = new HashMap<>();

    public static HashMap<Long, String> links = new HashMap<>();

    // IP Addr : Amount of times
    public static HashMap<String, Long> visitedTimes = new HashMap<>();


    // ID : IP Addr
    public static HashMap<Long, String> IDs = new HashMap<>();



    public static HashMap<String, Object> config = null;

    public static void main(String[] args) {
        try {
            configManager = new ConfigManager();
        } catch (Exception e) { e.printStackTrace(); }


        Yaml yaml = new Yaml();

        try {
            config = yaml.load(new FileInputStream("config.yml"));
        } catch (Exception e) { System.out.println("Can't read config.yml  file"); }


        for (String path : List.of("web/index.html", "web/styles.css", "web/app.js", "web/progress.js")) {
            File file = new File(path);
            if (file.exists()) {
                try (Scanner myReader = new Scanner(file)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (myReader.hasNextLine()) {
                        String line = myReader.nextLine();
                        if (config != null) {
                            line.replace(((Map<?, ?>)((Map<?, ?>) config.get("frontend")).get("send_download_request_to_server")).get("placeholder").toString(),
                                    ((Map<?, ?>)((Map<?, ?>) config.get("frontend")).get("send_download_request_to_server")).get("value").toString())

                                    .replace(((Map<?, ?>)((Map<?, ?>) config.get("frontend")).get("ask_server_for_download_stats")).get("placeholder").toString(),
                                            ((Map<?, ?>)((Map<?, ?>) config.get("frontend")).get("ask_server_for_download_stats")).get("value").toString())

                                    .replace(((Map<?, ?>)((Map<?, ?>) config.get("frontend")).get("client_wants_to_download_the_file")).get("placeholder").toString(),
                                            ((Map<?, ?>)((Map<?, ?>) config.get("frontend")).get("client_wants_to_download_the_file")).get("value").toString());
                        }
                        System.out.println(line);
                        stringBuilder.append(line).append("\n");
                    }
                    myReader.close();
                    if (path.endsWith("app.js")) {
                        js = stringBuilder.toString();
                    } else if (path.endsWith("progress.js")) {
                        progressJS = stringBuilder.toString();
                    } else if (path.endsWith(".css")){
                        style = stringBuilder.toString();
                    } else {
                        webpage = stringBuilder.toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (path.endsWith(".js")) {
                    js = "function updateDownloadProgress(percent) { document.getElementById(\"progress\").innerHTML = percent }";
                } else if (path.endsWith(".css")){
                    style = "body { background-image: url('background.jpg');background-repeat: no-repeat;background-attachment: fixed;background-size: cover; }";
                } else {
                    webpage = configManager != null ? configManager.getIndexHtml() : "";
                }
            }
        }

        Runnable helloRunnable = () -> {
            for (String ip : new ArrayList<>(visitedTimes.keySet())) {
                if (visitedTimes.containsKey(ip) && visitedTimes.get(ip) > 0) {
                    visitedTimes.put(ip, visitedTimes.get(ip)-1);
                }
            }
        };

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);

        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", ((Map<?, ?>) config.get("backend")).get("port").toString() ));
        app.run(args);

    }


    public static boolean isOverTheLimitIP(String ip) {
        try {

            if (visitedTimes.containsKey(ip) && visitedTimes.get(ip) >= Long.parseLong(((Map<?, ?>) config.get("backend")).get("max_requests_per_second").toString())) {
                return true;
            } else {

                if (1 < getAll_ID_from_IP(ip).stream().filter(id -> formats.get(id).equals("mp4") ||
                        formats.get(id).equals("mp3")).count()) {

                    return true;
                }

                if (visitedTimes.containsKey(ip)) {
                    visitedTimes.put(ip, visitedTimes.get(ip) + 1);
                } else {
                    visitedTimes.put(ip, 1L);
                }
                return false;
            }
        } catch (Exception e) { return true; }
    }

    public static List<Long> getAll_ID_from_IP(String ip) {
        return App.IDs.keySet().stream().filter(id -> App.IDs.get(id).equals(ip)).toList();
    }

}
