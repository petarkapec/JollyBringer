package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


//TODO enable that each id corresponds with specific name
@Entity
public class Role {

    @Id
    private Long role_id;

    private String name;

    public Role(Long role_id, String name) {
        this.role_id = role_id;
        this.name = name;
    }

    public Role() {

    }

    public long getId() {
        return role_id;
    }

    public void setId(long id) {
        this.role_id = id;
    }

    public String getName() {
        return name;
    }

//    public Role(long id) {
//
//
//        switch(id) {
//            case 1L:
//                this.name = "PARTICIPANT";
//                break;
//            case 2L:
//                this.name = "CHRISTMAS PRESIDENT";
//                break;
//            case 3L:
//                this.name = "ADMIN";
//                break;
//            default:
//                Assert.isTrue(id > 3, "Id out of range");
//
//        }
//        this.role_id = id;
//    }
//
//    public Role(String name) {
//
//        this.name = name;
//        switch(name) {
//            case "PARTICIPANT":
//                this.role_id = 1L;
//                break;
//            case "CHRISTMAS PRESIDENT":
//                this.role_id = 2L;
//                break;
//            case "ADMIN":
//                this.role_id = 3L;
//                break;
//        }
//    }

}
