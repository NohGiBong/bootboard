package com.busanit.repository;

import com.busanit.entity.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Slice<Reply> findByBoard_Bno(Long bno, Pageable pageable);
}
