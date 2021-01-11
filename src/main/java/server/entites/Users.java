package server.entites;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "deleted", columnDefinition = "true")
    private Boolean deleted;

    @OneToMany
    private List<Directories> directoriesList;


    public Users() {
    }

    public Users(Integer id, String username, String password, Boolean deleted) {
        this.id = id;
        this.login = username;
        this.pass = password;
        this.deleted = deleted;
    }

    public Users(String user, String pass) {
        this.login = user;
        this.pass = pass;
        deleted = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String username) {
        this.login = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String password) {
        this.pass = password;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean active) {
        this.deleted = active;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + login + '\'' +
                ", password=" + pass +
                ", deleted=" + deleted +
                '}';
    }
}
