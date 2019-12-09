package ru.javawebinar.basejava.streams;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        MainStream mainStream = new MainStream();
        int[] arr = mainStream.createNewArray();

        System.out.println("Исходный массив : " + Arrays.toString(arr));
        System.out.println("Минимально возможное число из уникальных цифр : " + mainStream.minValue(arr));

        List<Integer> list = mainStream.oddOrEven(Arrays.stream(arr).boxed().collect(Collectors.toList()));
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
    }

    public int[] createNewArray() {
        SecureRandom rnd = new SecureRandom();
        int length = rnd.nextInt(6) + 1;
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            arr[i] = rnd.nextInt(9) + 1;
        }
        return arr;
    }

    public int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((a, b) -> {
            return a * 10 + b;
        }).orElse(0);
    }

    private List<Integer> oddOrEven(List<Integer> integers) {
        int summ = integers.stream().reduce(0, (integer, integer2) -> integer + integer2);

        return integers.stream().filter(element -> summ % 2 == element % 2).collect(Collectors.toList());
    }
}
