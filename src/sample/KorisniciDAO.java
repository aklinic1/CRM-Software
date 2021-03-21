package sample;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class KorisniciDAO {
    private static KorisniciDAO instance;
    private Connection connection;

    private PreparedStatement dajKorisnikeUpit, dodajKorisnikaUpit, dajIdZadnjegKorisnikaUpit;

    public static KorisniciDAO getInstance(){
        if(instance == null) instance = new KorisniciDAO();
        return instance;
    }

    private KorisniciDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:crm.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            dajKorisnikeUpit = connection.prepareStatement("SELECT * FROM korisnici");
            dodajKorisnikaUpit = connection.prepareStatement("INSERT INTO korisnici VALUES(?, ?, ?, ?, ?)");
            dajIdZadnjegKorisnikaUpit = connection.prepareStatement("SELECT MAX(ID) FROM korisnici");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private Korisnik dajKorisnika(ResultSet rs) {
        Korisnik korisnik = null;
        try {

            String ime, prezime, email, datumRodjenja;
            ime = rs.getString(2);
            prezime = rs.getString(3);
            email = rs.getString(4);

            datumRodjenja = rs.getString(5);
            LocalDate datum = null;
            if(datumRodjenja != null) {
                String[] rez = datumRodjenja.split("\\.");
                 datum = LocalDate.of(Integer.parseInt(rez[2]), Integer.parseInt(rez[1]), Integer.parseInt(rez[0]));
            }
            korisnik = new Korisnik(ime, prezime, email, datum);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return korisnik;

    }
    public ArrayList<Korisnik> dajKorisnike(){
        ArrayList<Korisnik> korisnici = new ArrayList<>();
        try {
            ResultSet rs = dajKorisnikeUpit.executeQuery();

            while(rs.next())
                korisnici.add(dajKorisnika(rs));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return korisnici;
    }
    public void dodajKorisnika(Korisnik korisnik) throws SQLException {
        ResultSet rs = dajIdZadnjegKorisnikaUpit.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        dodajKorisnikaUpit.setInt(1,id + 1);
        dodajKorisnikaUpit.setString(2, korisnik.getIme());
        dodajKorisnikaUpit.setString(3, korisnik.getPrezime());
        dodajKorisnikaUpit.setString(4, korisnik.getEmail());
        dodajKorisnikaUpit.setString(5, korisnik.getDatumIspis());
        dodajKorisnikaUpit.executeUpdate();
    }

}
