package com.hospital.rest.contracts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientUpdateDto {

    private String name;

    private String surname;

    private int hospitalId;

}
