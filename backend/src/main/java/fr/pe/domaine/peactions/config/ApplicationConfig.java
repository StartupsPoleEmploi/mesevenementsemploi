package fr.pe.domaine.peactions.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationConfig {


   @Bean
   public CustomMapper customMapper() {
      return new CustomMapper();
   }

   @Bean
   @Primary
   public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
      return builder.build();
//    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
   }

}
