package com.cmikeb.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cmbylund on 12/27/15.
 */
@Configuration
public class SecurityConfig {

    //@Value("${security.key}")
    private String securityKey = "nRAqnCKzchLyD2r=YMU4";

    @Bean
    StringEncryptor stringEncryptor(){
        StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        stringEncryptor.setPassword(securityKey);
        return stringEncryptor;
    }
}