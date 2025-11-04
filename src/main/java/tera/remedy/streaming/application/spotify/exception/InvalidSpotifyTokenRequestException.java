package tera.remedy.streaming.application.spotify.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class InvalidSpotifyTokenRequestException extends BusinessBaseException {
    public InvalidSpotifyTokenRequestException() {
        super(ErrorMessage.INVALID_SPOTIFY_TOKEN_REQUEST);
    }
}
