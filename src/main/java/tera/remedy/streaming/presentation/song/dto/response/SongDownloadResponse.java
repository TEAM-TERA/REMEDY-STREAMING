package tera.remedy.streaming.presentation.song.dto.response;

public record SongDownloadResponse(
        String id,
        String title,
        String artist,
        int duration,
        String hlsPath,
        String albumImagePath
) {
}