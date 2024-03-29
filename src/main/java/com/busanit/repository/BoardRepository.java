package com.busanit.repository;

import com.busanit.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 1. JPA
// find + (엔티티 이름) + By + 변수이름
// findByAll - 전체에 대한 select
// OrderBy + 속성명 + Asc or Desc ex) findByOrderByBnoDesc()

// 2. @Query
// JPQL(Java Persistence Query Language)
// - SQL 문법과 유사

// 3. Querydsl(JPQL을 코드로 작성할 수 있도록 도와주는 빌더 AIP)
/* 장점
    - 고정된 SQL문이 아닌 조건에 맞게 동적으로 쿼리를 생성할 수 있음
    - 비슷한 쿼리를 재사용할 수 있으며 가독성을 향상시킬 수 있음
    - 문자열이 아닌 자바 소스코드로 작성하기 때문에 컴파일 시점에서 오류를 발견할 수 있음
    - IDE의 도움을 받아서 자동 완성 기능을 이용할 수 있기 때문에 생산성을 향상시킬 수 있음
 */

/*
    연관관계
    일대일(1:1): @OneToOne
    일대다(1:N): @OneToMany
    다대일(N:1): @ManyToOne
    다대다(N:M): @ManyToMany

    단방향/양방향
 */
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByOrderByBnoDesc();


    // Like - select... like: title
    // containing - select ... %title%

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
    Page<Board> findByContentContaining(String keyword, Pageable pageable);
}





