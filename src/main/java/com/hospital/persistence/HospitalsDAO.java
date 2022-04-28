package com.hospital.persistence;

import com.hospital.entities.Hospital;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class HospitalsDAO {
    @Inject
    private EntityManager entityManager;

    public void persist(Hospital hospital){
        this.entityManager.persist(hospital);
    }

    public List<Hospital> getAll(){
        return entityManager.createNamedQuery("Hospital.findAll", Hospital.class).getResultList();
    }

    public Hospital findOne(Integer id){
        return entityManager.find(Hospital.class, id);
    }

}
