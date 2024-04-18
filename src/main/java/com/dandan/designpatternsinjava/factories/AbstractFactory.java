package com.dandan.designpatternsinjava.factories;



import javafx.util.Pair;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


interface HotDrink {
    void consume();
}

class Tea implements HotDrink {
    @Override
    public void consume() {
        System.out.println("this tea is delicious");
    }
}

class Coffee implements HotDrink {
    @Override
    public void consume() {
        System.out.println("this coffee is delicious");
    }
}

// factory
interface HotDrinkFactory {
    HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory {
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("put in bag, boil water, pour "
                + amount + "ml, add lemon , enjoy!"
        );
        return new Tea();
    }
}

class CoffeeFactory implements HotDrinkFactory {
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("grind some beans, boil water, pour "
                + amount + "ml, add cream and sugar , enjoy!"
        );
        return new Coffee();
    }
}

//machine
class HotDrinkMachine {
    private List<Pair<String, HotDrinkFactory>> namedFactories = new ArrayList<>();

    public HotDrinkMachine() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // option 2: find all implementors of IHotDrinkFactory
        Set<Class<? extends HotDrinkFactory>> types =
                new Reflections("com.dandan.designpatternsinjava.factories") // ""
                        .getSubTypesOf(HotDrinkFactory.class);
        for (Class<? extends HotDrinkFactory> type : types) {
            namedFactories.add(new Pair<>(
                    type.getSimpleName().replace("Factory", ""),
                    type.getDeclaredConstructor().newInstance()
            ));
        }
    }

    public HotDrink makeDrink() throws IOException {
        System.out.println("Available drinks: ");
        for (int index = 0; index < namedFactories.size(); ++index)
        {
            Pair<String, HotDrinkFactory> item = namedFactories.get(index);
            System.out.println("" + index + ": " + item.getKey());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            String s;
            int i, amount;
            if ((s = reader.readLine()) != null
                    && (i = Integer.parseInt(s)) >= 0
                    && i < namedFactories.size())
            {
                System.out.println("Specify amount: ");
                s = reader.readLine();
                if (s != null
                        && (amount = Integer.parseInt(s)) > 0)
                {
                    return namedFactories.get(i).getValue().prepare(amount);
                }
            }
            System.out.println("Incorrect input, try again.");
        }
    }

}


public class AbstractFactory {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        HotDrinkMachine machine = new HotDrinkMachine();
        HotDrink drink = machine.makeDrink();
        drink.consume();
    }
}
