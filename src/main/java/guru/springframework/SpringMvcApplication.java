package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringMvcApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringMvcApplication.class, args);

        System.out.println("***** Beans *****");
        System.out.println(ctx.getBeanDefinitionCount());
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);

        }
    }
}
