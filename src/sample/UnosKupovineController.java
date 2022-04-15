package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class UnosKupovineController {
    private ObservableList<Proizvod> proizvodi;
    private ObservableList<Korisnik> korisnici;
    private ObservableList<Proizvod> kupljeniProizvodi;

    public TextField txtCijena, txtPopust;

    public SimpleStringProperty zaPlatiti;
    public SimpleStringProperty zaPlatitiProperty() {
        return zaPlatiti;
    }
    public String getZaPlatiti() {
        return zaPlatiti.get();
    }

    public ArrayList<Kupovina> kupovina = null;
    public ListView<Proizvod> listProizvodi, listKupljeniProizvodi;
    public ChoiceBox<Korisnik> choiceKorisnik;

    public UnosKupovineController(ObservableList<Korisnik> korisnici, ObservableList<Proizvod> proizvodi) {
        zaPlatiti = new SimpleStringProperty("0");
        korisnici.sort(Comparator.comparing(Korisnik::getIme));
        this.proizvodi = proizvodi;
        this.korisnici = korisnici;
        kupljeniProizvodi = FXCollections.observableArrayList();
    }
    @FXML
    public void initialize(){
        listProizvodi.setItems(proizvodi);
        choiceKorisnik.setItems(korisnici);
        choiceKorisnik.setValue(korisnici.get(0));
        txtPopust.textProperty().addListener(
                event-> {
                    Double popust;
                    if(txtPopust.getText().isBlank())
                        popust = 0.;
                    else
                        popust = Double.parseDouble(txtPopust.getText());
                    zaPlatiti.set(String.valueOf(
                            new BigDecimal(Double.valueOf(
                                    txtCijena.getText()) * (1 -  popust/ 100)).
                                    setScale(1, RoundingMode.HALF_UP)));
                });
        txtCijena.textProperty().addListener(
                event-> {
                    Double popust;
                    if(txtPopust.getText().equals(""))
                        popust = 0.;
                    else
                        popust = Double.parseDouble(txtPopust.getText());
                    zaPlatiti.set(String.valueOf(
                            new BigDecimal(Double.valueOf(
                                    txtCijena.getText()) * (1 -  popust/ 100)).
                                    setScale(2, RoundingMode.HALF_UP)));
                });



        listProizvodi.setOnMouseClicked(click -> {
            if(click.getClickCount() == 2){
                Proizvod proizvod = listProizvodi.getSelectionModel().getSelectedItem();
                if(proizvod.getKolicina() > 0) {
                    proizvod.setKolicina(proizvod.getKolicina() - 1);
                    kupljeniProizvodi.add(proizvod);
                    listKupljeniProizvodi.setItems(kupljeniProizvodi);
                    txtCijena.setText(String.valueOf(proizvod.getCijena() + Double.parseDouble(txtCijena.getText())));
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Upozorenje");
                    alert.setContentText("Proizvoda " + proizvod.getNaziv() + " nema na stanju");
                    alert.showAndWait();
                }
            }
        });
        listKupljeniProizvodi.setOnMouseClicked(click ->{
            if(click.getClickCount() == 2){
                Proizvod proizvod = listKupljeniProizvodi.getSelectionModel().getSelectedItem();
                proizvod.setKolicina(proizvod.getKolicina() + 1);
                kupljeniProizvodi.remove(proizvod);
                listKupljeniProizvodi.setItems(kupljeniProizvodi);
            }
        });
    }

    public void actionZavrsiKupovinu(ActionEvent actionEvent){
        kupovina = new ArrayList<>();
        for(Proizvod proizvod: kupljeniProizvodi){
            kupovina.add(new Kupovina(choiceKorisnik.getValue(), proizvod, LocalDate.now(), proizvod.getCijena()));

        }
        Stage stage = (Stage) listProizvodi.getScene().getWindow();
        stage.close();
    }
    public void actionOdustani(ActionEvent actionEvent){
        Stage stage = (Stage) listProizvodi.getScene().getWindow();
        stage.close();
    }

    public ArrayList<Kupovina> getKupovina() {
        return kupovina;
    }
}
