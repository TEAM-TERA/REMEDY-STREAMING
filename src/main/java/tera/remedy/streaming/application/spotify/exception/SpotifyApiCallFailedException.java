package tera.remedy.streaming.application.spotify.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class SpotifyApiCallFailedException extends BusinessBaseException {
    public SpotifyApiCallFailedException() {
        super(ErrorMessage.SPOTIFY_API_CALL_FAILED);
    }
}