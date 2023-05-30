package com.kakaobean.dataTech.dataInjection;

import com.kakaobean.dataTech.domain.*;
import com.kakaobean.dataTech.domain.respositorty.DateRepository;
import com.kakaobean.dataTech.domain.respositorty.DayOfWeekRepository;
import com.kakaobean.dataTech.domain.respositorty.PrecipitationRepository;
import com.kakaobean.dataTech.domain.respositorty.TimeStampRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsvService {

    private final DayOfWeekRepository dayOfWeekRepository;
    private final TimeStampRepository timeStampRepository;
    private final DateRepository dateRepository;
    private final PrecipitationRepository precipitationRepository;


    // 요일 생성
    @Transactional
    public void registerDay(){
        dayOfWeekRepository.save(new DayOfWeek(Week.MONDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.TUESDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.WEDNESDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.THURSDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.FRIDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.SATURDAY.toString()));
        dayOfWeekRepository.save(new DayOfWeek(Week.SUNDAY.toString()));
    }

    // 시간 생성
    @Transactional
    public void registerTime(){
        for (int i = 0; i < 24; i++) {
            timeStampRepository.save(new TimeStamp(i+":00"));
        }
    }

    // 날짜 생성
    @Transactional
    public void registerDate(){
        LocalDate startDate = LocalDate.of(2020,1,1);
        LocalDate endDate = LocalDate.of(2022,12,31);

        List<Date> dateList = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {

            // date 포멧 바꾸기
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.M.d");
            LocalDate inputDate = LocalDate.parse(date.toString(), inputFormatter);
            String outputDate = inputDate.format(outputFormatter);

            // 요일 찾기
            String dayOfWeek = date.getDayOfWeek().toString();
            DayOfWeek findDayOfWeek = dayOfWeekRepository.findByDayOfWeek(dayOfWeek)
                    .orElseThrow(() -> new RuntimeException("요일 못찾겠다"));

            Date dateEntity = new Date(outputDate);
            dateEntity.addWeekDay(findDayOfWeek);
            dateList.add(dateEntity);
        }

        dateRepository.saveAll(dateList);
    }

    // 강수량 생성
    @Transactional
    public void registerPrecipitation() throws CsvValidationException, IOException {
        readWeatherCSVFile("src/main/resources/dataset/weather20.csv");
    }

    // CSV 파일 읽기
    public void readWeatherCSVFile(String fileName) throws IOException, CsvValidationException {

        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        String[] line;

        csvReader.readNext(); // 첫 번째 라인 건너뛰기

        while ((line = csvReader.readNext()) != null) {
            String date = line[2].split(" ")[0]; // 2020.1.1 ~2020.12.31
            String time = line[2].split(" ")[1].split(":")[0];  // 0 ~ 23
            double precipitation = Double.parseDouble(line[3]); // 1.7 , 0.2 등등

            Date findDate = dateRepository.findByDate(date)
                    .orElseThrow(() -> new RuntimeException("날짜 못찾겠다"));
            TimeStamp findTimeStamp = timeStampRepository.findByTimeStamp(time)
                    .orElseThrow(() -> new RuntimeException("시간 못찾겠다"));

            Precipitation precipitationEntity = new Precipitation(precipitation);
            precipitationEntity.addDate(findDate);
            precipitationEntity.addTimeStamp(findTimeStamp);

            precipitationRepository.save(precipitationEntity);

//            System.out.println(date);
//            System.out.println(time);
//            System.out.println(precipitation);
        }
    }

}
