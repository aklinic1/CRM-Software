package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class GlavnaController {
    private CrmDAO dao;
    private ObservableList<Korisnik> listaKorisnika;
    private ObservableList<Proizvod> listaProizvoda;
    public TableView tableView;
    public TableColumn columnIme, columnPrezime, columnEmail, columnDatumRodjenja;
    public Set<Tag> tagovi = new HashSet<>();
    public ListView listOpcije;
    public ObservableList<String> listaOpcija;

    public GlavnaController(){
        dao = CrmDAO.getInstance();
        listaKorisnika = FXCollections.observableArrayList(dao.dajKorisnike());
        listaProizvoda = FXCollections.observableArrayList(dao.dajProizvode());
        listaOpcija = FXCollections.observableArrayList();
        listaOpcija.add("Proizvodi");
        listaOpcija.add("Korisnici");
    }

    private void dodajVrijednostiKolonamaTabele(String c1, String c2, String c3, String c4){
        columnIme.setCellValueFactory(new PropertyValueFactory(c1));
        columnPrezime.setCellValueFactory(new PropertyValueFactory(c2));
        columnEmail.setCellValueFactory(new PropertyValueFactory(c3));
        columnDatumRodjenja.setCellValueFactory(new PropertyValueFactory(c4));
    }
    private void postaviImenaKolonaTabele(String c1, String c2, String c3, String c4){
        columnIme.setText(c1);
        columnPrezime.setText(c2);
        columnEmail.setText(c3);
        columnDatumRodjenja.setText(c4);
    }

    @FXML
    public void initialize(){

        tableView.setItems(listaKorisnika);
        dodajVrijednostiKolonamaTabele("ime", "prezime", "email", "datumIspis");

        listOpcije.setItems(listaOpcija);
        listOpcije.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {
                if(click.getClickCount() == 2){

                    if(listOpcije.getSelectionModel().getSelectedItem().equals("Proizvodi")) {
                        if(!tableView.getColumns().get(0).equals("Naziv")) {
                            postaviImenaKolonaTabele("Naziv", "Brend", "Cijena(KM)", "Količina");
                            tableView.setItems(listaProizvoda);
                            dodajVrijednostiKolonamaTabele("naziv", "brend", "cijena", "kolicina");
                        }
                    }
                    else if(listOpcije.getSelectionModel().getSelectedItem().equals("Korisnici")){
                        if(!tableView.getColumns().get(0).equals("Ime")){
                            postaviImenaKolonaTabele("Ime", "Prezime", "E-mail", "Datum rođenja");
                            tableView.setItems(listaKorisnika);
                            dodajVrijednostiKolonamaTabele("ime", "prezime", "email", "datumIspis");
                        }
                    }
                }
            }
        });
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

                if(korisnik != null){
                    try {
                        korisnik.setId(dao.dajIdZadnjegKorisnika() + 1);
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
        Korisnik korisnik = (Korisnik) tableView.getSelectionModel().getSelectedItem();
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
