package tera.remedy.streaming.repository.storage;

import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Repository
public class LocalStorageRepository implements StorageRepository {

    private static final String PATH = "/app/songs";
    private static final String AUDIO_DIR = "audio";

    public String saveDownloadAudio(Path tempFilePath, String fileName) {
        String saveFileName = UUID.randomUUID() + "_" + fileName + ".mp3";
        Path savePath = Path.of(PATH, AUDIO_DIR, saveFileName);

        try {
            Files.move(tempFilePath, savePath);
            return AUDIO_DIR + "/" + saveFileName;
        } catch (IOException e) {
            throw new RuntimeException("다운로드된 오디오 파일 저장 실패", e);
        }
    }

    public LocalStorageRepository(){
        try{
            Path audioPath = Path.of(PATH, AUDIO_DIR);
            Files.createDirectories(audioPath);
        } catch(IOException e){
            throw new RuntimeException("디렉토리 생성 실패",e);
        }
    }
}
