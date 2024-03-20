package com.busanit.service;

import com.busanit.domain.BoardAttachDTO;
import com.busanit.domain.BoardDTO;
import com.busanit.entity.Board;
import com.busanit.entity.BoardAttach;
import com.busanit.repository.BoardAttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardAttachService {
    private final BoardAttachRepository boardAttachRepository;

    public void saveWithFiles(BoardDTO boardDTO, Board board) {

        if(boardDTO.getFileNames() != null && boardDTO.getFileNames().size() > 0) {
            for(int i = 0; i < boardDTO.getFileNames().size(); i++) {
                BoardAttach boardAttach = new BoardAttach();
                boardAttach.setFileName(boardDTO.getFileNames().get(i));
                boardAttach.setThumbnailName(boardDTO.getThumbnailNames().get(i));
                boardAttach.setBoard(board);

                boardAttachRepository.save(boardAttach);
            }
        }
    }

    public List<BoardAttachDTO> getBoardAttachList(Long bno) {
        List<BoardAttach> attachList = boardAttachRepository.findByBoard_Bno(bno);
        return attachList.stream().map(attach -> BoardAttachDTO.toDTO(attach))
                                    .collect(Collectors.toList());
    }
}









