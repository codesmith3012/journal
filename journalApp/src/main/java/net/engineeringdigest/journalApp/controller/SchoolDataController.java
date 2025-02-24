package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.SchoolData;
import net.engineeringdigest.journalApp.entity.Student;
import net.engineeringdigest.journalApp.entity.WebhookDetails;
import net.engineeringdigest.journalApp.repository.SchoolDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// This is the controller which is used to implement webhooks in our program i have not implemented it but i have made the controller if someone wants to  add something to it...
public class SchoolDataController {

    @Autowired
    private SchoolDataRepository schoolDataRepository;

    @Autowired
    private WebhookDetails webhookDetails;

    @Autowired
    private SchoolData schoolData;

    @Autowired
    private Student student;

    @PostMapping("/addNewSchool/{schoolName}")
    public ResponseEntity<SchoolData> addNewSchool(@PathVariable String schoolName) {
        SchoolData schoolData = new SchoolData();
        schoolData.setSchoolName(schoolName);
        SchoolData savedSchool = schoolDataRepository.save(schoolData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchool);
    }

    @PostMapping(path = "/addWebhookEvent/{schoolId}") // Map ONLY POST Requests
    public String addWebhookEvent(@PathVariable Integer schoolId, @RequestBody WebhookDetails webhookDetails) {
        Optional<SchoolData> schoolData = schoolDataRepository.findById(schoolId);

        ArrayList<WebhookDetails> webhooks;
        webhooks = new ArrayList<>();
        WebhookDetails details = new WebhookDetails();
        details.setEventName(webhookDetails.getEventName());
        details.setEndPointUrl(webhookDetails.getEndPointUrl());

        details.setSchoolData(schoolData.get());
        webhooks.add(details);

        schoolData.get().setWebbhookDetails(webhooks);
        schoolDataRepository.save(schoolData.get());
        return "Webhook Added";

    }

    @PostMapping(path = "/addStudent/{schoolId}") // Map ONLY POST Requests
    public String addStudent(@PathVariable Integer schoolId, @RequestBody Student reqData) {
        Optional<SchoolData> schoolData = schoolDataRepository.findById(schoolId);
        SchoolData schoolData2 = schoolData.get();

        List<Student> students = new ArrayList<Student>();
        student.setAge(reqData.getAge());
        student.setName(reqData.getName());
        student.setSchoolData(schoolData2);
        students.add(student);

        schoolData2.setStudents(students);
        schoolDataRepository.save(schoolData2);
        // send as webhook
       WebhookDetails webhookDetails= schoolData2.getWebbhookDetails().
                stream().filter(eventData -> (eventData.getEventName().equals("add")))
                .findFirst()
                .orElse(null);
        if(webhookDetails !=null && webhookDetails.getEndPointUrl() !=null) {
            String url = webhookDetails.getEndPointUrl();//localhost:9000/
            url+="/"+reqData.getName();
            RestTemplate restTemplate = new RestTemplate();
            String result= restTemplate.getForObject(url, String.class);
            System.out.println("result: "+result);
        }
        return "Student Added";

    }

}
