package com.hospital.persistence;

import com.hospital.entities.Patient;

import javax.persistence.EntityManager;
import java.util.List;

public interface IPatient {

    List<Patient> getAll();

    void persist(Patient patient);

    Patient findOne(Integer id);

    Patient update(Patient Patient);
}
