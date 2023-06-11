package com.example.footstep.domain.dto;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String loginEmail;

    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String password;
}