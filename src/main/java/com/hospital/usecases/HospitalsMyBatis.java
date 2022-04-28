package com.hospital.usecases;

import com.hospital.mybatis.model.Hospital;
import com.hospital.mybatis.dao.HospitalMapper;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class HospitalsMyBatis {
   @Inject
   private HospitalMapper hospitalMapper;

    @Getter
    private List<Hospital> allHospitals;

    @Getter @Setter
    private Hospital hospitalToCreate = new Hospital();

    @PostConstruct
    public void init() {
        this.loadAllHospitals();
    }

    private void loadAllHospitals() {
        this.allHospitals = hospitalMapper.selectAll();
    }

    @Transactional
    public String createHospital() {
        hospitalMapper.insert(hospitalToCreate);
        return "/myBatis/hospitals?faces-redirect=true";
    }
}
