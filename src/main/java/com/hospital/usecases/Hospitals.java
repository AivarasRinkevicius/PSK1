package com.hospital.usecases;

import com.hospital.entities.Hospital;
import com.hospital.persistence.HospitalsDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Hospitals {
    @Inject
    private HospitalsDAO hospitalsDAO;

    @Getter
    @Setter
    private Hospital hospitalToCreate = new Hospital();

    @Getter
    private List<Hospital> allHospitals;

    @PostConstruct
    public void init(){
        loadAllHospitals();
    }

    @Transactional
    public void createHospital(){
        this.hospitalsDAO.persist(hospitalToCreate);
    }

    private void loadAllHospitals(){
        this.allHospitals = hospitalsDAO.getAll();
    }
}
