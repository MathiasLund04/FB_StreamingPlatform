package UI;

import Infrastructure.DbConfig;
import Model.Movie;
import Repository.DataAccessException;
import Repository.Movie.MySqlMovieRepository;
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

    @FXML private Label lblStatus;
    @FXML private TextField searchTxt;


    private final ObservableList<Movie> items = FXCollections.observableArrayList();

    private StreamingService service;


    @FXML private void initialize() {

        DbConfig db = new DbConfig();
        MySqlMovieRepository mRepo = new MySqlMovieRepository(db);
        service = new StreamingService(mRepo);

        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitle()));
        colRating.setCellValueFactory(cell-> new SimpleDoubleProperty(cell.getValue().getRating()).asObject());
        colGenre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGenre()));

        mTable.setItems(items);

        refreshTable();

    }

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
        String movie =  searchTxt.getText();

        try{
            Optional<Movie> optional = service.findByTitle(movie);
            mTable.getItems().setAll(optional.get());
        } catch (DataAccessException dae) {
            dae.printStackTrace();
        }
        refreshTable();
    }


}
