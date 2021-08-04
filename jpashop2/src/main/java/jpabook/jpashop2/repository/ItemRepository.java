package jpabook.jpashop2.repository;

import jpabook.jpashop2.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { //DB에 처음 등록되는 Item은 id가 없으므르 신규 등록하는거임.
            em.persist(item);
        } else { //DB에 등록되어 있던걸 update 한다고 일단 생각하기.
            em.merge(item);
            //merge란? -> itemService의 updateItem()와 동일
            //DB에서 item을 찾는다. 그리고 파라미터로 넘어온 값으로 찾아온 item의 값을 모두 바꿔버린다.
            //그래서 commit이 일어나면 알아서 JPA가 update 쿼리 날려줌.
            //중요!! -> em.merge(item) 여기서 item은 준영속 상태이고 만약 Item merge = em.merge(item) 했을 때 merge가 영속 상태이다. 두 개는 다른거임!!
            //주의!! -> merge를 사용하면 모든 속성이 변경된다. 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (merge는 모든 필드를 교체한다.)
            //그렇기 때문에 변경 감지 기능을 사용하는게 좋다!
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
