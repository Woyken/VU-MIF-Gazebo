package lt.vu.mif;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    //TODO: Think about token generation alternatives
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

}