package com.co.devux.spring.springapi.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clients")
public class Client  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty()
    @Size(min = 4, max = 12)
    @Column(nullable = false)
    private String firstName;

    @NotEmpty
    @Column(nullable = false)
    private String lastName;

    @NotEmpty
    @Email
    @Column(unique = true, nullable = false)
    private String  email;

    @NotNull
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;





    private String avatar;


  /*  @PrePersist
    public  void prePersist(){
        createAt = new Date();
    }
 */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
