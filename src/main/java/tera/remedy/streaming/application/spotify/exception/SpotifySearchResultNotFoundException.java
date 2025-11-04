package tera.remedy.streaming.application.spotify.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class SpotifySearchResultNotFoundException extends BusinessBaseException {
    public SpotifySearchResultNotFoundException() {
        super(ErrorMessage.SPOTIFY_SEARCH_RESULT_NOT_FOUND);
    }
}
