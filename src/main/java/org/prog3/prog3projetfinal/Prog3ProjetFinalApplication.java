package org.prog3.prog3projetfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
public class Prog3ProjetFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Prog3ProjetFinalApplication.class, args);
    }
}
