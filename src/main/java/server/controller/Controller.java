package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.model.Response;

@RestController
public class Controller {
    @RequestMapping("/check")
    public ResponseEntity<Response> response(
            @RequestParam(value = "msg", defaultValue = "Successful server response") String msg) {
        return ResponseEntity.ok().body(new Response("Server: " + msg));
    }
}
