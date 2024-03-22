package com.busanit.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberRegFormDTO {

    @NotBlank(message = "이름 필수")
    private String name;

    @NotEmpty(message = "이메일 필수")
    @Email(message = "@ 안 넣냐")
    private String email;

    @NotEmpty(message = "비번 필수")
//    @Length(min=8, max = 16, message = "8이상 16이하 국룰")
    private String password;

    private String address;

    private boolean social;
}


/*
    NotEmpty - NULL 체크 및 문자열의 경우 길이 0인지 검사
    NotBlank - NULL 체크 및 문자열의 경우 0및 빈 문자열(" ") 검사
    Length(min=, max=) - 최소, 최대 길이 검사
    Email - 이메일 형식인지 검사
    Max(숫자) - 지정한 값보다 작은지 검사
    Min(숫자) - 지정한 값보다 큰지 검사
    Null - 값이 NULL인지 검사
    NotNull - 값이 NULL이 아닌지 검사
 */