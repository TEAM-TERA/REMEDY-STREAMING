package tera.remedy.streaming.global.error;

public enum ErrorMessage {
    SPOTIFY_SEARCH_RESULT_NOT_FOUND("검색 결과 입력하신 노래는 스포티파이에 존재하지 않습니다."),
    INVALID_SPOTIFY_TOKEN_REQUEST("스포티파이 Access Token 요청에 실패했습니다."),
    YOUTUBE_DOWNLOAD_TIMEOUT("유튜브 다운로드 타임아웃 발생!"),
    YOUTUBE_DOWNLOAD_FAILED("유튜브 다운로드 실패!"),
    ;

    public final String content;

    ErrorMessage(String content) {
        this.content = content;
    }
}
