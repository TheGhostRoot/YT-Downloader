package yt.downloader;


import com.github.felipeucelli.javatube.Youtube;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
public class AppController {


    @GetMapping("/")
    String index(@RequestParam(required = false) String ytlinks) throws Exception {
        if (ytlinks != null) {
            List<String> links = Arrays.stream(ytlinks.replaceAll(" ", "").split("\n")).toList();
            if (App.filterLinks(links).isEmpty()) {
                System.out.println("They are not youtube links");
            }
        }
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
