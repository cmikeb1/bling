package com.cmikeb;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlingApplication.class)
@WebAppConfiguration
public class BlingApplicationTests {

	@Autowired
	StringEncryptor stringEncryptor;

	@Test
	public void contextLoads() {
	}

	@Test
	public void encryption(){
		String encrypted = stringEncryptor.encrypt("kJnI9898");
		System.out.println(encrypted);
		String decrypted = stringEncryptor.decrypt(encrypted);
		System.out.printf(decrypted);
	}

}
