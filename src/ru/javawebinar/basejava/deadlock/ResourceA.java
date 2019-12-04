package ru.javawebinar.basejava.deadlock;

public class ResourceA {
    synchronized void test(String threadName, ResourceB resB) throws InterruptedException {
        System.out.println("Thread " + threadName + " called method test in ResourceA");
        Thread.sleep(400);
        resB.test(threadName, this);
    }
}
