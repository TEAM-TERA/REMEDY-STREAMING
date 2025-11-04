package tera.remedy.streaming.application.song;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.spotify.SpotifySearchService;
import tera.remedy.streaming.repository.song.SongCommandRepository;

@Service
@RequiredArgsConstructor
public class SongDownloadService {
    private final SpotifySearchService spotifySearchService;
    private final SongCommandRepository songCommandRepository;
}
