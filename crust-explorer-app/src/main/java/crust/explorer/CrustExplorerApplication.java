package crust.explorer;

import crust.explorer.util.HttpClientHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrustExplorerApplication {

    public static void main(String[] args) {
        HttpClientHandler.setUsePool(true);
        try {
            SpringApplication.run(CrustExplorerApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
