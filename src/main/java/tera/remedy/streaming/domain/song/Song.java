package tera.remedy.streaming.domain.song;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "songs")
@Getter
public class Song {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String title;        // 노래 제목

    @Field(type = FieldType.Text, analyzer = "nori")
    private String artist;       // 아티스트

    @Field(type = FieldType.Integer)
    private int duration;    // 재생 시간 (초)

    @Field(type = FieldType.Keyword)
    private String hlsPath;      // HLS 플레이리스트 경로

    @Field(type = FieldType.Keyword)
    private String albumImagePath;  // 앨범 이미지 경로

    public Song(String title, String artist, int duration, String hlsPath, String albumImagePath) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.hlsPath = hlsPath;
        this.albumImagePath = albumImagePath;
    }
}
