package tera.remedy.streaming.application.spotify;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.dto.SpotifyTrackInfo;
import tera.remedy.streaming.application.spotify.exception.SpotifySearchResultNotFoundException;

@Service
public class SpotifySearchJsonParser {
    public SpotifyTrackInfo parse(JsonNode jsonResponse) {
        JsonNode tracks = jsonResponse.path("tracks").path("items");

        if (!tracks.isEmpty()) {
            JsonNode track = tracks.get(0);
            JsonNode album = track.path("album");

            String albumImageUrl = album.path("images").get(0).path("url").asText();
            String artist = track.path("artists").get(0).path("name").asText();
            String title = track.path("name").asText();

            return new SpotifyTrackInfo(title, albumImageUrl, artist);
        }
        throw new SpotifySearchResultNotFoundException();
    }
}
