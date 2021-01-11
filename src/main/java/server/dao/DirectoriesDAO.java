package server.dao;

import server.entites.Directories;

import javax.persistence.EntityManager;
import java.util.List;

public class DirectoriesDAO {


    private EntityManager entityManager;

    public DirectoriesDAO() {
    }

    public DirectoriesDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Directories> getAll(int id) {
        return entityManager.createQuery(String.format("from Directories as d where d.idUser = %s", id)).getResultList();
    }
    

    public void create(Directories directories) {
        entityManager.persist(directories);
    }

    public void remove(Directories directories) {
        entityManager.remove(directories);
    }

    public void edit(Directories directories) {
        entityManager.merge(directories);
    }
}
