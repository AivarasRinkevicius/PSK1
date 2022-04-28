package com.hospital.usecases;

import com.hospital.entities.Hospital;
import com.hospital.entities.Illness;
import com.hospital.entities.Patient;
import com.hospital.persistence.HospitalsDAO;
import com.hospital.persistence.PatientsDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Model
public class PatientsForHospital implements Serializable {

    @Inject
    private PatientsDAO patientsDAO;

    @Inject
    private HospitalsDAO hospitalsDAO;

    @Getter @Setter
    private Hospital hospital;

    //@Getter
    //private List<Patient> allPatients;

    @Getter @Setter
    private Patient patientToCreate = new Patient();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer hospitalId = Integer.parseInt(requestParameters.get("hospitalId"));
        this.hospital = hospitalsDAO.findOne(hospitalId);

    }

    @Transactional
    public void createPatient() {
        patientToCreate.setHospital(this.hospital);
        patientsDAO.persist(patientToCreate);
    }

    //private void loadAllIllnesses(){
     //   this.allPatients = patientsDAO.getAll();
   // }
}
