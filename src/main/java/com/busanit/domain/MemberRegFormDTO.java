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
    @Length(min=8, max = 16, message = "8이상 16이하 국룰")
    private String password;

    private String address;

    private boolean social;
}


/*
    NotEmpty - NULL 체크 및 문자열의 경우 길이 0인지 검사
    NotBlank - NULL 체크 및 문자열의 경우 0및 빈 문자열(" ") 검사
    
 */