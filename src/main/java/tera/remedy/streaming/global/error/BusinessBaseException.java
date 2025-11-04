package tera.remedy.streaming.global.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessBaseException extends RuntimeException {
    public BusinessBaseException(ErrorMessage errorMessage) {
        super(errorMessage.content);
        log.error(errorMessage.content);
    }
}
