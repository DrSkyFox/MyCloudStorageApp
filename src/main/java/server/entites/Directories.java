package server.entites;

public class Directories {
    private Integer idUser;
    private String pathMain;
    private String privileges;
    private Boolean active;

    public Directories(Integer idUser, String pathMain, String privilage, Boolean active) {
        this.idUser = idUser;
        this.pathMain = pathMain;
        this.privileges = privilage;
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

    public String getPathMain() {
        return pathMain;
    }

    public void setPathMain(String pathMain) {
        this.pathMain = pathMain;
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
