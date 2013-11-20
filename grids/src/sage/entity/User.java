package sage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="User")
public class User {
    private long id;
    private String email;
    private String name;
    private String password;
    private String intro;
    private String avatar;

    public User() {
    }
    
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String name, String intro, String avatar) {
        this(email, password);
        this.name = name;
        this.intro = intro;
        this.avatar = avatar;
    }

    @Id
    @GeneratedValue
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    
    public String getIntro() {return intro;}
    public void setIntro(String intro) {this.intro = intro;}
    
    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar = avatar;}

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
