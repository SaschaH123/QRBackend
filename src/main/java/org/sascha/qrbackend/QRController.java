package org.sascha.qrbackend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@CrossOrigin(origins = "*") // Für Development - später spezifischer machen
public class QRController {
    @GetMapping("/hello")
    public String hello(){
        return "Hallo von Spring";
    }
}
