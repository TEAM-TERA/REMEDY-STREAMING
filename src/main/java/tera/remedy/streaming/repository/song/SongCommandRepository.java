package tera.remedy.streaming.repository.song;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import tera.remedy.streaming.domain.song.Song;

@Repository
public interface SongCommandRepository extends ElasticsearchRepository<Song, String> {
}
