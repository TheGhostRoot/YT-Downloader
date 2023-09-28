package yt.downloader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
 // favicon.ico

    @PostMapping("/download")
    String download(@RequestBody String link, @RequestBody String format) {
        switch (format) {
            case "mp4h": {
                // TODO download MP4 with high quality
                break;
            }
            case "mp4l": {
                // TODO download MP4 with low quality
                break;
            }
            case "mp3a": {
                // TODO download MP3 audio (high if possible)
                break;
            }
        }
        return App.webpage;
    }
}
