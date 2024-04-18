package com.dandan.designpatternsinjava.singleton;


class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("initializing a lazy singleton");
    }
//
//    public static synchronized LazySingleton getInstance() {
//        if (instance == null) {
//            instance = new LazySingleton();
//        }
//        return instance;
//    }

    //double-checked locking
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

}

class InnerStaticSingleton {
    private InnerStaticSingleton() {
    }

    private static class Impl {
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
    }

    public InnerStaticSingleton getInstance() {
        return Impl.INSTANCE;
    }
}

public class LazySingletonDemo {
    public static void main(String[] args) {

    }
}
