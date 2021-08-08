package jpabook.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
    //컬렉션은 필드에서 바로 초기화 하는 것이 좋다! -> null 문제에서 안전하다. / orders 컬렉션은 절대 변경하지 말것! -> 하이버네이트가 원하는대로 동작하지 않을 수 있다.
}
