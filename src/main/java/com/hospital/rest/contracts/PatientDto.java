package com.hospital.rest.contracts;

import com.hospital.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private String name;

    private Integer id;

    private String surname;

    public PatientDto(Patient patient) {
       this.name = patient.getName();
       this.surname = patient.getSurname();
       this.id = patient.getId();
    }
}
