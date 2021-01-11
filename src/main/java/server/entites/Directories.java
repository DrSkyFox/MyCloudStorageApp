package server.entites;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Directories")
public class Directories {
    @Id
    @Column(name = "user_id")
    private Integer idUser;
    @Column(name = "catalog")
    private String catalog;
    @Column(name = "privileges")
    private String privileges;
    @Column(name = "deleted")
    private Boolean active;

    public Directories(Integer idUser, String pathMain, String privileges, Boolean active) {
        this.idUser = idUser;
        this.catalog = pathMain;
        this.privileges = privileges;
        this.active = active;
    }

    public Directories() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String pathMain) {
        this.catalog = pathMain;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
