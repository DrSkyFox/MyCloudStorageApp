package server.entites;

public class User {
    private Integer id;
    private String username;
    private Character password;
    private Boolean active;

    public User() {
    }

    public User(Integer id, String username, Character password, Boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Character getPassword() {
        return password;
    }

    public void setPassword(Character password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
