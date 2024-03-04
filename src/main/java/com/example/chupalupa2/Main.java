package com.example.chupalupa2;

import com.sun.jna.Platform;

import java.io.Console;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            System.err.println("Usage: Main <pid> <address> <value>");
            System.exit(1);
        }

        // Получение PID процесса из аргументов командной строки
        int pid = Integer.parseInt(args[0]);

        // Получение адреса из аргументов командной строки
        String addressStr = args[1];

        // Получение значения для записи из аргументов командной строки
        int valueToWrite = Integer.parseInt(args[2]);

        // Создание экземпляра MyMemory
        MyMemory myMemory = new MyMemory(pid, addressStr);

        // Чтение значения из адреса
        int readValue = myMemory.read();
        System.out.println("Read value from address " + addressStr + ": " + readValue);

        // Запись значения в адрес
        myMemory.write(valueToWrite);
        System.out.println("Wrote value " + valueToWrite + " to address " + addressStr);
    }
}