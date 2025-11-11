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
    private static final String IMAGE_DIR = "images";

    public String downloadImage(String imageUrl, String fileName){

        Path savePath = Path.of(PATH, IMAGE_DIR, fileName);

        try {
            URL url = new URL(imageUrl);

            try (InputStream in = url.openStream();
            FileOutputStream out = new FileOutputStream(savePath.toFile())) {

                byte[] buffer = new byte[1024];

                int readByte;

                while((readByte = in.read(buffer)) != -1){
                    out.write(buffer, 0, readByte);
                }
            }
            return IMAGE_DIR + "/" + fileName;
        } catch (IOException e){
            throw new RuntimeException("이미지 다운로드 실패", e);
        }

    }

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
            Path imagePath = Path.of(PATH, IMAGE_DIR);

            Files.createDirectories(audioPath);
            Files.createDirectories(imagePath);
        } catch(IOException e){
            throw new RuntimeException("디렉토리 생성 실패",e);
        }
    }
}
