package org.zerock.ex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex2.entity.Memo;

//MemoRepository는 인터페이스 자체이고 JpaRepository를 상속함으로써 jpa관련 작업을 처리할 수 있음.
public interface MemoRepository extends JpaRepository<Memo, Long> {

}
