package com.hospital.persistence;

import com.hospital.entities.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import java.util.List;

@Specializes
@ApplicationScoped
public class SpecializedPatientsDAO extends PatientsDAO{

    @Override
    public List<Patient> getAll(){
        List<Patient> patients = super.getAll();
        for( Patient patient: patients){
            System.out.println("name:"+patient.getName());
            System.out.println("surname:"+patient.getSurname());
        }
        return patients;
    }
}
