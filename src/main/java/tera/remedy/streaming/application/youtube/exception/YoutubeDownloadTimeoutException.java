package tera.remedy.streaming.application.youtube.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class YoutubeDownloadTimeoutException extends BusinessBaseException {
    public YoutubeDownloadTimeoutException() {
        super(ErrorMessage.YOUTUBE_DOWNLOAD_TIMEOUT);
    }
}
