package com.hospital.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@NamedQueries({
        @NamedQuery(name = "Illness.findAll", query = "select illness from Illness as illness")
})
@Table(name = "ILLNESS")
@Getter @Setter
@NoArgsConstructor
public class Illness implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "TYPE")
    private String type;

    @ManyToMany
    @JoinTable(
            name = "patient_illness",
            joinColumns = @JoinColumn(name = "patient_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "illness_id",referencedColumnName = "id"))
    List<Patient> illnessPatients;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Illness illness = (Illness) o;
        return Objects.equals(id, illness.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
