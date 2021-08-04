package jpabook.jpashop2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) { //model에 데이터를 담아서 뷰에 넘길 수 있다.
        model.addAttribute("data", "hello!"); //key : data, value: hello!
        return "hello"; //자동으로 .html이 붙는다.
    }
}
