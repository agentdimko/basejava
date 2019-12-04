package ru.javawebinar.basejava.deadlock;

public class DeadLockTest {
    public static void main(String[] args) throws InterruptedException {
        ResourceA resA = new ResourceA();
        ResourceB resB = new ResourceB();

        Thread one = new Thread(() -> {
            System.out.println("In thread one");
            try {
                resA.test("Thread one", resB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        Thread two = new Thread(() -> {
            System.out.println("In thread two");
            try {
                resB.test("Thread two", resA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("Threads completed!");

    }

}
