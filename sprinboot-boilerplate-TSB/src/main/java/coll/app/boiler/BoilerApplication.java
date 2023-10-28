package coll.app.boiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"coll.app.boiler"})
public class BoilerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoilerApplication.class, args);
	}

}
