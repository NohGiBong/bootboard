package com.busanit.service;

import com.busanit.domain.BoardDTO;
import com.busanit.entity.Board;
import com.busanit.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
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

    public void delete(Long bno) {
//        return boardRepository.findById(bno).orElseThrow(() -> new NullPointerException("Board not found with bno: " + bno));
        boardRepository.deleteById(bno);
    }
}






