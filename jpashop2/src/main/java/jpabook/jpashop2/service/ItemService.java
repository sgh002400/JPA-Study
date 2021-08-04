package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional //Transaction이 commit이 되면서 JPA는 flush를 날리는데 이때 영속성 컨택스트에 있는 것들 중 변경된 것들을 모두 찾아서 update를 알아서 해준다(변경감지!)
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {

        Item findItem = itemRepository.findOne(itemId); //실제 DB에 있는 영속 상태 엔티티를 가져온다.
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setPrice(stockQuantity);
        //save할 필요 없음! -> 영속 상태이므로 JPA가 알아서 update 쿼리 날려준다.
        //그리고 이렇게 하나 하나 set~ 하는건 단연 좋지 않다! -> change~() 이런식으로 메서드로 변경하는게 좋다.

    }
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}