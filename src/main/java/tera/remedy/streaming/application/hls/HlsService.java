package tera.remedy.streaming.application.hls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.hls.exception.HlsConvertFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class HlsService {

    private static final String HLS_PATH = "/app/songs/hls";

    public String convertToHls(String mp3FilePath) throws IOException, InterruptedException {
        log.info("HLS 변환 시작: {}", mp3FilePath);

        validateFileExists(mp3FilePath);

        String hlsId = UUID.randomUUID().toString();
        Path hlsDir = createHlsDirectory(hlsId);

        ProcessBuilder processBuilder = createFfmpegProcessBuilder(mp3FilePath, hlsDir);

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StringBuilder output = readProcessOutput(process);

        int exitCode = process.waitFor();
        log.info("FFmpeg 종료 코드: {}", exitCode);

        validateConversionSuccess(exitCode, output);

        Files.deleteIfExists(Paths.get(mp3FilePath));
        log.info("HLS 변환 완료: {}", hlsId);

        return hlsId;
    }

    private void validateFileExists(String mp3FilePath) {
        if (!Files.exists(Paths.get(mp3FilePath))) {
            log.error("입력 파일이 존재하지 않습니다: {}", mp3FilePath);
            throw new HlsConvertFailedException();
        }
    }

    private Path createHlsDirectory(String hlsId) throws IOException {
        Path hlsDir = Paths.get(HLS_PATH, hlsId);
        Files.createDirectories(hlsDir);
        log.info("HLS 디렉토리 생성: {}", hlsDir);
        return hlsDir;
    }

    private ProcessBuilder createFfmpegProcessBuilder(String mp3FilePath, Path hlsDir) {
        return new ProcessBuilder(
                "ffmpeg",
                "-i", mp3FilePath,
                "-codec:a", "aac",
                "-b:a", "192k",
                "-vn",
                "-f", "hls",
                "-hls_time", "2",
                "-hls_list_size", "0",
                "-hls_segment_filename", hlsDir.resolve("segment_%03d.ts").toString(),
                hlsDir.resolve("playlist.m3u8").toString()
        );
    }

    private StringBuilder readProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
            log.debug("FFmpeg: {}", line);
        }
        return output;
    }

    private void validateConversionSuccess(int exitCode, StringBuilder output) {
        if (exitCode != 0) {
            log.error("FFmpeg 변환 실패. 전체 출력:\n{}", output);
            throw new HlsConvertFailedException();
        }
    }
}
