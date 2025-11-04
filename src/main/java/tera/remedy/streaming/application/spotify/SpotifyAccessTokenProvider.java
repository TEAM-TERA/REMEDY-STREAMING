package tera.remedy.streaming.application.spotify;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tera.remedy.streaming.application.spotify.exception.InvalidSpotifyTokenRequestException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class SpotifyAccessTokenProvider {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    public String getAccessToken() {
        HttpPost request = createTokenRequest();

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return extractAccessToken(response);
        } catch (Exception e) {
            throw new InvalidSpotifyTokenRequestException();
        }
    }

    private HttpPost createTokenRequest() {
        HttpPost post = new HttpPost("https://accounts.spotify.com/api/token");

        post.setHeader("Authorization", "Basic " + encodeCredentials(clientId, clientSecret));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = List.of(
                new BasicNameValuePair("grant_type", "client_credentials")
        );
        post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        return post;
    }

    private String extractAccessToken(CloseableHttpResponse response) throws IOException {
        String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JsonNode json = objectMapper.readTree(responseBody);

        return json.get("access_token").asText();
    }

    private String encodeCredentials(String clientId, String clientSecret) {
        String credentials = clientId + ":" + clientSecret;
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
