package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Item table에 Book, Movie, Album의 속성들을 모두 넣어서 생성해줌
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { //추상 클래스의 목적은 상속을 받아서 기능을 확장시키는 것이다.

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
