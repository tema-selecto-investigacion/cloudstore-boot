package eu.cloudscale.showcase;

import eu.cloudscale.showcase.db.generate.Generate;
import eu.cloudscale.showcase.db.generate.GenerateHibernate;
import eu.cloudscale.showcase.db.generate.IGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class Application {

    @Autowired
    private Generate generate;

    @Autowired
    private IGenerate generateHibernate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Component
//    class DataInitializer implements ApplicationRunner{
//
//        @Override
//        public void run(ApplicationArguments args) throws Exception{
//
//            generate.generate(generateHibernate, 100);
//
//        }
//
//    }
}
