package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class GlavnaController {
    private KorisniciDAO dao;
    private ObservableList<Korisnik> listaKorisnika;
    public TableView<Korisnik> tableView;
    public TableColumn columnIme, columnPrezime, columnEmail, columnDatumRodjenja;


    public GlavnaController(){
        dao = KorisniciDAO.getInstance();
        listaKorisnika = FXCollections.observableArrayList(dao.dajKorisnike());
    }

    @FXML
    public void initialize(){
        tableView.setItems(listaKorisnika);
        columnIme.setCellValueFactory(new PropertyValueFactory("ime"));
        columnPrezime.setCellValueFactory(new PropertyValueFactory("prezime"));
        columnEmail.setCellValueFactory(new PropertyValueFactory("email"));
        columnDatumRodjenja.setCellValueFactory(new PropertyValueFactory("datumIspis"));
    }


    public void actionDodajKorisnika(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/unosKorisnika.fxml"));
            UnosKorisnikaController controller = new UnosKorisnikaController();
            loader.setController(controller);
            root = loader.load();
            stage.setTitle("Unos korisnika");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            stage.setMinHeight(stage.getHeight());
            stage.setMinWidth(stage.getWidth());
            stage.setOnHiding(event -> {
                Korisnik korisnik =controller.getKorisnik();
                try {
                    korisnik.setId(dao.dajIdZadnjegKorisnika() + 1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if(korisnik != null){
                    try {
                        dao.dodajKorisnika(korisnik);
                        listaKorisnika.setAll(dao.dajKorisnike());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionIzbrisiKorisnika(ActionEvent actionEvent) {
        Korisnik korisnik = tableView.getSelectionModel().getSelectedItem();
        if(korisnik != null) {
            int id = korisnik.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja");
            String imePrezime = korisnik.getIme() + " " + korisnik.getPrezime();
            alert.setHeaderText("Brisanje korisnika "+ imePrezime);
            alert.setContentText("Da li ste sigurni da želite obrisati korisnika " + imePrezime +"?");
            alert.setResizable(true);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                dao.izbrisiKorisnika(id);
                listaKorisnika.setAll(dao.dajKorisnike());
            }

        }
    }
}
