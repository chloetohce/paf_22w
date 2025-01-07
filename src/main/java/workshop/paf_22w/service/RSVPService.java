package workshop.paf_22w.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import workshop.paf_22w.model.RSVP;
import workshop.paf_22w.repository.RSVPRepository;

@Service
public class RSVPService {
    @Autowired
    private RSVPRepository repository;

    public JsonArray getAllRSVP() {
        List<RSVP> rsvps = repository.getAllRSVP();
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (RSVP r : rsvps) {
            JsonObject rsvp = Json.createObjectBuilder()
                .add("name", r.getName())
                .add("email", r.getEmail())
                .add("phone", r.getPhone())
                .add("confirmDate", r.getConfirmDate().toString())
                .add("comments", r.getComments())
                .build();
            arr.add(rsvp);
        }
        return arr.build();
    }

    public JsonArray findRsvpByName(String name) {
        List<RSVP> rsvps = repository.findByName(name);
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (RSVP r : rsvps) {
            JsonObject rsvp = Json.createObjectBuilder()
                .add("name", r.getName())
                .add("email", r.getEmail())
                .add("phone", r.getPhone())
                .add("confirmDate", r.getConfirmDate().toString())
                .add("comments", r.getComments())
                .build();
            arr.add(rsvp);
        }
        return arr.build();
    }

    public boolean addRsvp(Map<String, String> data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return repository.addRSVP(data.get("email"), data.get("phone"), sdf.parse(data.get("confirmDate")), data.get("comments"));
    }
}
