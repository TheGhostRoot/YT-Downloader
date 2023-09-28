package yt.downloader;

import org.springframework.web.bind.annotation.GetMapping;
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
}
