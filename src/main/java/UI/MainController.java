package UI;

import Infrastructure.DbConfig;
import Model.Movie;
import Model.User;
import Exceptions.DataAccessException;
import Repository.Movie.MySqlMovieRepository;
import Repository.User.MySqlUserRepository;
import Service.StreamingService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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


    @FXML private Label lblStatus;
    @FXML private TextField searchTxt;


    private final ObservableList<Movie> items = FXCollections.observableArrayList();
    private final ObservableList<Movie> favItems = FXCollections.observableArrayList();

    private StreamingService service;


    @FXML private void initialize() {

        DbConfig db = new DbConfig();
        MySqlMovieRepository mRepo = new MySqlMovieRepository(db);
        MySqlUserRepository uRepo = new MySqlUserRepository(db);
        service = new StreamingService(mRepo,uRepo);

        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        colRating.setCellValueFactory(cell-> new SimpleDoubleProperty(cell.getValue().getRating()).asObject());
        colGenre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGenre()));

        favTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        favRating.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getRating()).asObject());
        favGenre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGenre()));
        fTable.setItems(items);
        mTable.setItems(items);

        refreshTable();

    }

    @FXML
    private void refreshTable() {
        try {
            List<Movie> all =  service.getAllMovies();
            items.addAll(all);
            lblStatus.setText("Loaded " + all.size() + " movies");

        } catch (DataAccessException dae) {

            dae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSearch() {
        String email =  searchTxt.getText().trim();
        try{
            Optional<User> user = service.findIdByEmail(email);

            fTable.getItems().setAll(service.)

        } catch (DataAccessException dae) {
            dae.printStackTrace();
            lblStatus.setText("Search error: " + dae.getMessage());
        }

    }


}
