package tera.remedy.streaming.application.song.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class SongAlreadyExistsException extends BusinessBaseException {
    public SongAlreadyExistsException() {
        super(ErrorMessage.SONG_ALREADY_EXISTS);
    }
}
