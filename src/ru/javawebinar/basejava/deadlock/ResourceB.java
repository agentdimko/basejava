package ru.javawebinar.basejava.deadlock;

public class ResourceB {
    synchronized void test(String threadName, ResourceA resA) throws InterruptedException {
        System.out.println("Thread " + threadName + " called method test in ResourceB");
        Thread.sleep(1000);
        resA.test(threadName, this);
    }
}