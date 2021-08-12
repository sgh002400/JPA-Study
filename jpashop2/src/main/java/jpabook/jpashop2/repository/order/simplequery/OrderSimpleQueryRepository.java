package jpabook.jpashop2.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() { //이 메서드는 Dto 스펙에 맞게(화면에 맞게) 엔티티에서 속성을 뽑아오기 때문에 Repository가 아닌 다른 곳으로 뺐다.
        //유지 보수할 때 편리하게 하기 위함!
        return em.createQuery(
                "select new jpabook.jpashop2.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" + " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}