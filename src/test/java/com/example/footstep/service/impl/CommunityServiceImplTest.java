package com.example.footstep.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.footstep.domain.dto.community.CommunityDetailDto;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.repository.CommunityRepository;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.service.CommunityService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CommunityServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    ShareRoomRepository shareRoomRepository;

    @Autowired
    CommunityService communityService;


    @Transactional
    @Test
    void 게시글_단건_조회() {
        // given
        Member member = Member.builder()
            .memberId(1L)
            .gender("m")
            .nickname("닉네임")
            .password("password")
            .loginEmail("test@naver.com")
            .build();

        memberRepository.save(member);

        ShareRoom shareRoom = ShareRoom.builder()
            .shareId(1L)
            .shareName("공유방 제목")
            .shareCode("랜던코드")
            .member(member)
            .build();

        shareRoomRepository.save(shareRoom);

        Community community = Community.builder()
            .communityId(1L)
            .communityName("제목")
            .content("내용")
            .communityPublicState(false)
            .member(member)
            .shareRoom(shareRoom)
            .build();
        LocalDateTime now = LocalDateTime.now();
        community.setCreateDate(now);

        communityRepository.save(community);

        // when
        CommunityDetailDto response = communityService.getOne(1L);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCommunityName()).isEqualTo(community.getCommunityName());
        assertThat(response.getMemberNickname()).isEqualTo(member.getNickname());
        assertThat(response.getLikeCount()).isEqualTo(community.getLikeCount());
        assertThat(response.getContent()).isEqualTo(community.getContent());

    }

}