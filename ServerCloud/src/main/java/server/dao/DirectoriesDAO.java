package server.dao;

import core.exceptions.NullLoginOrPassException;
import server.entites.Directories;
import server.entites.Users;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirectoriesDAO {


    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public DirectoriesDAO(EntityManager  entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public List<Directories> getAll(int id) {return entityManager.createQuery("from Directories").getResultList();  }

    public Directories getById(int id) {
        return entityManager.find(Directories.class, id);
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
