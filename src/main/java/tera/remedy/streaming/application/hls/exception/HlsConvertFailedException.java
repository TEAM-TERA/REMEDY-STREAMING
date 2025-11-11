package tera.remedy.streaming.application.hls.exception;

import tera.remedy.streaming.global.error.BusinessBaseException;
import tera.remedy.streaming.global.error.ErrorMessage;

public class HlsConvertFailedException extends BusinessBaseException {
    public HlsConvertFailedException() {
        super(ErrorMessage.HLS_CONVERT_FAILED);
    }
}