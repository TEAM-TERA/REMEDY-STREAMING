package tera.remedy.streaming.presentation.song.dto.request;

public record SongDownloadRequest(
        String songTitle,
        String artist
) {
}