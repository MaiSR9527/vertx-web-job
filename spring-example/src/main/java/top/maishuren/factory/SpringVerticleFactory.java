package top.maishuren.factory;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-06-16 21:23
 **/
@Component
public class SpringVerticleFactory implements VerticleFactory, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public String prefix() {
        // Just an arbitrary string which must uniquely identify the verticle factory
        return "myapp";
    }

    @Override
    public void init(Vertx vertx) {
    }

    @Override
    public void createVerticle(String verticleName, ClassLoader classLoader, Promise<Callable<Verticle>> promise) {
        // Our convention in this example is to give the class name as verticle name
        String clazz = VerticleFactory.removePrefix(verticleName);
        promise.complete(() -> (Verticle) applicationContext.getBean(Class.forName(clazz)));
    }

    @Override
    public void close() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
