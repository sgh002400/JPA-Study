package jpabook.jpashop1.controller;

import jpabook.jpashop1.domain.Address;
import jpabook.jpashop1.domain.Member;
import jpabook.jpashop1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {

        model.addAttribute("memberForm", new MemberForm()); //controller에서 view로 넘어갈 때 memberForm 데이터를 넘겨준다.
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { //@Valid 어노테이션을 통해 MemberForm이 유효한 객체인지 검사한다.
        //BindingResult -> 오류가 있다면 오류가 변수에 담갠채로 코드가 실행된다.

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
