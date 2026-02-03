package com.example.fb_streamingplatform;

import Infrastructure.DbConfig;
import javafx.application.Application;

import java.util.Scanner;

public class Main {
           private static DbConfig dbConfig = new DbConfig();
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args, Scanner input)  {



            dbConfig.testConnection();

    }

}
