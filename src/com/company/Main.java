package com.company;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

    public static void main(String[] args)  {
        Scanner in = new Scanner(System.in);
        System.out.println("Добро пожаловать в симулятор склада");
        System.out.print("Введите максимальный размер склада: ");
        int maxSize = in.nextInt();
        System.out.print("Введите количество имеющихся продуктов: ");
        int productCount = in.nextInt();
        System.out.print("Введите начальное количество покупателей: ");
        int customerNumber = in.nextInt();
        MainForm mainForm = new MainForm(productCount,maxSize,customerNumber);
        //MainForm mainForm = new MainForm(2500,5000,10);
        mainForm.setVisible(true);
        mainForm.pack();
        System.out.print("Симуляция запущена. Нажмите \"Старт\"" );

    }
}










