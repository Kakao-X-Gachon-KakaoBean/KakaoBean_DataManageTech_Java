package com.kakaobean.dataTech.dataInjection;

import com.kakaobean.dataTech.common.CommandSuccessResponse;
import com.kakaobean.dataTech.dataInjection.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CsvController {

    private final CsvService csvService;

    @PostMapping("/day")
    public ResponseEntity registerDay(){
        csvService.registerDay();
        return new ResponseEntity(new CommandSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping("/time")
    public ResponseEntity registerTime(){
        csvService.registerTime();
        return new ResponseEntity(new CommandSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping("/date")
    public ResponseEntity registerDate(){
        csvService.registerDate();
        return new ResponseEntity(new CommandSuccessResponse(), HttpStatus.OK);
    }



}
