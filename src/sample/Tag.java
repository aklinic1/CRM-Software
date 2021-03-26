package sample;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class Tag {
    private String naziv;

    public Tag(String naziv) {
        this.naziv = dajMalimSlovima(naziv);
    }

    private static String dajMalimSlovima(String tag){
        return tag.toLowerCase();
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = dajMalimSlovima(naziv);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(dajMalimSlovima(naziv), dajMalimSlovima(tag.naziv));
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    @Override
    public String toString() {
        return naziv;
    }
}
