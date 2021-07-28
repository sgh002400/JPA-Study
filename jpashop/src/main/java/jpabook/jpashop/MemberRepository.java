package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; //entityManagerFactory.createEntityManager(); 이런거 안해도 spring-boot-starter-data-jpa에서 알아서 생성해줌

    public Long save(Member member) { //커맨드와 쿼리를 분리하라! -> 원래는 리턴하지 않음
        em.persist(member); //entity를 영속성 컨텍스트에 저장해줌 -> DB 저장과 다르다!
        return member.getId(); //근데 id가 있으면 조회할 수 있어서 리턴해준거
    }

    public Member find(Long id) {
        return em.find(Member.class, id); //em.find() 를 하면 DB보다 먼저, 1차 캐시를 조회한다. 있으면 바로 반환, 없으면 DB 조회 (참고: https://gmlwjd9405.github.io/2019/08/06/persistence-context.html)


    }
}
