package org.kickass.lordofpomelo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.kickass.lordofpomelo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByName(String username) {
        TypedQuery<User> query = em.createQuery("select o from User o where o.name = :name", User.class);
        query.setParameter("name", username);
        return query.getSingleResult();
    }

    @Transactional
    public void createUser(User user) {
        em.persist(user);
    }

}
