package com.example.fb_streamingplatform;

import Infrastructure.DbConfig;
import Repository.Movie.MySqlMovieRepository;
import Service.StreamingService;
import javafx.application.Application;

import java.util.Scanner;

public class Main {

    private static DbConfig dbConfig = new DbConfig();
    private static Scanner input = new Scanner(System.in);
    private static MySqlMovieRepository mRepo = new MySqlMovieRepository(dbConfig);
    private static StreamingService service = new StreamingService(mRepo);

    public static void main(String[] args)  {

        service.movieCount();

    }
}