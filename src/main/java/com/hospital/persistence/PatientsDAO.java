package com.hospital.persistence;

import com.hospital.entities.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
@Default
public class PatientsDAO implements IPatient{

    @Inject
    private EntityManager entityManager;

    public void persist(Patient patient){
        this.entityManager.persist(patient);
    }

    public List<Patient> getAll(){
        return entityManager.createNamedQuery("Patient.findAll", Patient.class).getResultList();
    }

    public Patient findOne(Integer id){
        return entityManager.find(Patient.class, id);
    }

    public Patient update(Patient player){
        return entityManager.merge(player);
    }
}
