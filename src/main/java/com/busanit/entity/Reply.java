package com.busanit.entity;

import com.busanit.domain.ReplyDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String text;
    private String replyer;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @JsonIgnore         // JSON 형태로 응답을 할 때 해당 필드는 제외하고 응답함
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    // DTO -> Entity 변환
    public static Reply toEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .regDate(replyDTO.getRegDate())
                .updateDate(replyDTO.getModDate())
                .board(board)
                .build();

        return reply;
    }
}






