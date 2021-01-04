package server.dao;

import server.entites.Users;
import server.interfaces.DAO;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDAO implements DAO<Users> {


    private EntityManager entityManager;

    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



    @Override
    public Users getById(int i) {
        return entityManager.find(Users.class, i);
    }

    @Override
    public Users getFindByParam(Users users) {
        return null;
    }


    @Override
    public List<Users> getAll() {
        return null;
    }

    @Override
    public void create(Users users) {

    }

    @Override
    public void remove(Users users) {

    }

    @Override
    public void edit(Users users) {

    }
}
