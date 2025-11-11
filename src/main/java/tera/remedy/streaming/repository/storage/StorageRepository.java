package tera.remedy.streaming.repository.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageRepository {

    // 나중에 사용자가 자기 음악 업로드 하는 기능 추가 할 때 사용
    //String uploadFile(MultipartFile file);

    String downloadImage(String imageUrl, String fileName);

    String saveDownloadAudio(Path tempFilePath, String fileName);
}
