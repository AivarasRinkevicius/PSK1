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
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    private CompletableFuture<String> generateRandomNameTask = null;
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(PatientUpdateDto patientUpdateDto) {

        Patient patient = new Patient();
        patient.setSurname(generator.generateRandomMessage());
        generateRandomNameTask = CompletableFuture.supplyAsync(this::generateRandomName);
        try{
          patient.setName(generateRandomNameTask.get());
        } catch (Exception e){
          System.out.println(e.getMessage());
        }
        // patient.setName(patientUpdateDto.getName());
        // patient.setSurname(patientUpdateDto.getSurname());
        patient.setHospital(hospitalsDAO.findOne(patientUpdateDto.getHospitalId()));
        patient.setPatientIllnesses(new ArrayList<>());
        System.out.println(patient);
        patientsDAO.persist(patient);

      return Response.ok().build();
    }

    @Path("/task")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String asyncExample(){
        if(generateRandomNameTask != null && !generateRandomNameTask.isDone()){
            return "generate random name task is in progress";
        }
        return "generate random name task is completed";
    }

    public String generateRandomName() {

        int max = 9;
        int min = 0;
        int diff = max - min;
        Random rn = new Random();
        int i = rn.nextInt(diff + 1);
        i += min;

        List<String> names = new ArrayList<>( Arrays.asList("Jonas", "Tomas", "Ona", "Ignas", "Veronika", "Migle", "Ieva", "Kamile", "Kornelija", "Ugne"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return names.get(i);
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
