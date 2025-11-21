package tera.remedy.streaming.application.song;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.dto.SpotifyTrackInfo;
import tera.remedy.streaming.application.hls.HlsService;
import tera.remedy.streaming.application.song.exception.SongAlreadyExistsException;
import tera.remedy.streaming.application.spotify.SpotifySearchService;
import tera.remedy.streaming.application.youtube.YoutubeDownloadService;
import tera.remedy.streaming.domain.song.Song;
import tera.remedy.streaming.repository.song.SongCommandRepository;
import tera.remedy.streaming.repository.song.SongQueryRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SongDownloadService {
    private final SpotifySearchService spotifySearchService;
    private final SongCommandRepository songCommandRepository;
    private final YoutubeDownloadService youtubeDownloadService;
    private final HlsService hlsService;
    private final SongQueryRepository songQueryRepository;

    public Song songDownload(String songTitle, String artist)
        throws IOException, InterruptedException {
        if(songQueryRepository.existsByArtistAndTitle(artist, songTitle)) {
            throw new SongAlreadyExistsException();
        }

        SpotifyTrackInfo trackInfo = spotifySearchService.search(songTitle, artist);

        int duration = youtubeDownloadService.getDuration(songTitle, artist);
        String audioPath = youtubeDownloadService.downloadAudio(songTitle, artist);
        String hlsId = hlsService.convertToHls(audioPath);

        Song song = new Song(
                trackInfo.title(),
                trackInfo.artist(),
                duration,
                hlsId,
                trackInfo.albumImageUrl()
        );

        return songCommandRepository.save(song);
    }
}
