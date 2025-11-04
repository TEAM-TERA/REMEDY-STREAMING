package tera.remedy.streaming.application.youtube;

import lombok.extern.slf4j.Slf4j;
import tera.remedy.streaming.application.youtube.exception.YoutubeDownloadFailedException;
import tera.remedy.streaming.application.youtube.exception.YoutubeDownloadTimeoutException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class YoutubeDownloadService {
    private String downloadAudio(String videoId, String songTitle) throws IOException, InterruptedException {
        Path downloadPath = Paths.get("./songs");
        Files.createDirectories(downloadPath);

        String outputPath = downloadPath.resolve(songTitle + ".%(ext)s").toString();
        String finalPath = downloadPath.resolve(songTitle + ".mp3").toString();

        ProcessBuilder processBuilder = new ProcessBuilder(
                "yt-dlp",
                "--extract-audio",
                "--audio-format", "mp3",
                "--audio-quality", "192K",
                "--output", outputPath,
                "--no-playlist",
                "https://www.youtube.com/watch?v=" + videoId
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            boolean finished = process.waitFor(60, TimeUnit.SECONDS);
            executor.shutdown();

            if (!finished) {
                process.destroyForcibly();
                throw new YoutubeDownloadTimeoutException();
            }

            if (process.exitValue() != 0) {
                throw new YoutubeDownloadFailedException();
            }

            return finalPath;
        }
    }
}
