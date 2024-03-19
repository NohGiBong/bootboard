package com.busanit.controller;


import com.busanit.domain.BoardDTO;
import com.busanit.repository.BoardRepository;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final BoardRepository boardRepository;

//    @GetMapping("/list")
//    public String list(Model model,
//                       @PageableDefault(size = 5, sort = "bno",
//                            direction = Sort.Direction.DESC)Pageable pageable) {
//
//        List<BoardDTO> boardList = boardService.getBoardDTOList();
//        model.addAttribute("list", boardList);
//
//        return "board/list";
//    }

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
        model.addAttribute("startPage", startPage);

        int endPage = Math.min(boardDTOList.getTotalPages(), boardDTOList.getPageable().getPageNumber() + 5);
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
    @PostMapping("register")
    public String register(@Valid BoardDTO boardDTO, BindingResult result) {

        if(result.hasErrors()) {
            return "board/register";
        }

        boardService.register(boardDTO);

        return "redirect:/board/list";
    }

    @GetMapping("/view")
    public String view(Long bno, Model model) {

        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("board", boardDTO);

        return "board/view";
    }

    @GetMapping("/update")
    public String update(Long bno, Model model) {

        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("board", boardDTO);

        return "board/update";
    }

    @PostMapping("/update")
    public String update(BoardDTO boardDTO) {

        boardService.update(boardDTO);

        return "redirect:/board/view?bno=" + boardDTO.getBno();
//        return "redirect:/board/view/" + boardDTO.getBno();
    }

    @PostMapping("/delete")
    public String delete(Long bno) {

        boardService.delete(bno);

        return "redirect:/board/list";
    }
}











