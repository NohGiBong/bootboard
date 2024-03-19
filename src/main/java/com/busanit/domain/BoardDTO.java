package com.busanit.domain;

import com.busanit.entity.Board;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long bno;
    @NotEmpty(message = "제목 넣어")
    private String title;
    private String content;
    @NotEmpty(message = "작성자 넣어")
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    // Entity -> DTO
    public static BoardDTO toDTO(Board board) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .updateDate(board.getUpdateDate())
                .build();
    }

    // Page<Entity> -> Page<Dto> 변환처리
    public static Page<BoardDTO> toDTOList(Page<Board> boardList) {
        Page<BoardDTO> boardDTOList = boardList.map(entity-> BoardDTO.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .build());
        return  boardDTOList;
    }
}
