package tera.remedy.streaming.presentation.song;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tera.remedy.streaming.application.song.SongDownloadService;
import tera.remedy.streaming.domain.song.Song;
import tera.remedy.streaming.presentation.song.dto.request.SongDownloadRequest;
import tera.remedy.streaming.presentation.song.dto.response.SongDownloadResponse;

import java.io.IOException;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongDownloadService songDownloadService;

    @PostMapping("/download")
    public ResponseEntity<SongDownloadResponse> downloadSong(@RequestBody SongDownloadRequest request)
            throws IOException, InterruptedException {

        Song song = songDownloadService.songDownload(request.songTitle(), request.artist());

        SongDownloadResponse response = new SongDownloadResponse(
                song.getId(),
                song.getTitle(),
                song.getArtist(),
                song.getDuration(),
                song.getHlsId(),
                song.getAlbumImagePath()
        );

        return ResponseEntity.ok(response);
    }
}
