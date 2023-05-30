package com.kakaobean.dataTech.dataInjection;

import com.kakaobean.dataTech.domain.respositorty.DateRepository;
import com.kakaobean.dataTech.domain.respositorty.PrecipitationRepository;
import com.kakaobean.dataTech.domain.respositorty.TimeStampRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadCSV {

    private final CsvService csvService;

    @PostConstruct
    void initCSV() throws IOException, CsvValidationException {
        // csvService.readWeatherCSVFile("src/main/resources/dataset/weather20.csv");
    }

}
