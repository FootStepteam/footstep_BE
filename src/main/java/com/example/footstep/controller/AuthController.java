package com.example.footstep.controller;

import com.example.footstep.authentication.oauth.kakao.KakaoLoginParams;
import com.example.footstep.component.jwt.AuthTokens;
import com.example.footstep.service.impl.KakaoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final KakaoServiceImpl oAuthLoginService;

    //https://kauth.kakao.com/oauth/authorize?client_id=361fc4d12b75888a392207252d5db496&redirect_uri=http://localhost:8080/api/kakao/callback&response_type=code

    @PostMapping("/auth/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        return ResponseEntity.ok(code);
    }
}