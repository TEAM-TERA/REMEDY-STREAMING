package tera.remedy.streaming.application.dto.response;

public record SongDownloadResponse(
        String id,
        String title,
        String artist,
        int duration,
        String albumImagePath
) {
}