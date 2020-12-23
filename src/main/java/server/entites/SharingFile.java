package server.entites;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sharingfile")
public class SharingFile {

    @Column(name = "userID")
    private Integer userId;
    @Column(name = "shareToUserId")
    private Integer sharingToUserId;
    @Column(name = "priileges")
    private String privileges;


    public SharingFile(Integer userId, Integer sharingToUserId, String privileges) {
        this.userId = userId;
        this.sharingToUserId = sharingToUserId;
        this.privileges = privileges;
    }


    public SharingFile() {
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSharingToUserId() {
        return sharingToUserId;
    }

    public void setSharingToUserId(Integer sharingToUserId) {
        this.sharingToUserId = sharingToUserId;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }
}
