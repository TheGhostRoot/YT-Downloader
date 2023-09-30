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



    // 'http://localhost:8080/download?link='+youtube_link+"&format="+download_format
    // 129.152.4.113:25533
    // @RequestMapping(value = "download", method = RequestMethod.POST)

    @GetMapping("/downloader")
    String getProgress(HttpServletRequest request) {
        String remoteAddress = null;
        try {
            remoteAddress = request.getRemoteAddr();
        } catch (Exception e) {
            return "0";
        }
        if (remoteAddress != null) {
            return Objects.requireNonNullElse(App.progress.get(remoteAddress), (short) 0).toString();
        }
        return "0";
    }

    public static class ProgressResponse {
        private short progress;

        public ProgressResponse(short progress) {
            this.progress = progress;
        }
        public ProgressResponse() {
            this.progress = 0;
        }

        public short getProgress() {
            return this.progress;
        }

        // Getters and setters are not necessary here, but you can include them if needed
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
        if (remoteAddress != null) {
            YTDownloader.manage_IDs(remoteAddress, link, format);
        }
        return App.webpage;
    }
}
