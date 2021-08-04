package jpabook.jpashop1.domain.item;

import jpabook.jpashop1.domain.Category;
import jpabook.jpashop1.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //==비즈니스 로직==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
