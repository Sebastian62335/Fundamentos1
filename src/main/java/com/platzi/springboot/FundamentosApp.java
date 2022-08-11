package com.platzi.springboot;

import com.platzi.springboot.bean.implementation.MyBean;
import com.platzi.springboot.component.MyBeanCommandLineRunner;
import com.platzi.springboot.component.MyComponent;
import com.platzi.springboot.entity.User;
import com.platzi.springboot.pojo.properties.UserProperties;
import com.platzi.springboot.repository.UserRepository;
import com.platzi.springboot.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class FundamentosApp implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(this.getClass());

    private MyBean myBean;
    private MyBeanCommandLineRunner beanCommandLineRunner;
    private MyComponent myComponent;
    private UserProperties userProperties;
    private UserRepository userRepository;


    public FundamentosApp(MyBean myBean, @Qualifier("cualquierNombre") MyComponent myComponent,
                          UserProperties userProperties, UserRepository userRepository,
                          UserService userService
                       ) {
        this.myBean = myBean;
        this.myComponent = myComponent;
        this.userProperties = userProperties;
        this.userRepository = userRepository;

    }

    @Bean
    public Function<String, String> uppercase() {
        return (String value) -> value.toUpperCase();
    }

    public static void main(String[] args) {
        SpringApplication.run(FundamentosApp.class, args);
    }

    @Override
    public void run(String... args) {

        saveUsersInDb();
        getInformationJpqlFromUser();
    }

    private void getInformationJpqlFromUser(){
        logger.info("Usuario con el metodo findmyuseremail"+userRepository.findMyUserByEmail("john@domain.com").
                orElseThrow(()->new RuntimeException("no se encontro el usuario")));
        userRepository.findByAndSort("Test", Sort.by("id").ascending())
                .stream()
                .forEach(user -> logger.info("Usuario con metodo sort "+user));
        userRepository.findByName("John")
                .stream()
                .forEach(user -> logger.info("usuario con query method"+user));
        logger.info("usuario encontrado con query method find by name and email"+
                userRepository.findUsersByNameAndAndEmail("Daniela", "daniela@domain.com").
                orElseThrow(()->new RuntimeException(" usuario no encontrado")));
        userRepository.findByNameLike("%t%")
                .stream()
                .forEach(user -> logger.info("usuario encontrado con find by namelike"+user));
        userRepository.findUsersByNameOrEmail(null,"Test1@domain.com")
                .stream()
                .forEach(user -> logger.info("usuario encontrado con find by nameoremail"+user));
        userRepository.findByBirthDateBetween(LocalDate.of(2021,03,01),LocalDate.of(2022,05,19))
                .stream()
                .forEach(user -> logger.info("usuario encontrado entre fechas "+user));
        userRepository.findByNameLikeOrderByIdDesc("%Test%")
                .stream()
                .forEach(user -> logger.info("usuario encontrado con find by findByNameLikeOrderByIdDesc"+user));
        userRepository.findByNameContainingOrderByIdDesc("Test")
                .stream()
                .forEach(user -> logger.info("usuario encontrado con find by findByNameContainingOrderByIdDesc"+user));
        logger.info("El usuario a partir de named parameter es "+ userRepository.findByNameOrEmail("John",
                        "john@domain.com")
                .orElseThrow(()->new RuntimeException("No se encontro nada")));

    }


    private void saveUsersInDb() {
        User user1 = new User("John", "john@domain.com", LocalDate.of(2021, 03, 15));
        User user2 = new User("Julie", "julie@domain.com", LocalDate.of(2021, 03, 20));
        User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2021, 03, 25));
        User user4 = new User("Oscar", "oscar@domain.com", LocalDate.now());
        User user5 = new User("Test1", "Test1@domain.com", LocalDate.now());
        User user6 = new User("Test2", "Test2@domain.com", LocalDate.now());
        User user7 = new User("Test3", "Test3@domain.com", LocalDate.now());
        User user8 = new User("Test4", "Test4@domain.com", LocalDate.now());
        User user9 = new User("Test5", "Test5@domain.com", LocalDate.now());
        User user10 = new User("Test6", "Test6@domain.com", LocalDate.now());
        User user11 = new User("Test7", "Test7@domain.com", LocalDate.now());
        User user12 = new User("Test8", "Test8@domain.com", LocalDate.now());
        User user13 = new User("Test9", "Test9@domain.com", LocalDate.now());
        List<User> list = Arrays.asList(user4, user1, user3, user2, user5, user6, user7, user8, user9, user10, user11, user12, user13);
        list.stream().forEach(userRepository::save);


    }
}
