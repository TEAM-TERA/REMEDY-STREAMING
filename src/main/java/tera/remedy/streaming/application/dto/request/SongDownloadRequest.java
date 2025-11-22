package tera.remedy.streaming.application.dto.request;

public record SongDownloadRequest(
        String songTitle,
        String artist
) {
}