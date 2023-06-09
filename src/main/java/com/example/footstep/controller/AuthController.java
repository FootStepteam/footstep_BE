package com.example.footstep.controller;

import com.example.footstep.authentication.oauth.kakao.KakaoLoginParams;
import com.example.footstep.component.jwt.JwtTokenProvider;
import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.model.dto.TokenValidationResponseDto;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import com.example.footstep.service.KakaoService;
import com.example.footstep.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final KakaoService kakaoService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;


    // 로그아웃 리다이렉트 링크
    @PostMapping("/auth/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams kakaoAccessCode) {

        AuthTokens authTokens = kakaoService.login(kakaoAccessCode);
        tokenService.saveRefreshToken(authTokens.getRefreshToken(), authTokens.getJwtAccessToken());

        return ResponseEntity.ok(authTokens);
    }


    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {

        return ResponseEntity.ok(code);
    }


    @PostMapping("/kakao/unlink")
    public ResponseEntity<String> kakaoUnlink(@RequestBody KakaoLoginParams kakaoAccessCode) {

        kakaoService.kakaoUnlink(kakaoAccessCode);

        return ResponseEntity.ok("회원 탈퇴 성공");
    }


    @GetMapping("/kakao/logout") // 확인용 추후 삭제될 코드
    public ResponseEntity<String> kakaoLogout() {

        return ResponseEntity.ok("로그아웃");
    }


    @PostMapping("/auth/refresh")
    public String reIssue(@CookieValue("refresh-token") String refreshToken) {

        return tokenService.reIssueAccessToken(refreshToken);
    }


    @PostMapping("/auth/check-token")
    public ResponseEntity<TokenValidationResponseDto> checkAccessToken(
        @RequestHeader("Authorization") String authorizationHeader) {

        validateHeader(authorizationHeader);

        String accessToken = extractAccessToken(authorizationHeader);

        if (ObjectUtils.isEmpty(accessToken)) {
            throw new GlobalException(ErrorCode.WRONG_ACCESS_TOKEN_AUTH);
        }

        try {
            jwtTokenProvider.validate(accessToken);
            return ResponseEntity.ok(new TokenValidationResponseDto(Boolean.TRUE)
            );
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(new TokenValidationResponseDto(Boolean.FALSE)
            );
        } catch (JwtException e) {
            throw new GlobalException(ErrorCode.JWT_EXCEPTION);
        }

    }


    private String extractAccessToken(String authorizationHeader) {

        return authorizationHeader.replace("Bearer ", "");
    }


    private void validateHeader(String authorizationHeader) {

        if (ObjectUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer")) {
            throw new GlobalException(ErrorCode.WRONG_AUTHORIZATION_HEADER);
        }
    }
}
