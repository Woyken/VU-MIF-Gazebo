package lt.vu.mif.bl.implementations;

import java.util.UUID;
import lt.vu.mif.bl.interfaces.ITokenGenerator;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator implements ITokenGenerator {

    //TODO: Think about token generation alternatives
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}