package server.dao;

import server.entites.Directories;
import server.interfaces.DAO;

import javax.persistence.EntityManager;
import java.util.List;

public class DirectoriesDAO implements DAO<Directories> {


    private EntityManager entityManager;

    public DirectoriesDAO() {
    }

    @Override
    public Directories getById(int i) {
        return null;
    }

    @Override
    public Directories getFindByParam(Directories directories) {
        return null;
    }

    @Override
    public List<Directories> getAll() {
        return null;
    }

    @Override
    public void create(Directories directories) {

    }

    @Override
    public void remove(Directories directories) {

    }

    @Override
    public void edit(Directories directories) {

    }
}
