package sample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class JSONFormatter {

    String sadrzaj;
    public void zapisi(File file) {
        try {
            Files.writeString(file.toPath(), sadrzaj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zapisiKorisnike(ArrayList<Korisnik> korisnici) {
        File file = new File("resources/JSON/korisnici.json");
//        String zapis = "";
//
//        if(korisnici.size() > 1)
//           zapis = "[ ";
//        int br = 0;
        JSONArray jsonArray = new JSONArray();
        for(Korisnik korisnik: korisnici){
//            if(br !=0) zapis += " , \n";
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("id", korisnik.getId());
            jsonObject.put("ime", korisnik.getIme());
            jsonObject.put("prezime", korisnik.getPrezime());
            jsonObject.put("spol" , korisnik.getSpol());
            jsonObject.put("email", korisnik.getEmail());
            jsonObject.put("datumRodjenja", korisnik.getDatumRodjenja().toString());
            jsonArray.put(jsonObject);
//            zapis += jsonObject.toString();
//            br++;
        }
//        if(korisnici.size() > 1)
//            zapis += " ]";
        sadrzaj = jsonArray.toString();
        zapisi(file);

    }

    public void zapisiProizvode(ArrayList<Proizvod> proizvodi) {
        File file = null;
        String zapis = "";
        file = new File("resources/JSON/proizvodi.json");
        JSONArray jsonArray = new JSONArray();

//        if(proizvodi.size() > 1)
//            zapis = "[";
//        int br = 0;
        for(Proizvod proizvod: proizvodi){
//            if(br!=0) zapis += " , \n";
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("id", proizvod.getId());
            jsonObject.put("naziv", proizvod.getNaziv());
            jsonObject.put("brend", proizvod.getBrend());
            jsonObject.put("cijena" ,proizvod.getCijena());
            jsonObject.put("kolicina", proizvod.getKolicina());
            jsonObject.put("tagovi", proizvod.getTagovi());
            jsonObject.put("detaljneInformacije", proizvod.getDetaljneInformacije());
            jsonArray.put(jsonObject);
//            zapis += jsonObject.toString();
//            br++;
        }
//        if(proizvodi.size() > 1)
//            zapis += "]";
        sadrzaj = jsonArray.toString();
        zapisi(file);
    }


    public void ucitaj(File file) {
        try {
            Scanner ulaz = new Scanner(new FileReader(file));
            while(ulaz.hasNextLine()){
                sadrzaj += ulaz.nextLine() + '\n';
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Proizvod> getProizvodi() throws JSONException {

        ArrayList<Proizvod> proizvodi = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(sadrzaj);
        int i = 0;
        JSONObject jsonObject = new JSONObject();
        for(i = 0; i < jsonArray.length(); i++){
            jsonObject = jsonArray.getJSONObject(i);
            Proizvod proizvod = new Proizvod();
            proizvod.setNaziv(jsonObject.getString("naziv"));
            proizvod.setKolicina(jsonObject.getInt("kolicina"));
            proizvod.setBrend(jsonObject.getString("brend"));
            proizvod.setCijena(jsonObject.getDouble("cijena"));
            proizvod.setDetaljneInformacije(jsonObject.getString("detaljneInformacije"));
            Set<Tag> tagovi = new HashSet<>();
//            tagovi.addAll(jsonObject.getJSONArray("tagovi"));

//            proizvod.setTagovi(Set.copyOf());
        }
        return null;
    }

    public ArrayList<Korisnik> getKorisnici() {

        return null;
    }
}
