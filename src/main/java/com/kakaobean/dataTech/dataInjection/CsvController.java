package com.kakaobean.dataTech.dataInjection;

import com.kakaobean.dataTech.common.CommandSuccessResponse;
import com.kakaobean.dataTech.dataInjection.CsvService;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

    @PostMapping("/precipitation")
    public ResponseEntity registerPrecipitation() throws CsvValidationException, IOException {
        csvService.registerPrecipitation();
        return new ResponseEntity(new CommandSuccessResponse(), HttpStatus.OK);
    }

}
