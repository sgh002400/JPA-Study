package jpabook.jpashop2;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Jpashop2Application {

	public static void main(String[] args) {

		SpringApplication.run(Jpashop2Application.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module() { //외부에 엔티티를 노출시킬 때 사용하는 방법임 -> 사실 노출하면 안되기 때문에 사용하지 않을듯!
		return new Hibernate5Module();
	}

}
