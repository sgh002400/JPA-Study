package jpabook.jpashop1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //Default : ORDINAL(enum 순서 값을 DB에 저장) -> READY와 COMP 사이에 값이 들어가면 망함.
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]
}
