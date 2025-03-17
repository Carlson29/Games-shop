package business;

public class GameService {

    public static final String HOST = "localhost";
    public static final int PORT = 41238;
    //not sure what port
    public static final String USER_REQUEST = "USER";
    public static final String USER_CONNECT_RESPONSE = "CONNECTED";
    public static final String ORDER_REQUEST = "ORDER";
    public static final String ORDER_MATCH_RESPONSE = "MATCH";
    public static final String CANCEL_REQUEST = "CANCEL";
    public static final String CANCEL_CANCELLED_RESPONSE = "CANCELLED";
    public static final String NOT_FOUND_RESPONSE = "NOT_FOUND";
    public static final String VIEW_REQUEST = "VIEW";
    public static final String END_REQUEST = "END";
    public static final String END_RESPONSE = "ENDED";
    public static final String DELIMITER = ",";
    public static final String ACTION_DELIMITER = ":";
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    //what to use for DELIMITER, we used %% for Michelle for film service
}
