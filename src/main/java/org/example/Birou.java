package org.example;

import java.text.ParseException;
import java.util.Vector;

public class Birou {
    Vector<Cerere> cereriAsteptare;
    Vector<Cerere> cereriSolutionate;
    Vector<Functionar> vectFunctionari;

    static void StergereCerereBirou(String text, String type) {
        Birou birou = Birou.TipBirou(type);

        Vector<Cerere> vectAux = new Vector<>();
        for (Cerere cerere : birou.cereriAsteptare)
            if(cerere.text.contains(text) == false)
                vectAux.add(cerere);

        birou.cereriAsteptare = vectAux;
    }

    // metoda care returneaza biroul de care apartine cererea curenta
    static Birou TipBirou(String type) {
        Birou birou = null;

        if(type.contains("angajat")) {
            birou = ManagementPrimarie.birouAngajati;
        } else if (type.contains("pensionar")) {
            birou = ManagementPrimarie.birouPensionari;
        } else if (type.contains("elev")) {
            birou = ManagementPrimarie.birouElevi;
        } else if (type.contains("persoana")) {
            birou = ManagementPrimarie.birouPersoane;
        } else {
            birou = ManagementPrimarie.birouEntitati;
        }

        return birou;
    }

    // metoda care insereaza o cerere in cererile e asteptare ale biroului
    static void InserareCerereBirou(String[] statements, String type, Utilizator user) throws ParseException {
        Birou birou = TipBirou(type);

        // generez textul cererii
        String Textcerere = ManagementPrimarie.GenerareTextCerere(statements, type, user);
        Textcerere = Integer.parseInt(statements[4]) + " - " + Textcerere;
        Cerere cerereCurenta = new Cerere(Textcerere, Integer.parseInt(statements[4]), statements[1], ManagementPrimarie.TipulCererii(statements[2]));

        if (birou.cereriAsteptare == null) {
            birou.cereriAsteptare = new Vector<>();
            birou.cereriAsteptare.add(cerereCurenta);

        } else {
            int inserat = 0;
            Vector<Cerere> vectorSortare = new Vector<>();

            // inserez cererea ordonat in functie de prioritate si, pentru prioritati egale, data la care au fost efectuate
            for (Cerere cerere : birou.cereriAsteptare) {

                if (cerere.prioritate < cerereCurenta.prioritate && inserat == 0) {
                    vectorSortare.add(cerereCurenta);
                    vectorSortare.add(cerere);
                    inserat = 1;

                } else if (cerere.prioritate == cerereCurenta.prioritate &&
                        ManagementPrimarie.ComparareDate(cerere.text.substring(4, cerere.text.length()),
                                cerereCurenta.text.substring(4, cerereCurenta.text.length())) > 0 && inserat == 0) {

                    vectorSortare.add(cerereCurenta);
                    vectorSortare.add(cerere);
                    inserat = 1;

                } else {
                    vectorSortare.add(cerere);
                }
            }
            // daca nu a fost inserata deloc este adugata la finalul listei
            if (inserat == 0) {
                vectorSortare.add(cerereCurenta);
            }
            birou.cereriAsteptare = vectorSortare;
        }
    }
}
