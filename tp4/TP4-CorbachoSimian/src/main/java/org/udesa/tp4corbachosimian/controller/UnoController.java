package org.udesa.tp4corbachosimian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UnoController {
    @GetMapping("/")
    public String saludo() {
        return "index";
    }
//
//    @PostMapping("play/{matchID}/{player}") public ResponseEntity play(@PathVariable UUID matchID);

}
