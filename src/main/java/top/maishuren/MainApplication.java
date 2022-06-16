package top.maishuren;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.maishuren.factory.SpringVerticleFactory;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-06-16 21:23
 **/
@Configuration
@ComponentScan("top.maishuren")
public class MainApplication {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        ApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class);

        VerticleFactory verticleFactory = context.getBean(SpringVerticleFactory.class);

        // The verticle factory is registered manually because it is created by the Spring container
        vertx.registerVerticleFactory(verticleFactory);

        // Scale the verticles on cores: create 4 instances during the deployment
        DeploymentOptions options = new DeploymentOptions().setInstances(4);
        vertx.deployVerticle(verticleFactory.prefix() + ":" + MainVerticle.class.getName(), options);
    }
}
