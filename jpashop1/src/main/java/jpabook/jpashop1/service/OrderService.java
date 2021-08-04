package jpabook.jpashop1.service;

import jpabook.jpashop1.domain.*;
import jpabook.jpashop1.domain.item.Item;
import jpabook.jpashop1.repository.ItemRepository;
import jpabook.jpashop1.repository.MemberRepository;
import jpabook.jpashop1.repository.OrderRepository;
import jpabook.jpashop1.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); //간단히 하기 위해 그냥 회원가입시 작성한 주소를 사용
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        /** new OrderItem해서 set을 통해 값들을 추가할 수도 있는데 형식을 통일하지 않으면 유지 보수 측면에서 좋지 않다 그래서 protected 생성자를 통해 이를 막아준다! **/

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); //Cascade 옵션 덕분에 이렇게 하나만 해줘도 OrderItem과 Delivery가 자동으로 persist 된다!

        return order.getId();
    }

    /** 주문 취소 **/
    @Transactional
    public void cancelOrder(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
        //SQL을 직접 쓴다면 주문이 취소 됐으므로 재고를 업데이트하는 쿼리를 직접 날려줘야 하는데 JPA는 바뀐 변경 포인트들을 더티 체킹(변경 내역 감지)하여 DB에 알아서 Update 쿼리를 날려줌!!
    }

    /** 주문 검색 **/
     public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
     }

}
