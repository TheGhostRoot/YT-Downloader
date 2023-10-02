package yt.downloader;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
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


    @GetMapping("/downloader")
    HashMap<String, String> getProgress(HttpServletRequest request) {
        String remoteAddress = null;
        HashMap<String, String> data = new HashMap<>();
        data.put("stats", "No Videos To Download");
        data.put("link", "");
        data.put("format", "");
        data.put("title", "");
        try {
            remoteAddress = request.getRemoteAddr();
        } catch (Exception e) {
            return data;
        }
        if (remoteAddress != null) {
            remoteAddress = "0:0:0:0:0:0:0:1".equals(remoteAddress) ? "127.0.0.1" : remoteAddress;
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
            }

            return data;
        }
        return data;
    }

    @GetMapping("/ask")
    public ResponseEntity<Resource> download(String id, String format) {
        if (id.contains("http")) { return ResponseEntity.noContent().build();}

        if (Files.exists(Path.of("videos/"+id+"."+format))) {
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

         YTDownloader.manage_IDs("0:0:0:0:0:0:0:1".equals(remoteAddress) ? "127.0.0.1" : remoteAddress, link, format);

         return App.webpage;
    }
}
