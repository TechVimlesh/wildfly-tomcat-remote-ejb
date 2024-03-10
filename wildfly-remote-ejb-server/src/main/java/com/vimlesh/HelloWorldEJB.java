package com.vimlesh;

import javax.ejb.Stateless;
@Stateless
public class HelloWorldEJB implements HelloWorld {
    @Override
    public String getHelloWorldMessage() {
        return "Hello World from WildFly!";
    }
}
