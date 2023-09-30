package yt.downloader;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
            return App.IDs.values().contains(remoteAddress) ? "Downloading..." : "No Videos To Download";
        }
        return "No Videos To Download";
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
