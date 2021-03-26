package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Set;

public class UnosProizvodaController {
    private ObservableSet<Tag> tagovi;
    private ObservableList<Integer> kolicine = FXCollections.observableArrayList();
    public ComboBox<Tag> choiceTagovi;
    public TextField txtNaziv, txtBrend, txtCijena;
    public TextArea txtInformacije;
    public ChoiceBox<Integer> choiceKolicina;

    public Proizvod proizvod = null;

    public Proizvod getProizvod() {
        return proizvod;
    }

    public UnosProizvodaController(Set<Tag> tagovi){
        this.tagovi = FXCollections.observableSet(tagovi);
    }

    public UnosProizvodaController(){
        int i;
        for(i = 0; i < 50; i++){
            kolicine.add(i);
        }
    }

    @FXML
    public void initialize(){
        choiceKolicina.setItems(kolicine);
        //choiceTagovi.setItems((ObservableList<Tag>) tagovi);
    }

    public void actionDodaj(ActionEvent actionEvent){
        String cijena = txtCijena.getText().replaceFirst(",",".");
        boolean unosCijene = true;
        try {
            Double.parseDouble(cijena);
        }catch (Exception e){
            unosCijene = false;
        }

        if(txtNaziv.getText().trim().isEmpty() || unosCijene == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("Neispravan unos proizvoda");
            if (txtNaziv.getText().trim().isEmpty())
                alert.setContentText("Prije potvrde unesite naziv proizvoda!");
            else alert.setContentText("Unesite validnu cijenu proizvoda!");
            alert.showAndWait();
            if (txtNaziv.getText().trim().isEmpty())
                txtNaziv.requestFocus();
            else {
                txtCijena.requestFocus();
            }
        }
        else {
          proizvod = new Proizvod();
          proizvod.setNaziv(txtNaziv.getText());
          proizvod.setBrend(txtBrend.getText());
          proizvod.setCijena(Double.parseDouble(cijena));
          proizvod.setKolicina(choiceKolicina.getValue());
          proizvod.setDetaljneInformacije(txtInformacije.getText());
          Stage stage = (Stage) txtNaziv.getScene().getWindow();
          stage.close();
        }
    }

    public void actionOdustani(ActionEvent actionEvent){

    }


    public void actionUnesiTag(ActionEvent actionEvent){

    }
}
