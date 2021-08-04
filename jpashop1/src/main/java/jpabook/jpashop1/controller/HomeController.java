package jpabook.jpashop1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    //Logger log = LoggerFactory.getLogger(getClass()); //Slf4j 사용법 -> Lombok으로 대체할 수 있다.

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
