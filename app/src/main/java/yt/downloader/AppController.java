package yt.downloader;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    String getProgress(HttpServletRequest request) {
        String remoteAddress = null;
        try {
            remoteAddress = request.getRemoteAddr();
        } catch (Exception e) { return "No Videos To Download"; }
        if (remoteAddress != null) {
            remoteAddress = remoteAddress == "0:0:0:0:0:0:0:1" ? remoteAddress : "127.0.0.1";
            System.out.println(remoteAddress+" is in waiting "+App.IDs.values().contains(remoteAddress));
            // count how many times the remoteAddress is in the App.IDs.values() and use this info to tell it where the download progress is.
            // The server will send request to API that will tell the name of the video and store it with the ID
            // 
            return App.IDs.values().contains(remoteAddress) ? "Downloading...\nNAME\nFORMAT" : "No Videos To Download";
        }
        return "No Videos To Download";
    }

    @GetMapping("/ask")
    public ResponseEntity<Resource> download(String id, String format) throws IOException {

        if (Files.exists(Path.of("videos/"+id))) {

            File file = new File("videos/"+id+"."+format);

            if (file.exists()) {
                return ResponseEntity.ok()
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(new InputStreamResource(new FileInputStream(file)));
            }
        }
        return (ResponseEntity<Resource>) ResponseEntity.badRequest();
    }
    

    @PostMapping("/download")
    String download(@RequestParam("link") String link, @RequestParam("format") String format, HttpServletRequest request) {
        String remoteAddress = null;
        try {
            remoteAddress = request.getRemoteAddr();
        } catch (Exception e) {
            return "";
        }
        /*
        Thread t = new Thread(() -> YTDownloader.manage_IDs(finalRemoteAddress, link));
                    t.setDaemon(true);
                    t.start();
         */

         remoteAddress = remoteAddress == "0:0:0:0:0:0:0:1" ? remoteAddress : "127.0.0.1";
         System.out.println("Download request from "+remoteAddress);

        if (remoteAddress != null) {
            YTDownloader.manage_IDs(remoteAddress, link, format);
        }
        return App.webpage;
    }
}
