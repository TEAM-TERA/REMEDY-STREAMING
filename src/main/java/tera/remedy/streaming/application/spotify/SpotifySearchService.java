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

    private String accessToken;

    public SpotifyTrackInfo search(String songTitle, String artist) {
        String query = buildSearchQuery(songTitle, artist);
        return searchTrackInfo(query);
    }

    private SpotifyTrackInfo searchTrackInfo(String query) {
        ensureAccessTokenExists();

        String url = buildSearchUrl(query);
        HttpGet get = createHttpGetWithAuth(url);

        return getTrackInfo(get);
    }

    private SpotifyTrackInfo getTrackInfo(HttpGet get) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {

            CloseableHttpResponse response = httpClient.execute(get);

            refreshTokenIfUnauthorized(response);

            JsonNode jsonResponse = parseResponseToJson(response);

            return spotifySearchJsonParser.parse(jsonResponse);
        } catch (IOException e) {
            throw new SpotifyApiCallFailedException();
        }
    }

    private String buildSearchQuery(String songTitle, String artist) {
        return "track:" + songTitle + " artist:" + artist;
    }

    private void ensureAccessTokenExists() {
        if (accessToken == null) {
            accessToken = spotifyAccessTokenProvider.getAccessToken();
        }
    }

    private String buildSearchUrl(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return "https://api.spotify.com/v1/search?q=" + encodedQuery + "&type=track&limit=1";
    }

    private HttpGet createHttpGetWithAuth(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "Bearer " + accessToken);
        return get;
    }

    private void refreshTokenIfUnauthorized(CloseableHttpResponse response) {
        if (response.getStatusLine().getStatusCode() == 401) {
            accessToken = spotifyAccessTokenProvider.getAccessToken();
        }
    }

    private JsonNode parseResponseToJson(CloseableHttpResponse response) throws IOException {
        String responseBody = EntityUtils.toString(response.getEntity());
        return objectMapper.readTree(responseBody);
    }
}
