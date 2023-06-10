package com.kakaobean.dataTech;

import com.kakaobean.dataTech.domain.dto.BasicDto;
import com.kakaobean.dataTech.infrastructure.SubwayUsageQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DataTechApplicationTests {
	
	@Autowired
	SubwayUsageQueryRepository queryRepository;

	@Test
	void test() {
		List<BasicDto> all = queryRepository.findAll();
		System.out.println("all.size() = " + all.size());
	}
}
