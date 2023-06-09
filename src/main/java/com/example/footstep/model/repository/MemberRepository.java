package com.example.footstep.model.repository;


import com.example.footstep.model.entity.Member;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByMemberId(Long memberId);

    default Member getMemberById(Long memberId) {
        return findByMemberId(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));
    }
}
