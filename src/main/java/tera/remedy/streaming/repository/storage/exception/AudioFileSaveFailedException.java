package tera.remedy.streaming.repository.storage.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class AudioFileSaveFailedException extends BusinessBaseException {
    public AudioFileSaveFailedException() {
        super(ErrorMessage.AUDIO_FILE_SAVE_FAILED);
    }
}