package server.entites;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SharingFiles")
public class SharingFile {

    @Id
    @Column(name = "userID")
    private Integer userId;
    @Column(name = "toUserID")
    private Integer toUserId;
    @Column(name = "privileges")
    private String privileges;
    @Column(name = "filesShare")
    private String fileShare;


    public SharingFile(Integer userId, Integer toUserId, String privileges, String fileShare) {
        this.userId = userId;
        this.toUserId = toUserId;
        this.privileges = privileges;
        this.fileShare = fileShare;
    }

    public SharingFile() {
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getFileShare() {
        return fileShare;
    }

    public void setFileShare(String fileShare) {
        this.fileShare = fileShare;
    }

    @Override
    public String toString() {
        return "SharingFile{" +
                "userId=" + userId +
                ", toUserId=" + toUserId +
                ", privileges='" + privileges + '\'' +
                ", fileShare='" + fileShare + '\'' +
                '}';
    }
}
