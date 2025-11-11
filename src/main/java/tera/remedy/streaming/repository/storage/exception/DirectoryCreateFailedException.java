package tera.remedy.streaming.repository.storage.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class DirectoryCreateFailedException extends BusinessBaseException {
    public DirectoryCreateFailedException() {
        super(ErrorMessage.DIRECTORY_CREATE_FAILED);
    }
}