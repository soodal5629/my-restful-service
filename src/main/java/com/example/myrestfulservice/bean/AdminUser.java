package com.example.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserInfo")
public class AdminUser {
    private Integer id;
    @Size(min = 2, message = "name은 2글자 이상 입력해 주세요.")
    private String name;
    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.") // 과거날짜만 가능
    private LocalDateTime joinDate;
    //@JsonIgnore
    private String password;
    //@JsonIgnore
    private String ssn;
}
