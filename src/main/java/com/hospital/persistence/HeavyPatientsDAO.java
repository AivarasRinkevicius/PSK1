package com.hospital.persistence;

import com.hospital.entities.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
@Alternative
public class HeavyPatientsDAO implements IPatient{
    @Inject
    private EntityManager entityManager;

    public void persist(Patient patient){
        try {
            Thread.sleep(4000);
            this.entityManager.persist(patient);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<Patient> getAll(){
        try {
            Thread.sleep(4000);
            return entityManager.createNamedQuery("Patient.findAll", Patient.class).getResultList();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Patient findOne(Integer id){
        try {
            Thread.sleep(4000);
            return entityManager.find(Patient.class, id);
        }
         catch (Exception e){
           System.out.println(e.getMessage());
        }
        return null;
    }

    public Patient update(Patient player){
        try {
            Thread.sleep(4000);
        return entityManager.merge(player);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
