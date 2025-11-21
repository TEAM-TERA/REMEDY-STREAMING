package tera.remedy.streaming.global.error;

public enum ErrorMessage {
    SPOTIFY_SEARCH_RESULT_NOT_FOUND("검색 결과 입력하신 노래는 스포티파이에 존재하지 않습니다."),
    INVALID_SPOTIFY_TOKEN_REQUEST("스포티파이 Access Token 요청에 실패했습니다."),
    SPOTIFY_API_CALL_FAILED("스포티파이 API 호출 실패!"),

    YOUTUBE_DOWNLOAD_TIMEOUT("유튜브 다운로드 타임아웃 발생!"),
    YOUTUBE_DOWNLOAD_FAILED("유튜브 다운로드 실패!"),

    HLS_CONVERT_FAILED("HLS 변환 실패!"),

    AUDIO_FILE_SAVE_FAILED("오디오 파일 저장 실패!"),
    DIRECTORY_CREATE_FAILED("디렉토리 생성 실패!"),

    SONG_ALREADY_EXISTS("이미 존재하는 노래입니다."),
    ;

    public final String content;

    ErrorMessage(String content) {
        this.content = content;
    }
}
