package com.hospital.decorators;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;


import java.util.Random;

@Decorator
public class RandomMessageGeneratorDecorator implements IGenerator{

    @Inject
    @Delegate
    @Any
    IGenerator iGenerator;

    @Override
    public String generateRandomMessage() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 5;
        Random random = new Random();
        String generatedMessage = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return generatedMessage;
    }
}