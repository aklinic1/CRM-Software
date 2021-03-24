package sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Korisnik {

    private String ime;
    private String prezime;
    private String email;
    private LocalDate datumRodjenja;
    private ArrayList<Proizvod> kupljeniProizvodi = new ArrayList<>();
    private int id;

    public Korisnik() {
    }

    public Korisnik(int id, String ime, String prezime, String email, LocalDate datumRodjenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.datumRodjenja = datumRodjenja;
        this.id = id;
    }
    public Korisnik(String ime, String prezime, String email, LocalDate datumRodjenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.datumRodjenja = datumRodjenja;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getDatumIspis() {
        if(datumRodjenja == null) return null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return fomatter.format(datumRodjenja);
    }

}
