package com.hospital.usecases;

import com.hospital.entities.Patient;
import com.hospital.persistence.HeavyPatientsDAO;
import com.hospital.persistence.PatientsDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ApplicationScoped
@Named
@Getter
@Setter
public class UpdatePatientDetails implements Serializable {

    private Patient patient;

    @Inject
    private PatientsDAO patientsDAO;


    @Transactional
    public String updatePatient(int id, String name) {

        patient = patientsDAO.findOne(id);
        patient.setName(name);
        try{
            patientsDAO.update(this.patient);
        } catch (OptimisticLockException e) {
            return ""+this.patient.getId();
        }
        return null;
    }
}
