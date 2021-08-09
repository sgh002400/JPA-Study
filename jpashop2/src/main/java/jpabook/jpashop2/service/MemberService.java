package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //JPA의 모든 데이터 변경과 같은 로직들은 Transaction 안에 실행돼야 함.
// 조회만 하는 메서드에서는 이 어노테이션과 옵션을 붙여주면 성능을 최적화해준다. -> 이걸 여기서 선언해주면 클래스 내 모든 메서드에 적용된다.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /** 회원가입 **/ //@Transactional의 Default는 readOnly = false이므로 이 어노테이션을 여기서 다시 붙여주면 true 대신 false가 적용된다.
    @Transactional //데이터를 변경하는 메서드에서 readOnly = true 옵션을 붙이면 변경이 되지 않는다!
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**전체 회원 조회 **/
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
