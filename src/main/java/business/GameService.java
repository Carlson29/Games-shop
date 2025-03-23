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
    public static final String NOT_CONNECTED_RESPONSE = "NOT_CONNECTED";
    public static final String GAME_ORDER_DELIMITER = "$$";
    public static final String ORDER_EXIST_RESPONSE = "ORDER_EXIST";
    public static final String ORDER_ADDED_RESPONSE = "ORDER_ADDED";
    public static final String GAME_ADDED_RESPONSE = "GAME_ADDED";
    public static final String GAME_EXIST_RESPONSE = "GAME_EXIST";
    public static final String BUY_ID = "B";
    public static final String SELL_ID = "S";
    public static final String ALREADY_CONNECTED = "ALREADY_CONNECTED";
    //what to use for DELIMITER, we used %% for Michelle for film service
}
