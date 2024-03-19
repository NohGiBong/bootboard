package com.busanit.domain;

import com.busanit.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReplyDTO {
    private Long rno;
    private String text;
    private String replyer;
    private Long bno;       // 게시글 번호
    private LocalDateTime regDate, modDate;

    // Entity -> DTO 변환
    public static ReplyDTO toDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getUpdateDate())
                .build();

       return dto;
    }

    // Slice<Entity> -> Slice<dto> 변환처리
    public static Slice<ReplyDTO> toDTOList(Slice<Reply> replyList) {
        Slice<ReplyDTO> replyDTOList = replyList.map(entity -> ReplyDTO.builder()
                .rno(entity.getRno())
                .text(entity.getText())
                .replyer(entity.getReplyer())
                .regDate(entity.getRegDate())
                .modDate(entity.getUpdateDate())
                .build());
        return replyDTOList;
    }
}








