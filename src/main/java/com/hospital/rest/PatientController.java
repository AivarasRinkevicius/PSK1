package com.hospital.rest;

import com.hospital.decorators.IGenerator;
import com.hospital.entities.Patient;
import com.hospital.interceptors.MethodLogger;
import com.hospital.persistence.HospitalsDAO;
import com.hospital.persistence.IPatient;
import com.hospital.persistence.IllnessesDAO;
import com.hospital.rest.contracts.PatientDto;
import com.hospital.rest.contracts.PatientUpdateDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/patients")
public class PatientController {

    @Inject
    private IPatient patientsDAO;

    @Inject
    private IGenerator generator;

    @Inject
    private HospitalsDAO hospitalsDAO;

    @Inject
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @MethodLogger
    public Response getAll() {

        List<PatientDto> patientDtos = new ArrayList<>();
        List<Patient> patients = patientsDAO.getAll();

        for(Patient patient: patients){
            patientDtos.add(new PatientDto(patient));
        }

        System.out.println(patients);
        if (patientDtos.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(patientDtos).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Patient patient = patientsDAO.findOne(id);
        if (patient == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PatientDto patientDto = new PatientDto();
        patientDto.setName(patient.getName());
        patientDto.setSurname(patient.getSurname());
        patientsDAO.update(patient);
        patientDto.setId(id);
        return Response.ok(patientDto).build();
    }


      @POST()
      @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.APPLICATION_JSON)
      @Transactional
      public Response create(PatientUpdateDto patientUpdateDto) {
          Patient patient = new Patient();
          patient.setName(patientUpdateDto.getName());
          // patient.setSurname(patientUpdateDto.getSurname());
          // decorator
          patient.setSurname(generator.generateRandomMessage());
          patient.setHospital(hospitalsDAO.findOne(patientUpdateDto.getHospitalId()));
          patient.setPatientIllnesses(new ArrayList<>());
          System.out.println(patient);
          patientsDAO.persist(patient);

          return Response.ok().build();
      }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer id,
            PatientUpdateDto patientData){
        try{
            Patient patient = patientsDAO.findOne(id);
            Thread.sleep(3000);
            if (patient == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            patient.setName(patientData.getName());
            patient.setSurname(patientData.getSurname());
            patientsDAO.update(patient);
            entityManager.flush();
            return Response.ok().build();
        } catch (OptimisticLockException e) {
            System.out.println("OptimisticLockException");
            return Response.status(Response.Status.CONFLICT).build();
        }
        catch (Exception e) {
            System.out.println("OptimisticLockException");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
