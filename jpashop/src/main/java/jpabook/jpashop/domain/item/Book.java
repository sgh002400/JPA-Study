package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //default는 클래스명임
@Getter @Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
