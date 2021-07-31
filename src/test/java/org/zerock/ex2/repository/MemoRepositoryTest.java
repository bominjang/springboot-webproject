package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
class MemoRepositoryTest {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        //memoRepository 인터페이스 타입의 실제 객체를 확인
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        //db에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("===================================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }

    }

    @Transactional
    @Test
    public void testSelect2(){
        //db에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("===================================");

        System.out.println(memo);
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("Update Text")
                .build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete(){
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
        //1페이지 10개
        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result  = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("--------------------------------------");

        System.out.println("Total Pages: "+result.getTotalPages());//총 몇 페이지인지

        System.out.println("Total Count: "+result.getTotalElements());//전체 개수

        System.out.println("Page Number: "+result.getNumber());//현재 페이지 번호

        System.out.println("Page Size: "+result.getSize());//페이지당 데이터 개수

        System.out.println("has next page?: "+result.hasNext());//다음 페이지 존재 여부

        System.out.println("First page?: "+result.isFirst());//시작페이지(0) 여부

        System.out.println("--------------------------------------");

        //getContent() 반환형 : List<엔티티타입>
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }

        System.out.println("--------------------------------------");

        //get() 반환형 : Stream<엔티티타입>
        result.get().forEach(System.out::println);

    }

}