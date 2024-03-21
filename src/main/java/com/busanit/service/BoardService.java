package com.busanit.service;

import com.busanit.domain.BoardDTO;
import com.busanit.entity.Board;
import com.busanit.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    @Value("${com.busanit.upload.path}")    // application.properties의 변수
    private String uploadPath;

    private final BoardRepository boardRepository;

    public List<Board> getBoardList() {
        return boardRepository.findAllByOrderByBnoDesc();
    }

    public List<BoardDTO> getBoardDTOList() {
        List<Board> result = boardRepository.findAllByOrderByBnoDesc();
        return result.stream().map(board -> BoardDTO.toDTO(board))
                                    .collect(Collectors.toList());
    }

    public Page<BoardDTO> getBoardPageList(Pageable pageable) {

        Page<Board> boardList = boardRepository.findAll(pageable);

        // Page<Entity> -> Page<Dto> 변환
        return BoardDTO.toDTOList(boardList);
    }

    public Page<BoardDTO> getBoardTitlePageList(String keyword, Pageable pageable) {

        Page<Board> boardList = boardRepository.findByTitleContaining(keyword, pageable);

        // Page<Entity> -> Page<Dto> 변환
        return BoardDTO.toDTOList(boardList);
    }

    public Page<BoardDTO> getBoardContentPageList(String keyword, Pageable pageable) {

        Page<Board> boardList = boardRepository.findByContentContaining(keyword, pageable);

        // Page<Entity> -> Page<Dto> 변환
        return BoardDTO.toDTOList(boardList);
    }

    public Board register(BoardDTO boardDTO) {
        Board board = Board.toEntity(boardDTO);

        boardRepository.save(board);

        return board;
    }

    public BoardDTO get(Long bno) {

        Board board = boardRepository.findById(bno).orElseThrow(() -> new NullPointerException("board null!!"));
        return BoardDTO.toDTO(board);
    }

    public void update(BoardDTO boardDTO) {

        boardRepository.save(Board.toEntity(boardDTO));
    }

    public void delete(BoardDTO boardDTO) {

        boardRepository.deleteById(boardDTO.getBno());

        // 첨부파일 물리파일 삭제
        if(boardDTO.getFileNames() != null) {
            for(int i = 0; i < boardDTO.getFileNames().size(); i++) {
                removeFile(boardDTO.getFileNames().get(i));
            }
        }
    }

    // 물리파일 삭제
    private void removeFile(String fileName) {
        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(fileName, "UTF-8");

            // 원본 파일 삭제
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            // 썸네일 삭제
            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}






