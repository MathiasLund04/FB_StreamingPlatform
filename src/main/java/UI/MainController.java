package UI;

import Exceptions.ValidationException;
import Infrastructure.DbConfig;
import Model.Movie;
import Model.User;
import Exceptions.DataAccessException;
import Repository.Favorite.MySqlFavoriteRepository;
import Repository.Movie.MySqlMovieRepository;
import Repository.User.MySqlUserRepository;
import Service.StreamingService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML private TableView<Movie> mTable;
    @FXML private TableColumn<Movie, String> colTitle;
    @FXML private TableColumn<Movie, Double> colRating;
    @FXML private TableColumn<Movie, String> colGenre;
    @FXML private TableView<Movie> fTable;
    @FXML private TableColumn<Movie, String> favTitle;
    @FXML private TableColumn<Movie, Double> favRating;
    @FXML private TableColumn<Movie, String> favGenre;
    @FXML private TableView<User> uTable;
    @FXML private TableColumn<User, String> userName;
    @FXML private TableColumn<User, String> userEmail;
    @FXML private TableColumn<User, String> userSubsription;

    @FXML private Label emailTxt;
    @FXML private Label userTxt;
    @FXML private Label lblStatus;
    @FXML private TextField searchTxt;;


    private final ObservableList<Movie> items = FXCollections.observableArrayList();
    private final ObservableList<Movie> favItems = FXCollections.observableArrayList();
    private final ObservableList<User> users = FXCollections.observableArrayList();

    private StreamingService service;


    @FXML private void initialize() {

        DbConfig db = new DbConfig();
        MySqlMovieRepository movieRepo = new MySqlMovieRepository(db);
        MySqlUserRepository userRepo = new MySqlUserRepository(db);
        MySqlFavoriteRepository favRepo = new MySqlFavoriteRepository(db);
        service = new StreamingService(movieRepo,userRepo,favRepo);

        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        colRating.setCellValueFactory(cell-> new SimpleDoubleProperty(cell.getValue().getRating()).asObject());
        colGenre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGenre()));

        favTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        favRating.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getRating()).asObject());
        favGenre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGenre()));

        userName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        userEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        userSubsription.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSubscriptionType()));

        uTable.setItems(users);
        fTable.setItems(favItems);
        mTable.setItems(items);

        refreshTable();

    }

    @FXML
    private void refreshTable() {
        try {
            mTable.getItems().clear();
            mTable.getItems().setAll(service.getAllMovies());
            uTable.getItems().clear();
            uTable.getItems().setAll(service.getAllUsers());
            fTable.getItems().clear();
            emailTxt.getText();
            userTxt.setText("Name: ");
            searchTxt.getText();
            lblStatus.setText("Loaded available movies and users");

        } catch (DataAccessException dae) {

            dae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAdd() {
        Movie selected = mTable.getSelectionModel().getSelectedItem();
        String email = emailTxt.getText();
        if( selected == null ) {
            lblStatus.setText("Please select a movie");
            return;
        } else if (email.isBlank()) {
            lblStatus.setText("Please select a user");
            return;
        }
        try {
            service.addFavoriteByEmail(email, selected.getId());
            fTable.getItems().setAll(service.findFavoriteByEmail(email));

        } catch (DataAccessException dae) {
            throw new DataAccessException("Error adding to favorite try again later");
        }
    }
    @FXML
    private void onRemove() {
        Movie selected = fTable.getSelectionModel().getSelectedItem();
        String email = emailTxt.getText();

        if( selected == null ) {
            lblStatus.setText("Please select a movie");
            return;
        }
        try {
            service.removeFavoriteByEmail(email, selected.getId());
            fTable.getItems().setAll(service.findFavoriteByEmail(email));
        } catch (DataAccessException dae) {
            throw new DataAccessException("Error removing from favorite try again later");
        }

    }
    @FXML
    private void onSearch() {
        String email =  searchTxt.getText();
        try{
            Optional<User> user = service.findByEmail(email);
            userTxt.setText("Name: " + user.get().getName());
            fTable.getItems().setAll(service.findFavoriteByEmail(email));
            emailTxt.setText("Selected Email: " + email);

            if(fTable.getItems().isEmpty()){
                lblStatus.setText("No favorites found for this user");
            } else{
                lblStatus.setText(" Found " + service.favCount(user.get().getId()) +
                        " Favorite movies for " + user.get().getName());
            }

        }
        catch(ValidationException e) {
            lblStatus.setText("Status: "+ e.getMessage());
        }
        catch (DataAccessException dae) {
            dae.printStackTrace();
            lblStatus.setText("Search error: " + dae.getMessage());
        }


    }


}
