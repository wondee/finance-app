package info.wondee.app.financeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("info.wondee.app.financeapp")
public class FinanceApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(FinanceApplication.class, args);

  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer objectMapperCustomizer() {
  	return builder -> {
  		builder
  			.deserializerByType(FinanceMonth.class, new FinanceMonthDeserializer())
  			.serializerByType(FinanceMonth.class, new FinanceMonthSerializer())
  			;
  	};
  }
  
}
