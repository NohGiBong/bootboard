package com.busanit.service;

import com.busanit.domain.ReplyDTO;
import com.busanit.entity.Reply;
import com.busanit.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Long register(ReplyDTO replyDTO) {
        Reply reply = Reply.toEntity(replyDTO);

        replyRepository.save(reply);

        log.info("~~reply.getRno = " + reply.getRno());

        return reply.getRno();
    }

    public Slice<ReplyDTO> getReplyList(Long bno, Pageable pageable) {
        Slice<Reply> replyList = replyRepository.findByBoard_Bno(bno, pageable);

        return ReplyDTO.toDTOList(replyList);
    }
}
