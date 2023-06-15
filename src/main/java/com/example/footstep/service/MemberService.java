package com.example.footstep.service;

import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.dto.member.MemberDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.form.MemberForm;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokensGenerator authTokensGenerator;

    @Transactional
    public String memberSignup(MemberForm memberForm) {
        if (isEmailExist(memberForm.getLoginEmail())) {
            throw new GlobalException(ErrorCode.ALREADY_MEMBER_EMAIL);
        }
        Member member = memberForm.from(memberForm);
        String password = passwordEncoder.encode(member.getPassword());
        member.setPassword(password);
        memberRepository.save(member);
        return "회원가입 성공";
    }
    @Transactional
    public AuthTokens login(LoginDto loginDto) {
        Member member = memberRepository.findByLoginEmail(loginDto.getLoginEmail())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            throw new GlobalException(ErrorCode.WRONG_MEMBER_PASSWORD);
        };

        return authTokensGenerator.generate(member.getMemberId());
    }
    public boolean isEmailExist(String email) {
        return memberRepository.findByLoginEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    public MemberDto getMemberWithSareRoom(Long memberId) {

        Member member = memberRepository.findByMemberId(memberId).get();
        return MemberDto.of(member);
    }
}