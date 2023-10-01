package yt.downloader;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {


    @GetMapping("/")
    String index() {
        return App.webpage;
    }

    @GetMapping("/styles.css")
    String style() {
        return App.style;
    }

    @GetMapping("/app.js")
    String js() {
        return App.js;
    }

    @GetMapping("/progress.js")
    String progressjs() {
        return App.progressJS;
    }
    // favicon.ico


    // 129.152.4.113:25533

    @GetMapping("/downloader")
    HashMap<String, String> getProgress(HttpServletRequest request) {
        String remoteAddress = null;
        HashMap<String, String> data = new HashMap<>();
        try {
            remoteAddress = request.getRemoteAddr();
        } catch (Exception e) {
            data.put("stats", "No Videos To Download");
            data.put("link", "");
            data.put("format", "");
            data.put("title", "");
            return data;
        }
        if (remoteAddress != null) {
            remoteAddress = "0:0:0:0:0:0:0:1".equals(remoteAddress) ? "127.0.0.1" : remoteAddress;
            // count how many times the remoteAddress is in the App.IDs.values() and use this info to tell it where the download progress is.
            // The server will send request to API that will tell the name of the video and store it with the ID
            long id = 0;
            for (Map.Entry<Long, String> entry : App.IDs.entrySet()) {
                if (entry.getValue().equals(remoteAddress)) {
                    id = entry.getKey();
                    break;
                }
            }
            if (App.IDs.containsValue(remoteAddress)) {
                data.put("stats", "Downloading...");
                String link = App.links.get(id);
                if (link.startsWith("https://www.youtube.com/watch?v=")) {
                    data.put("link", link.substring(32, 43));
                } else if (link.startsWith("https://youtu.be/")) {
                    data.put("link", link.substring(17, 28));
                } else {
                    data.put("link", "");
                }
                data.put("format", App.formats.get(id));
                data.put("title", App.titles.get(id));
            } else {
                data.put("stats", "No Videos To Download");
                data.put("link", "");
                data.put("format", "");
                data.put("title", "");
            }

            return data;
        }
        data.put("stats", "No Videos To Download");
        data.put("link", "");
        data.put("format", "");
        data.put("title", "");
        return data;
    }

    @GetMapping("/ask")
    public ResponseEntity<Resource> download(String id, String format) {
        if (id.contains("http")) { return ResponseEntity.noContent().build();}

        if (Files.exists(Path.of("videos/"+id))) {

            File file = new File("videos/"+id+"."+format);

            if (file.exists() && file.isFile()) {
                try {
                    return ResponseEntity.ok()
                            .contentLength(file.length())
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(new InputStreamResource(new FileInputStream(file)));
                } catch (Exception e) {
                    return ResponseEntity.noContent().build();
                }
            }
        }
        return ResponseEntity.noContent().build();
    }
    

    @PostMapping("/download")
    String download(@RequestParam("link") String link, @RequestParam("format") String format, HttpServletRequest request) {
        String remoteAddress = null;
        try {
            remoteAddress = request.getRemoteAddr();
        } catch (Exception e) {
            return "";
        }

         remoteAddress = "0:0:0:0:0:0:0:1".equals(remoteAddress) ? "127.0.0.1" : remoteAddress;
         System.out.println("Download request from "+remoteAddress);

         YTDownloader.manage_IDs(remoteAddress, link, format);

         return App.webpage;
    }
}
