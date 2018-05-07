package lt.vu.mif.utils.implementations;

import java.util.UUID;
import lt.vu.mif.utils.interfaces.ITokenGenerator;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator implements ITokenGenerator {

    //TODO: Think about token generation alternatives
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}