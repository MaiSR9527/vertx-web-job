package top.maishuren.component;

import org.springframework.stereotype.Component;

/**
 * @author MaiShuRen
 * @site https://www.maishuren.top
 * @since 2022-06-16 21:27
 **/
@Component
public class Hello {

    public String sayHello(String name) {
        return "Hello " + name;
    }
}
