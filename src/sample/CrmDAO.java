package sample;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CrmDAO {
    private static CrmDAO instance;
    private Connection connection;

    private PreparedStatement dajKorisnikeUpit, dodajKorisnikaUpit, dajIdZadnjegKorisnikaUpit, izbrisiKorisnikaUpit,
        dajProizvodeUpit, dodajProizvodUpit, izbrisiProizvodUpit;

    public static CrmDAO getInstance(){
        if(instance == null) instance = new CrmDAO();
        return instance;
    }

    private CrmDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:crm.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            dajKorisnikeUpit = connection.prepareStatement("SELECT * FROM korisnici");
            dodajKorisnikaUpit = connection.prepareStatement("INSERT INTO korisnici VALUES(?, ?, ?, ?, ?, ?, ?)");
            dajIdZadnjegKorisnikaUpit = connection.prepareStatement("SELECT MAX(ID) FROM korisnici");
            izbrisiKorisnikaUpit = connection.prepareStatement("DELETE FROM korisnici WHERE id=?");
            dajProizvodeUpit = connection.prepareStatement("SELECT * FROM proizvodi");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private Korisnik dajKorisnika(ResultSet rs) {
        Korisnik korisnik = null;
        try {
            int id = rs.getInt(1);
            String ime, prezime, email, datumRodjenja, spol;
            ime = rs.getString(2);
            prezime = rs.getString(3);
            email = rs.getString(4);
            spol = rs.getString(5);

            datumRodjenja = rs.getString(5);
            LocalDate datum = null;
            if(datumRodjenja != null) {
                String[] rez = datumRodjenja.split("\\.");
                 datum = LocalDate.of(Integer.parseInt(rez[2]), Integer.parseInt(rez[1]), Integer.parseInt(rez[0]));
            }
            korisnik = new Korisnik(id, ime, prezime, email, datum, spol);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return korisnik;

    }

    public Proizvod dajProizvod(ResultSet rs){
        Proizvod proizvod = new Proizvod();
        try {
            proizvod.setId(rs.getInt(1));
            proizvod.setNaziv(rs.getString(2));
            proizvod.setBrend(rs.getString(3));
            proizvod.setCijena(rs.getDouble(4));
            proizvod.setKolicina(rs.getInt(5));
            proizvod.setPopust(rs.getInt(6));
            proizvod.setDetaljneInformacije(rs.getString(7));
            proizvod.setTagovi(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return proizvod;
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
    public ArrayList<Proizvod> dajProizvode(){
        ArrayList<Proizvod> proizvodi = new ArrayList<>();
        try {
            ResultSet rs = dajProizvodeUpit.executeQuery();

            while(rs.next()){
                proizvodi.add(dajProizvod(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return proizvodi;
    }
    public void dodajKorisnika(Korisnik korisnik) throws SQLException {

        dodajKorisnikaUpit.setInt(1,korisnik.getId());
        dodajKorisnikaUpit.setString(2, korisnik.getIme());
        dodajKorisnikaUpit.setString(3, korisnik.getPrezime());
        dodajKorisnikaUpit.setString(4, korisnik.getEmail());
        dodajKorisnikaUpit.setString(5, korisnik.getDatumIspis());
        dodajKorisnikaUpit.setString(6, korisnik.getSpol());
        dodajKorisnikaUpit.executeUpdate();
    }
    public int dajIdZadnjegKorisnika() throws SQLException {
        ResultSet rs = dajIdZadnjegKorisnikaUpit.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        return id;
    }
    public void izbrisiKorisnika(int id){
        try {
            izbrisiKorisnikaUpit.setInt(1, id);
            izbrisiKorisnikaUpit.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
