package tera.remedy.streaming.repository.song;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tera.remedy.streaming.domain.song.Song;

public interface SongQueryRepository extends ElasticsearchRepository<Song, String> {
    boolean existsByArtistAndTitle(String artist, String title);
}
