package server.entites;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user")
    private String user;

    @Column(name = "pass")
    private Character pass;

    @Column(name = "deleted")
    private Boolean deleted;


    public Users() {
    }

    public Users(Integer id, String username, Character password, Boolean deleted) {
        this.id = id;
        this.user = username;
        this.pass = password;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String username) {
        this.user = username;
    }

    public Character getPass() {
        return pass;
    }

    public void setPass(Character password) {
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
                ", username='" + user + '\'' +
                ", password=" + pass +
                ", deleted=" + deleted +
                '}';
    }
}
