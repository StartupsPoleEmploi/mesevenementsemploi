package fr.pe.domaine.peactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class PeActionsDomaineApplication {

    private static final Logger log = LoggerFactory.getLogger(PeActionsDomaineApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PeActionsDomaineApplication.class);
    }


}
