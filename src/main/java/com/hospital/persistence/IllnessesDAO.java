package com.hospital.persistence;

import com.hospital.entities.Illness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class IllnessesDAO {
    @Inject
    private EntityManager entityManager;

    public void persist(Illness illness){
        this.entityManager.persist(illness);
    }

    public List<Illness> getAll(){
        return entityManager.createNamedQuery("Illness.findAll", Illness.class).getResultList();
    }

    public Illness findOne(Integer id){
        return entityManager.find(Illness.class, id);
    }
}