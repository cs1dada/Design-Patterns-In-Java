package com.dandan.designpatternsinjava.singleton.testability;

import com.google.common.collect.Iterables;
import org.springframework.core.io.ClassPathResource;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

interface Database
{
    int getPopulation(String name);
}

class SingletonDatabase implements Database{
    private Dictionary<String, Integer> capitals = new Hashtable<>();
    private static int instanceCount = 0;

    public static int getCount() {
        return instanceCount;
    }

    private SingletonDatabase() {
        instanceCount++;
        System.out.println("Initializing database");


        try {

            ClassPathResource resource = new ClassPathResource("capitals.txt");
            File f = resource.getFile();
            List<String> lines = Files.readAllLines(f.toPath());
            System.out.println("lines: " + lines);

            Iterables.partition(lines, 2)
                    .forEach(kv -> capitals.put(
                            kv.get(0).trim(),
                            Integer.parseInt(kv.get(1))
                    ));
            System.out.println("capitals: " + capitals);

        } catch (Exception e) {
            // handle it!
            System.err.println(e);
        }
    }

    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    public static SingletonDatabase getInstance() {
        return INSTANCE;
    }

    @Override
    public int getPopulation(String name) {
        return capitals.get(name);
    }
}

class SingletonRecordFinder {
    public int getTotalPopulation(List<String> names) {
        int result = 0;
        for (String name : names)
            result += SingletonDatabase.getInstance().getPopulation(name);
        return result;
    }
}

/**
 * import 不同db,但是可以做一樣的單元測試
 */
class ConfigurableRecordFinder
{
    private Database database;

    public ConfigurableRecordFinder(Database database) {
        this.database = database;
    }

    public int getTotalPopulation(List<String> names)
    {
        int result = 0;
        for (String name : names)
            result += database.getPopulation(name);
        return result;
    }
}

class DummyDatabase implements Database
{
    private Dictionary<String, Integer> data = new Hashtable<>();

    public DummyDatabase() {
        data.put("alpha", 1);
        data.put("beta", 2);
        data.put("gamma", 3);
    }

    @Override
    public int getPopulation(String name)
    {
        return data.get(name);
    }
}

public class SingletonTestabilityDemo {
    public static void main(String[] args) {
        // testing on a live database
        SingletonRecordFinder rf = new SingletonRecordFinder();
        List<String> names = List.of("Seoul", "Mexico City");
        int tp = rf.getTotalPopulation(names);
        System.out.println((17500000 + 17400000) == tp);

    }
}
