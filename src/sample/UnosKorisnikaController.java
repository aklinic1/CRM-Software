package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.awt.*;

public class UnosKorisnikaController {

    public Korisnik korisnik = null;
    public TextField txtIme;
    public TextField txtPrezime;
    public TextField txtEmail;
    public DatePicker datePicker;

    public void actionOdustani(ActionEvent actionEvent){
        Stage stage = (Stage) txtIme.getScene().getWindow();
        stage.close();
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void actionDodaj(ActionEvent actionEvent){
        if(txtIme.getText().trim().isEmpty() || txtPrezime.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("Neispravan unos korisnika");
            alert.setContentText("Prije potvrde unesite ime i prezime korisnika");
            alert.showAndWait();
            if(txtIme.getText().trim().isEmpty())
                txtIme.requestFocus();
            else txtPrezime.requestFocus();
        }
        else{
            korisnik = new Korisnik();
            korisnik.setIme(txtIme.getText());
            korisnik.setPrezime(txtPrezime.getText());
            korisnik.setEmail(txtEmail.getText());
            korisnik.setDatumRodjenja(datePicker.getValue());
            Stage stage = (Stage) txtIme.getScene().getWindow();
            stage.close();
        }

    }


}
