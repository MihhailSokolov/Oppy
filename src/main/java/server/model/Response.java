package server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    private String message;

    @JsonCreator
    public Response(@JsonProperty("message") String message) {
        this.message = message;
    }

    @JsonCreator
    public Response(@JsonProperty("message") int intMessage) {
        this.message = String.valueOf(intMessage);
    }

    @JsonCreator
    public Response(@JsonProperty("message") boolean boolMessage) {
        this.message = String.valueOf(boolMessage);
    }

    public String getMessage() {
        return message;
    }
}