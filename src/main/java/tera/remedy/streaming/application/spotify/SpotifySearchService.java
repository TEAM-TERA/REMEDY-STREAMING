package tera.remedy.streaming.application.spotify;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.dto.SpotifyTrackInfo;
import tera.remedy.streaming.application.spotify.exception.SpotifyApiCallFailedException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SpotifySearchService {
    private final SpotifyAccessTokenProvider spotifyAccessTokenProvider;
    private final SpotifySearchJsonParser spotifySearchJsonParser;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String accessToken = spotifyAccessTokenProvider.getAccessToken();

    public SpotifyTrackInfo search(String songTitle, String artist) {
        String query = buildSearchQuery(songTitle, artist);
        return searchTrackInfo(query);
    }

    private SpotifyTrackInfo searchTrackInfo(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://api.spotify.com/v1/search?q=" + encodedQuery + "&type=track&limit=1";

        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + accessToken);

        return getTrackInfo(get);
    }

    private SpotifyTrackInfo getTrackInfo(HttpGet get) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {

            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 401) {
                accessToken = spotifyAccessTokenProvider.getAccessToken();
            }

            String responseBody = EntityUtils.toString(response.getEntity());
            JsonNode jsonResponse = objectMapper.readTree(responseBody);

            return spotifySearchJsonParser.parse(jsonResponse);
        } catch (IOException e) {
            throw new SpotifyApiCallFailedException();
        }
    }

    private String buildSearchQuery(String songTitle, String artist) {
        return "track:" + songTitle + " artist:" + artist;
    }
}
