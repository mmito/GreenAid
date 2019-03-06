package app.models;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private Long users_id;
    private Long roles_id;

    public Long getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Long users_id) {
        this.users_id = users_id;
    }

    public Long getRoles_id() {
        return roles_id;
    }

    public void setRoles_id(Long roles_id) {
        this.roles_id = roles_id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
