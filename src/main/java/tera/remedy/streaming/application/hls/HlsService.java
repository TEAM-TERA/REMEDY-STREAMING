package tera.remedy.streaming.application.hls;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class HlsService {

    private static final String HLS_PATH = "/app/songs/hls";

    public String convertToHls(String mp3FilePath) throws IOException, InterruptedException {

        String hlsId = UUID.randomUUID().toString();
        Path hlsDir = Paths.get(HLS_PATH, hlsId);
        Files.createDirectories(hlsDir);

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-i", mp3FilePath,
                "-codec:a", "aac",
                "-b:a", "192k",
                "-vn",
                "-f", "hls",
                "-hls_time", "10",
                "-hls_list_size", "0",
                "-hls_segment_filename", hlsDir.resolve("segment_%03d.ts").toString(),
                hlsDir.resolve("playlist.m3u8").toString()
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();


        if (exitCode != 0) {
            throw new RuntimeException("HLS 변환 실패");
        }

        Files.deleteIfExists(Paths.get(mp3FilePath));

        return "hls/" + hlsId + "/playlist.m3u8";
    }

}
