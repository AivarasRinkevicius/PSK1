package com.hospital.decorators;

import lombok.NoArgsConstructor;

import javax.enterprise.context.RequestScoped;
import java.util.Random;

@NoArgsConstructor
@RequestScoped
public class RandomMessageGenerator implements IGenerator{

    public String generateRandomMessage() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedMessage = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedMessage;
    }
}
