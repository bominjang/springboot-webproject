package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
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

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending(); //내림차순 정렬
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);//and를 이용한 sort1과 sort2의 연결

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(
                System.out::println
        );
    }

    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(Memo memo : list){
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(System.out::println);
    }

    @Commit//최종 결과를 커밋하기 위해 사용. 이를 적용하지 않으면 deleteBy는 기본적으로 롤백 처리 되어서 결과에 반영되지 않음.
    @Transactional //select문으로 해당 엔티티 객체들을 가져오는 작업과 엔티티를 삭제하는 작업이 같이 이루어지기 때문에 사용.
    @Test
    public void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(100L);
    }

}