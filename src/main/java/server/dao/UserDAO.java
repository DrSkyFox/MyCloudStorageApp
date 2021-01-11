package server.dao;

import core.exceptions.LoginSmallException;
import core.exceptions.NullLoginOrPassException;
import core.exceptions.SomeThingWrongException;
import core.exceptions.UserAlreadyExistsException;
import server.entites.Users;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDAO  {


    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Users getById(int i) {
        return entityManager.find(Users.class, i);
    }


    public Users getFindByParam(String login, String pass) throws NullLoginOrPassException {
        CriteriaQuery<Users> usersCriteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> root = usersCriteriaQuery.from(Users.class);
        List<Predicate> predicate = new ArrayList<>();
        if ((login == null) || (pass == null)) {
            throw new NullLoginOrPassException("Empty login or pass");
        }

        predicate.add(criteriaBuilder.equal(root.get("login"), login));

        predicate.add(criteriaBuilder.equal(root.get("pass"), pass));

        usersCriteriaQuery.select(root).where(predicate.toArray(new Predicate[] {}));
        Users user = entityManager.createQuery(usersCriteriaQuery).getSingleResult();
        return user;
    }



    public List<Users> getAll() {
        return entityManager.createQuery("from Users").getResultList();
    }

    public void create(Users users) throws UserAlreadyExistsException, LoginSmallException, SomeThingWrongException {

        if(users.getLogin().length() <= 3) {
            throw new LoginSmallException("Login must value > 3");
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(users);
            entityManager.getTransaction().commit();

        } catch (RollbackException e) {
            throw new UserAlreadyExistsException("User already exists", e);
        } catch (Exception e) {
            throw new SomeThingWrongException("Something was wrong", e);
        }

    }

    public void remove(Users users) {
        entityManager.remove(users);
    }

    public void edit(Users users) {
        entityManager.merge(users);
    }
}
