package com.project.resuming.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

   //200 OK (성공)
    GET_SUCCESS(HttpStatus.OK, "성공적으로 조회했습니다."),
    MEMBER_UPDATE_SUCCESS(HttpStatus.OK, "사용자가 성공적으로 수정되었습니다."),
    MEMBER_DELETE_SUCCESS(HttpStatus.OK, "사용자가 성공적으로 삭제되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    RESUME_DELETE_SUCCESS(HttpStatus.OK, "resume 삭제 성공"),


    //201 CREATED (생성 성공)
    MEMBER_SAVE_SUCCESS(HttpStatus.CREATED, "사용자가 성공적으로 생성되었습니다.");

   private final HttpStatus httpStatus;
   private final String message;

    public int getHttpStatusCode() {       // HTTP 상태 코드에서 404와 같은 숫자 값만 반환해 주기 위한 메소드
        return httpStatus.value();
    }

}
