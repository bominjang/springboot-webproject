package org.zerock.ex2.entity;

import lombok.*;

import javax.persistence.*;

@Entity //해당 클래스의 인스턴스들이 JPA로 관리되는 엔티티 객체임을 의미.
@Table(name="tbl_memo")//db에 entity 클래스를 어떠한 테이블로 생성할 것인지에 대한 정보를 담기 위함.
@ToString
@Getter //메서드 생성
@Builder //객체성 생성할 수 있게 처리
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    @Id//PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
