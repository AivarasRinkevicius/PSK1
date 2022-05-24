package com.hospital.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Patient.findAll", query = "select patient from Patient as patient")
})
@Table(name = "PATIENT")
@Getter @Setter
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private int version;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @ManyToMany
    @JoinTable(
            name = "illness_patient",
            joinColumns = @JoinColumn(name = "patient_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "illness_id",referencedColumnName = "ID"))
    List<Illness> patientIllnesses;
    @ManyToOne
    @JoinColumn(name = "HOSPITAL_ID")
    private Hospital hospital;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id != null && Objects.equals(id, patient.id)
                && Objects.equals(name, patient.name)
                && Objects.equals(surname, patient.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,surname);
    }
}
