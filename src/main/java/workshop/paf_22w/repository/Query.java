package workshop.paf_22w.repository;

public class Query {
    public static final String ALL_RSVP = """
            select * from rsvp;
            """;

    public static final String FIND_RSVP_BY_NAME = """
            select * from rsvp
                where name like ?;
            """;

    public static final String ADD_RSVP = """
            replace into rsvp(email, phone, confirmDate, comments)
                values (?, ?, ?, ?);
            """;
}
