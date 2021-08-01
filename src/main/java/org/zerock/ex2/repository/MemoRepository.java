package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;

//MemoRepository는 인터페이스 자체이고 JpaRepository를 상속함으로써 jpa관련 작업을 처리할 수 있음.
public interface MemoRepository extends JpaRepository<Memo, Long> {
    //쿼리 메서드
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    //정렬 형태는 pageable로 처리 가능하기 때문에 좀 더 간단한 형태의 메서드 선언이 가능
   Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    //deleteBy는 한 번에 삭제가 이루어지는 것이 아니라 각 엔티티 객체를 하나씩 삭제함. -> 실제 개발에서는 많이 사용되지 X
    void deleteMemoByMnoLessThan(Long num);

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
}
