package yt.downloader;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

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

    @GetMapping("/favicon.ico")
    String icon() {
        return "<img src=\"https://cdn.discordapp.com/attachments/779418496601686016/1157283999413190677/youtube-dl-icon.png?ex=65180c36&is=6516bab6&hm=dd743742bbf5f8a7a78611df40a2b9f02ee419cf9edc95aa92051d15eba490ed&\">";
    }


    // 'http://localhost:8080/download?link='+youtube_link+"&format="+download_format
    // 129.152.4.113:25533
    // @RequestMapping(value = "download", method = RequestMethod.POST)


    @PostMapping("/download")
    @MessageMapping("/download")
    @SendTo("/downloader")
    String download(@RequestParam("link") String link, @RequestParam("format") String format, HttpServletRequest request) {
        String remoteAddress = null;
        try {
            remoteAddress = request.getRemoteAddr();
        } catch (Exception e) {
            return "";
        }
        if (remoteAddress != null) {
            switch (format) {
                case "mp4h": {
                    YTDownloader.start_MP4_HIGH_download(link);
                    break;
                }
                case "mp4l": {
                    YTDownloader.start_MP4_LOW_download(link);
                    break;
                }
                case "mp3a": {
                    YTDownloader.start_MP3_download(link);
                    break;
                }
            }
            return App.progress.get(remoteAddress).toString();
        }
        return App.webpage;
    }
}
