package com.busanit.domain;

import com.busanit.entity.BoardAttach;
import com.busanit.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BoardAttachDTO {
    private Long attachNo;
    private String fileName;
    private String thumbnailName;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    // Entity -> DTO 변환
    public static BoardAttachDTO toDTO(BoardAttach attach) {
        BoardAttachDTO dto = BoardAttachDTO.builder()
                .attachNo(attach.getAttachNo())
                .fileName(attach.getFileName())
                .thumbnailName(attach.getThumbnailName())
                .regDate(attach.getRegDate())
                .updateDate(attach.getUpdateDate())
                .build();

        return dto;
    }
}
