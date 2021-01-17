package server.dao;

import core.exceptions.*;
import server.entites.Users;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    public Users getFindByParam(String login, String pass) throws NullLoginOrPassException, UserNotFoundException {


        CriteriaQuery<Users> usersCriteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> root = usersCriteriaQuery.from(Users.class);
        List<Predicate> predicate = new ArrayList<>();


        try {
            predicate.add(criteriaBuilder.equal(root.get("login"), Objects.requireNonNull(login)));
            predicate.add(criteriaBuilder.equal(root.get("pass"), Objects.requireNonNull(pass)));
        }
        catch (NullPointerException e) {
            throw new NullLoginOrPassException("Incorrect login or pass", e.getCause());
        }

        usersCriteriaQuery.select(root).where(predicate.toArray(new Predicate[] {}));

        try {
            return Objects.requireNonNull(entityManager.createQuery(usersCriteriaQuery).getSingleResult());
        } catch (NullPointerException e) {
            throw new UserNotFoundException("User not found", e.getCause());
        }
    }



    public List<Users> getAll() {
        return entityManager.createQuery("from Users").getResultList();
    }

    public void create(Users users) throws UserAlreadyExistsException, LoginSmallException, SomeThingWrongException {
        Users userChecked = checkUserData(users);

        if(userChecked.getLogin().length() <= 3) {
            throw new LoginSmallException("Login must value > 3");
        }


        try {
            entityManager.getTransaction().begin();
            entityManager.persist(userChecked);
            entityManager.getTransaction().commit();

        } catch (RollbackException e) {
            throw new UserAlreadyExistsException("User already exists", e);
        } catch (Exception e) {
            throw new SomeThingWrongException("Something was wrong", e);
        }

    }

    private Users checkUserData(Users users) throws SomeThingWrongException {
        try {
            return Objects.requireNonNull(users);
        }
        catch (NullPointerException e) {
            throw new SomeThingWrongException("Not found data USER", e.getCause());
        }
    }

    public void remove(Users users) throws SomeThingWrongException {
        entityManager.remove(checkUserData(users));
    }

    public void edit(Users users) throws SomeThingWrongException {
        entityManager.merge(checkUserData(users));
    }
}
