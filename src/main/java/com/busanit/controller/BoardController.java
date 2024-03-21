package com.busanit.controller;

import com.busanit.domain.BoardAttachDTO;
import com.busanit.domain.BoardDTO;
import com.busanit.entity.Board;
import com.busanit.service.BoardAttachService;
import com.busanit.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardAttachService boardAttachService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "") String searchType,
                       @RequestParam(defaultValue = "") String keyword,
                       @PageableDefault(size = 5, sort = "bno",
                               direction = Sort.Direction.DESC)Pageable pageable) {
        Page<BoardDTO> boardDTOList = null;

        if(searchType.equals("title")) {
            boardDTOList = boardService.getBoardTitlePageList(keyword, pageable);
        } else if(searchType.equals("content")) {
            boardDTOList = boardService.getBoardContentPageList(keyword, pageable);
        } else {
            boardDTOList = boardService.getBoardPageList(pageable);
        }

        model.addAttribute("list", boardDTOList);

        int startPage = Math.max(1, boardDTOList.getPageable().getPageNumber() - 5);
        int endPage = Math.min(boardDTOList.getTotalPages(), boardDTOList.getPageable().getPageNumber() + 5);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "board/list";
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("boardDTO", new BoardDTO());

        return "board/register";
    }

    // WebDataBinder
    // 1. 타입 변환
    // 2. 데이터 검증
    // 결과 - BindingResult
    @PostMapping("/register")
    public String register(@Valid BoardDTO boardDTO, BindingResult result) {

        if(result.hasErrors()) {
            return "board/register";
        }

        // 첨부파일 있을 경우 DB에 첨부파일 정보 저장
        Board board = boardService.register(boardDTO);
        boardAttachService.saveWithFiles(boardDTO, board);

        return "redirect:/board/list";
    }

    @GetMapping("/view")
    public String view(Long bno, Model model) {

        BoardDTO boardDTO = boardService.get(bno);
        List<BoardAttachDTO> attachDTOList = boardAttachService.getBoardAttachList(bno);

        model.addAttribute("board", boardDTO);
        model.addAttribute("attachList", attachDTOList);

        return "board/view";
    }

    @GetMapping("/update")
    public String update(Long bno, Model model) {

        BoardDTO boardDTO = boardService.get(bno);
        List<BoardAttachDTO> attachDTOList = boardAttachService.getBoardAttachList(bno);

        model.addAttribute("board", boardDTO);
        model.addAttribute("attachList", attachDTOList);

        return "board/update";
    }

    @PostMapping("/update")
    public String update(BoardDTO boardDTO) {

        boardService.update(boardDTO);

        // 첨부파일 있을 경우 DB에 첨부파일 정보 저장
        Board board = boardService.register(boardDTO);
        boardAttachService.modifyWithFiles(boardDTO, board);

        return "redirect:/board/view?bno=" + boardDTO.getBno();
//        return "redirect:/board/view/" + boardDTO.getBno();
    }

    @PostMapping("/delete")
//    public String delete(Long bno) {
    public String delete(BoardDTO boardDTO) {

        boardService.delete(boardDTO);

        return "redirect:/board/list";
    }
}











