package jpabook.jpashop1.controller;

import jpabook.jpashop1.domain.item.Book;
import jpabook.jpashop1.domain.item.Item;
import jpabook.jpashop1.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {

        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(BookForm form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    /** 상품 목록 **/
    @GetMapping(value = "/items")
    public String list(Model model) {

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Book item = (Book) itemService.findOne(itemId); //예제를 간단히 하기 위해 Book으로 함
        BookForm form = new BookForm();

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    //궁금증 -> 그럼 여기가 준영속 상태인 것은 db에서 findOne으로 객체를 넣어준게 아니라 단지 new를 통해 form의 값들을 넣어줘서 그런거?
    //db에 한번 저장된건 무조건 준영속이 아니라?

    /** 상품 수정 **/
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable long itemId, @ModelAttribute("form") BookForm form) { //@ModelAttribute -> 뷰 페이지에서 form이라는 이름으로 값을 가져올 수 있게 해준다.

        //이 유저가 이 아이템에 대해서 수정할 권한이 있는지 체크해줘야 악의적으로 수정하는 것을 막을 수 있다.

        /**
        Book book = new Book();
        book.setId(form.getId()); //id가 있다는건 JPA를 통해 DB에 저장이 되었다는 뜻! -> 이런걸 준영속 엔티티라고 한다!
        //JPA가 관리하는 영속 상태 엔티티는 변경 감지가 일어난다. 하지만 준영속 엔티티는 영속성 컨텍스트의 지원을 받을 수 없고 데이터를 수정해도 변경 감지 기능은 동작X
        //준영속 엔티티를 수정하는 2가지 방법
        //변경 감지 기능 사용 -> itemService의 updateItem() 참고
        //병합( merge ) 사용

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        **/

        //좋은 방법!
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}