package com.example.footstep.model.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseTimeEntity.class)
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String communityName;
    @Column(columnDefinition = "TEXT NOT NULL")
    private String content;
    @Column(columnDefinition = "BOOLEAN NOT NULL")
    private boolean communityPublicState;
    private int likeCount;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shareId")
    private ShareRoom shareRoom;


    public void update(String content, String communityName) {
        this.communityName = communityName;
        this.content = content;
    }

    public boolean isWrittenBy(Long memberId) {

        return member.getMemberId().equals(memberId);
    }
}
