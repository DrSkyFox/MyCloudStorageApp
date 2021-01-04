package server.dao;

import server.entites.SharingFile;
import server.interfaces.DAO;

import javax.persistence.EntityManager;
import java.util.List;

public class SharingFilesDAO implements DAO<SharingFile> {



    private EntityManager entityManager;

    public SharingFilesDAO() {
    }

    @Override
    public SharingFile getById(int i) {
        return null;
    }

    @Override
    public SharingFile getFindByParam(SharingFile sharingFile) {
        return null;
    }

    @Override
    public List<SharingFile> getAll() {
        return null;
    }

    @Override
    public void create(SharingFile sharingFile) {

    }

    @Override
    public void remove(SharingFile sharingFile) {

    }

    @Override
    public void edit(SharingFile sharingFile) {

    }
}
