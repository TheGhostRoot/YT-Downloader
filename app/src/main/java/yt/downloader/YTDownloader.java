package yt.downloader;

import com.github.felipeucelli.javatube.Stream;
import com.github.felipeucelli.javatube.Youtube;

public class YTDownloader {


    private static void progress(Long value) {}

    // https://www.youtube.com/watch?v=tvKRrT11ugg
    // A5ovL4HTwDs
    // qgeaoW55Pks

    public static void manage_IDs(String ip, String link, String format) {
        long gen_id = 0L;
        while (App.IDs.containsKey(gen_id)) {
            gen_id = App.random.nextLong();
        }
        App.IDs.put(gen_id, ip);
        App.formats.put(gen_id, format.startsWith("mp4") ? "mp4" : "mp3");
        App.links.put(gen_id, link);

        try {
            Youtube youtube = new Youtube(link);
            App.titles.put(gen_id, youtube.getTitle());
        } catch (Exception e) {  App.titles.put(gen_id, "Can't get video Title."); }

        Long id = gen_id;

        switch (format) {
            case "mp4h" -> {
                Thread th = new Thread(() -> YTDownloader.MP4_HIGH_download(id, link));
                th.setDaemon(true);
                th.start();
            }
            case "mp4l" -> {
                Thread th = new Thread(() -> YTDownloader.MP4_LOW_download(id, link));
                th.setDaemon(true);
                th.start();
            }
            case "mp3a" -> {
                Thread th = new Thread(() -> YTDownloader.MP3_download(id, link));
                th.setDaemon(true);
                th.start();
            }
        }
    }

    private static void cleanUp(Long gen_id) {
        App.IDs.remove(gen_id);
        App.links.remove(gen_id);
        App.formats.remove(gen_id);
        App.titles.remove(gen_id);
    }

    private static void MP4_HIGH_download(Long gen_id, String link) {
        try {
            Stream stream = new Youtube(link).streams().getHighestResolution();
            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                stream.download("videos/", link.substring(32, 43), YTDownloader::progress);
            } else if (link.startsWith("https://youtu.be/")) {
                stream.download("videos/", link.substring(17, 28), YTDownloader::progress);
            }
            cleanUp(gen_id);
        } catch (Exception e) {}
    }

    private static void MP4_LOW_download(Long gen_id, String link) {
        try {
            Stream stream = new Youtube(link).streams().getLowestResolution();
            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                stream.download("videos/", link.substring(32, 43), YTDownloader::progress);
            } else if (link.startsWith("https://youtu.be/")) {
                stream.download("videos/", link.substring(17, 28), YTDownloader::progress);
            }
            cleanUp(gen_id);
        } catch (Exception e) {}

    }

    private static void MP3_download(Long gen_id, String link) {
        try {
            Stream stream = new Youtube(link).streams().getOnlyAudio();
            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                stream.download("videos/", link.substring(32, 43), YTDownloader::progress);
            } else if (link.startsWith("https://youtu.be/")) {
                stream.download("videos/", link.substring(17, 28), YTDownloader::progress);
            }
            cleanUp(gen_id);
        } catch (Exception e) {}
    }
}
