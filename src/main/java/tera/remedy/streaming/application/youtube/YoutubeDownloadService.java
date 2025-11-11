package tera.remedy.streaming.application.youtube;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.youtube.exception.YoutubeDownloadFailedException;
import tera.remedy.streaming.application.youtube.exception.YoutubeDownloadTimeoutException;
import tera.remedy.streaming.repository.storage.StorageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeDownloadService {

    private final StorageRepository storageRepository;
    public String downloadAudio(String videoId, String songTitle) throws IOException, InterruptedException {

        Path tempDownloadPath = Paths.get(System.getProperty("java.io.tmpdir"), "youtube-downloads");
        Files.createDirectories(tempDownloadPath);

        String outputPath = tempDownloadPath.resolve(songTitle + ".%(ext)s").toString();
        Path tempFilePath = tempDownloadPath.resolve(songTitle + ".mp3");

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

            return storageRepository.saveDownloadAudio(tempFilePath, songTitle);
        } catch (Exception e) {
            Files.deleteIfExists(tempFilePath);
            throw e;
        }
    }
    public int getDuration(String videoId) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "yt-dlp",
                "--print", "duration",
                "https://www.youtube.com/watch?v=" + videoId
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );
        String output = reader.readLine();

        int exitCode = process.waitFor();

        if (exitCode != 0 || output == null) {
            throw new YoutubeDownloadFailedException();
        }

        return Integer.parseInt(output.trim());
    }
}
