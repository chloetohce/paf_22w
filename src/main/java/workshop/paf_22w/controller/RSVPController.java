package workshop.paf_22w.controller;

import java.net.URI;
import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.json.JsonArray;
import workshop.paf_22w.service.RSVPService;




@RestController
@RequestMapping("/api")
public class RSVPController {
    @Autowired
    private RSVPService service;
    
    @GetMapping(path="/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRsvps() {
        ResponseEntity<String> response = ResponseEntity.ok(service.getAllRSVP().toString());
        return response;
    }

    @GetMapping(path="/rsvp", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRsvpByName(@RequestParam String q) {
        JsonArray rsvps = service.findRsvpByName(q);
        if (rsvps.isEmpty()) {
            return new ResponseEntity<>("RSVP with name %s not found".formatted(q), HttpStatusCode.valueOf(404));
        }
        ResponseEntity<String> response = ResponseEntity.ok(rsvps.toString());
        return response;
    }

    @PostMapping(path="/rsvp", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRsvp(@RequestBody Map<String, String> entity) {
        try {
            boolean added = service.addRsvp(entity);
            if (!added) {
                return new ResponseEntity<>("Error adding RSVP for %s. ".formatted(entity.get("email")), HttpStatusCode.valueOf(422));
            } else {
                URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .pathSegment("api")
                    .pathSegment("rsvp")
                    .queryParam("q", entity.get("name"))
                    .build()
                    .toUri();
                System.out.println(uri.toString());
                return ResponseEntity.created(uri).build();
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Error parsing date.");
        }
    }

    @GetMapping(path = "/rsvp/count", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> count() {
        return ResponseEntity.ok(Integer.toString(service.count()));
    }
    
    
    
}
