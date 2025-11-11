package tera.remedy.streaming.repository.storage;

import org.springframework.stereotype.Repository;
import tera.remedy.streaming.repository.storage.exception.AudioFileSaveFailedException;
import tera.remedy.streaming.repository.storage.exception.DirectoryCreateFailedException;

import java.io.IOException;

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
            throw new AudioFileSaveFailedException();
        }
    }

    public LocalStorageRepository(){
        try{
            Path audioPath = Path.of(PATH, AUDIO_DIR);
            Files.createDirectories(audioPath);
        } catch(IOException e){
            throw new DirectoryCreateFailedException();
        }
    }
}
