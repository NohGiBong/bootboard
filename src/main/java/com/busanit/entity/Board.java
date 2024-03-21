package com.busanit.entity;

import com.busanit.domain.BoardDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(nullable = false)
    private String writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    List<Reply> replyList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    List<BoardAttach> boardAttachList;


    // DTO -> Entity
    public static Board toEntity(BoardDTO dto) {
        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }
}
