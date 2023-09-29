package yt.downloader;

import com.github.felipeucelli.javatube.Stream;
import com.github.felipeucelli.javatube.Youtube;

public class YTDownloader {

    private static void progress(Long value) {
        App.progress.put(, value);
    }



    // https://www.youtube.com/watch?v=tvKRrT11ugg
    // A5ovL4HTwDs
    // qgeaoW55Pks

    public static void start_MP4_HIGH_download(String ip, String link) {
        try {
            Stream stream = new Youtube(link).streams().getHighestResolution();

            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                stream.download("videos/", link.substring(32, 43), YTDownloader::progress);
            } else if (link.startsWith("https://youtu.be/tvKRrT11ugg?si=VG4W7jSd951v2oRk")) {
                stream.download("videos/", link.substring(17, 28), YTDownloader::progress);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void start_MP4_LOW_download(String ip, String link) {
        try {
            Stream stream = new Youtube(link).streams().getLowestResolution();
            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                stream.download("videos/", link.substring(32, 43), YTDownloader::progress);
                // https://www.youtube.com/watch?v=tvKRrT11ugg
                // A5ovL4HTwDs
                // qgeaoW55Pks
            } else if (link.startsWith("https://youtu.be/tvKRrT11ugg?si=VG4W7jSd951v2oRk")) {
                stream.download("videos/", link.substring(17, 28), YTDownloader::progress);
            }
        } catch (Exception e) { e.printStackTrace(); }

    }

    public static void start_MP3_download(String link) {
        try {
            Stream stream = new Youtube(link).streams().getOnlyAudio();
            if (link.startsWith("https://www.youtube.com/watch?v=")) {
                stream.download("videos/", link.substring(32, 43), YTDownloader::progress);
                // https://www.youtube.com/watch?v=tvKRrT11ugg
                // A5ovL4HTwDs
                // qgeaoW55Pks
            } else if (link.startsWith("https://youtu.be/tvKRrT11ugg?si=VG4W7jSd951v2oRk")) {
                stream.download("videos/", link.substring(17, 28), YTDownloader::progress);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
