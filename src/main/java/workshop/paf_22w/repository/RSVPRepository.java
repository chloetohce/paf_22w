package workshop.paf_22w.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import workshop.paf_22w.model.RSVP;

@Repository
public class RSVPRepository {
    @Autowired
    private JdbcTemplate template;

    public List<RSVP> getAllRSVP() {
        SqlRowSet rs = template.queryForRowSet(Query.ALL_RSVP);
        List<RSVP> list = new ArrayList<>();
        while (rs.next()) {
            list.add(RSVP.toRSVP(rs));
        }
        return list;
    }

    public List<RSVP> findByName(String name) {
        SqlRowSet rs = template.queryForRowSet(Query.FIND_RSVP_BY_NAME, "%%%s%%".formatted(name));
        List<RSVP> list = new ArrayList<>();
        while (rs.next()) {
            list.add(RSVP.toRSVP(rs));
        }
        return list;
    }

    public boolean addRSVP(String email, String phone, Date confirmDate, String comments) {
        int replaced = template.update(Query.ADD_RSVP, email, phone, confirmDate, comments);
        return replaced > 0;
    }
}
