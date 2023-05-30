package com.kakaobean.dataTech.dataInjection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadCSV {

    public void readCSVFile() throws FileNotFoundException {
        BufferedReader file = new BufferedReader(
                new FileReader("classpath:dataset/weather20.csv"));
        String line = "";

        try {
            Set<String> set = new HashSet<>();
            while ((line = file.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                List<String> aLine = new ArrayList<>();
                String[] arr = line.split(","); // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.

                for (String s : arr) {
                    System.out.println(s);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostConstruct
    void initCSV() throws FileNotFoundException {
        readCSVFile();
    }

}
