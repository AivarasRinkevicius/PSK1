package com.hospital.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = "Hospital.findAll", query = "select hospital from Hospital as hospital")
})
@Table(name = "HOSPITAL")
@Getter @Setter
@NoArgsConstructor
public class Hospital implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospital hospital = (Hospital) o;
        return id != null && Objects.equals(id, hospital.id)
                && Objects.equals(name, hospital.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
