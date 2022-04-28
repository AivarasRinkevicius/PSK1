package com.hospital.usecases;

import com.hospital.entities.Illness;
import com.hospital.persistence.IllnessesDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Illnesses {
    @Inject
    private IllnessesDAO illnessesDAO;

    @Getter
    @Setter
    private Illness illnessToCreate = new Illness();

    @Getter
    private List<Illness> allIllnesses;

    @PostConstruct
    public void init() {
        loadAllIllnesses(); }

    @Transactional
    public void createIllness(){
        this.illnessesDAO.persist(illnessToCreate);
    }

    private void loadAllIllnesses(){
        this.allIllnesses = illnessesDAO.getAll();
    }
}
