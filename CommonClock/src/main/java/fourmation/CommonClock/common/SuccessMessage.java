package fourmation.CommonClock.common;

public enum SuccessMessage {
    CREATE("생성 성공"),
    GET("조회 성공"),
    UPDATE("수정 성공"),
    DELETE("삭제 성공");
    private final String message;

    SuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
