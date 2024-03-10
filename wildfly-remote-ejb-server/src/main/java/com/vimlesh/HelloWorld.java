package com.vimlesh;

import javax.ejb.Remote;

@Remote
public interface HelloWorld {
    public String getHelloWorldMessage();
}
