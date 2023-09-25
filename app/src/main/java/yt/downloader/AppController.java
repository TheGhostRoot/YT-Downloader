package yt.downloader;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {


    @GetMapping("/")
    String index() {
        return App.webpage;
    }
}
