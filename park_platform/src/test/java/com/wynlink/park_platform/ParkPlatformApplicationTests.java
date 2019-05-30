package com.wynlink.park_platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wynlink.park_platform.test.CodeGenerator;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkPlatformApplicationTests {

	@Test
	public void contextLoads() {
		CodeGenerator gse = new CodeGenerator();
        //要给那些表生成
        gse.generateByTables(false,"month_card");
	}

}
