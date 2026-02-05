package com.example.fb_streamingplatform;

import Infrastructure.DbConfig;
import Model.Movie;
import Model.User;
import Repository.Favorite.MySqlFavoriteRepository;
import Repository.Movie.MySqlMovieRepository;
import Repository.User.MySqlUserRepository;
import Service.StreamingService;
import javafx.application.Application;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static DbConfig dbConfig = new DbConfig();
    private static Scanner input = new Scanner(System.in);
    private static MySqlMovieRepository mRepo = new MySqlMovieRepository(dbConfig);
    private static MySqlUserRepository uRepo = new MySqlUserRepository(dbConfig);
    private static MySqlFavoriteRepository fRepo = new MySqlFavoriteRepository(dbConfig);
    private static StreamingService service = new StreamingService(mRepo, uRepo, fRepo);

    public static void main(String[] args)  {


        dbConfig.testConnection();

        service.movieCount();
        System.out.println(" ");

        List<Movie> allMovies = service.getAllMovies();
        List<User> allUsers = service.getAllUsers();


        for (Movie movie : allMovies) {

            System.out.println(movie.getTitle());
        }
        System.out.println(" ");
        for (User user : allUsers) {
            System.out.println(user.getName());
        }
    }
}