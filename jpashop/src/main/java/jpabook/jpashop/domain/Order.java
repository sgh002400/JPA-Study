package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //이렇게 명시하지 않으면 order가 되버리는데 이러면 order 메서드랑 혼동될 수 있어서 orders라고 명시함.
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //OneToOne & ManyToOne은 Default가 fetch = FetchType.EAGER인데 즉시로딩( EAGER )은 예측이 어렵고,
    //어떤 SQL이 실행될지 추적하기 어렵다. 특히 JPQL을 실행할 때 N+1문제가 자주 발생한다.
    //연관된 엔티티를 함께 DB에서 조회해야 하면, fetch join 또는 엔티티 그래프 기능을 사용한다.
    @JoinColumn(name = "member_id") //fk가 member_id가 됨
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)//Entity의 상태 변화가 있으면 연관되어 있는 Entity에도 상태 변화를 전이시키는 옵션이다.(참고 : https://www.inflearn.com/questions/56718)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //SpringPhysicalNamingStrategy가 엔티티 필드를 테이블 컬럼으로 변환할 때 아래 규칙에 따라 변환하여 저장한다.
    //1. 카멜 케이스 언더스코어(orderDate -> order_date)
    //2. .(점) -> _(언더스코어)
    //3. 대문자 -> 소문자
    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]


    //연관관계 메서드 -> 아래와 같이 비즈니스 로직에서 사용할텐데 실수할 수 있으니까 두 메서드를 원자적으로 묶어준거임! (양방향일 때 사용)
    //    public static void main(String[] args) {
    //    Member member = new Member();
    //    Order order = new Order();
    //
    //    member.getOrders().add(order);
    //    order.setMember(member);
    //}

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery,
                                    OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//

    /** 주문 취소 */
    public void cancel() {

        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /** 전체 주문 가격 조회 */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
