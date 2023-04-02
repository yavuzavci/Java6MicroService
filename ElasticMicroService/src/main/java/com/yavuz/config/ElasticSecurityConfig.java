package com.yavuz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Configuration -> Konfigürasyon dosyası olarak spring e bildireceğimiz sınıflara ekliyoruz.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ElasticSecurityConfig {
    @Bean
    JwtTokenFilter getTokenFilter(){
        return new JwtTokenFilter();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        /**
         * _csrf -> bunu kapatmak için disable komutunu kullanıyoruz.
         */
        httpSecurity.csrf().disable();
        /**
         * Gelen bütün isteklere oturum açılmış mı kendini doğrulamış mı bak.
         * Eğer özel adreslere erişimi açmak istiyorsanız bunları belirterek izin
         * vermelisiniz.
         * Match("/{URLS}") için izin ver permitAll demelisiniz.
         */
        httpSecurity.authorizeHttpRequests()
                .antMatchers("/mylogin.html").permitAll()
                .anyRequest().authenticated();
        /**
         * Yetkisiz girişlerde kullanıcıların kendilerini doğrulamaları için login formuna
         * yönlendirme yaparız.
         */
        //httpSecurity.formLogin();
        /**
         * Eğer kullanıcıya kendi login formumuzu göstermek istiyor iseniz o zaman kendi formunuza
         * izin vererek yönlendirme işlemi yapmalısınız.
         */
        //httpSecurity.formLogin().loginPage("/mylogin.html");

        /**
         * Auth servis üzerinden kendisini doğrulayan bir kişinin isteklerinin nasıl işleyeceğini çözmemiz gerekiyor.
         * Kişinin elinde olan token bilgisi okunarak bu kişiye yetkileri dahilinde geçerli olan token üzerinden
         * oturum izni verilecek, böylece işi her seferinde kendini doğrulamak zorunda kalmayacak.
         * Bunu başarmak için önce filter işle
         */
        httpSecurity.addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
