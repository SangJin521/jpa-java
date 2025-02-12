package io.goorm.service;

import io.goorm.model.Member;
import io.goorm.util.JPAConnectionUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Jpql {
    public void createMember() {

        EntityManager em  = JPAConnectionUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            String jpql = "SELECT m FROM Member m WHERE m.memberName = :name";
            List<Member> result = em.createQuery(jpql, Member.class)
                    .setParameter("name", "test")
                    .getResultList();


            System.out.println("##################################before-commit");
            em.getTransaction().commit();
            System.out.println("##################################after-commit");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // 트랜잭션 롤백
            }
            e.printStackTrace(); // 예외 출력
        } finally {
            em.close();
        }

    }
}
