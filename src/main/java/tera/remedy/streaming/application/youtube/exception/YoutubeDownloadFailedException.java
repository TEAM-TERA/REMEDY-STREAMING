package tera.remedy.streaming.application.youtube.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class YoutubeDownloadFailedException extends BusinessBaseException {
    public YoutubeDownloadFailedException() {
        super(ErrorMessage.YOUTUBE_DOWNLOAD_FAILED);
    }
}
