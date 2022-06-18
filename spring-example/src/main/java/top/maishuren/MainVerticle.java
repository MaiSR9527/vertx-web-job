package top.maishuren;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import top.maishuren.component.Hello;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-06-16 21:26
 **/
@Component
// Prototype scope is needed as multiple instances of this verticle will be deployed
@Scope(SCOPE_PROTOTYPE)
public class MainVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    @Autowired
    Hello hello;

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer().requestHandler(request -> {
            String name = request.getParam("name");
            LOG.info("Got request for name: " + name);
            if (name == null) {
                request.response().setStatusCode(400).end("Missing name");
            } else {
                // It's fine to call the greeter from the event loop as it's not blocking
                request.response().end(hello.sayHello(name));
            }
        }).listen(8080, ar -> {
            if (ar.succeeded()) {
                LOG.info("GreetingVerticle started: @" + this.hashCode());
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }
}
