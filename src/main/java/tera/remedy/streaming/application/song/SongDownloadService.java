package tera.remedy.streaming.application.song;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.dto.SpotifyTrackInfo;
import tera.remedy.streaming.application.spotify.SpotifySearchService;
import tera.remedy.streaming.application.youtube.YoutubeDownloadService;
import tera.remedy.streaming.domain.song.Song;
import tera.remedy.streaming.repository.song.SongCommandRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SongDownloadService {
    private final SpotifySearchService spotifySearchService;
    private final SongCommandRepository songCommandRepository;
    private final YoutubeDownloadService youtubeDownloadService;

    public Song songDownload(String songTitle, String artist, String youtubeVideoId)
        throws IOException, InterruptedException {

        SpotifyTrackInfo trackInfo = spotifySearchService.search(songTitle, artist);

        String audioPath = youtubeDownloadService.downloadAudio(youtubeVideoId, songTitle);

        Song song = new Song(
                trackInfo.title(),
                trackInfo.artist(),
                0,
                null,
                trackInfo.albumImageUrl()
        );

        return songCommandRepository.save(song);
    }

}
