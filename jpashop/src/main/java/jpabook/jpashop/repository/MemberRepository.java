package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //Component Scan에 의해 Spring Bean에 등록된다.
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //Spring이 EntityManager를 주입해줌. -> Spring Data JPA를 사용하면 @Autowired로 대체할 수 있다.
    //@Autowired -> 단일 생성자이므로 생략함.
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from  Member m", Member.class) //JPQL -> SQL은 테이블을 대상으로 쿼리하는데 JPQL은 엔티티 객체를 대상으로 쿼리를 한다.
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
