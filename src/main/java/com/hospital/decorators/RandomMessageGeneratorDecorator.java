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

        String message = iGenerator.generateRandomMessage();
        if (message != null)
        {
            System.out.println("generated message is:" + message);
        }

        return message;
    }
}