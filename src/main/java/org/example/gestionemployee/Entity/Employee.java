package org.example.gestionemployee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Employee {
    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Boolean isworking;
    private Boolean hasleft;

    public Employee(Long id, String nom, String prenom, String email, Boolean isworking, Boolean left) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.isworking = isworking;
        this.hasleft = left;
    }

    public Employee() {

    }
}
