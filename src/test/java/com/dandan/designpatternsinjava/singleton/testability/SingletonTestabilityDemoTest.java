package com.dandan.designpatternsinjava.singleton.testability;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTestabilityDemoTest {

    @Test
    public void isSingletonTest()
    {
        SingletonDatabase db = SingletonDatabase.getInstance();
        SingletonDatabase db2 = SingletonDatabase.getInstance();
        assertSame(db, db2);
        assertEquals(1, SingletonDatabase.getCount());
    }

    @Test
    public void singletonTotalPopulationTest()
    {
        // testing on a live database
        SingletonRecordFinder rf = new SingletonRecordFinder();
        List<String> names = List.of("Seoul", "Mexico City");
        int tp = rf.getTotalPopulation(names);
        assertEquals(17500000+17400000, tp);
    }

    /**
     * DummyDatabase
     */
    @Test
    public void dependentPopulationTest()
    {
        DummyDatabase db = new DummyDatabase();
        ConfigurableRecordFinder rf = new ConfigurableRecordFinder(db);
        assertEquals(4, rf.getTotalPopulation(
                List.of("alpha", "gamma")
        ));
    }

    /**
     * SingletonDatabase
     */
    @Test
    public void dependentPopulationTest2()
    {
        SingletonDatabase db = SingletonDatabase.getInstance();
        ConfigurableRecordFinder rf = new ConfigurableRecordFinder(db);
        assertEquals(17500000+17400000, rf.getTotalPopulation(
                List.of("Seoul", "Mexico City")
        ));
//        ConfigurableRecordFinder rf = new ConfigurableRecordFinder(db);
//        assertEquals(4, rf.getTotalPopulation(
//                List.of("alpha", "gamma")
//        ));
    }

    @Test
    public void dependentPopulationTest3()
    {
        SingletonDatabase db = SingletonDatabase.getInstance();
        String city = "Tokyo";
        int pop = db.getPopulation(city);
        System.out.println(
                String.format("%s has population %d", city, pop)
        );

    }

}