package tera.remedy.streaming.repository.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageRepository {
    String uploadFile(MultipartFile file);

    String downloadImage(String imageUrl, String fileName);
}
