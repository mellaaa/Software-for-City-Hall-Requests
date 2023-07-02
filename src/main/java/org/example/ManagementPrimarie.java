package org.example;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.text.ParseException;

enum TipCerere {
    INLOCUIRE_BULETIN,
    VENIT_SALARIAL,
    CARNET_SOFER,
    CARNET_ELEV,
    ACT_CONSTITUTIV,
    REINOIRE_AUTORIZATIE,
    CUPOANE_PENSIE
}

// clasa abstracta pentru implementarea unui utilizator
abstract class Utilizator {
    String name, type;
    Vector<Cerere> cereriAsteptare, cereriSolutionate;
    protected Utilizator(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

class Persoana extends Utilizator{
    Persoana(String nume) {
        super(nume, "persoana");
    }
}

class Angajat extends Utilizator {
    String companie;

     Angajat(String nume, String companie) {
         super(nume, "angajat");
         this.companie = companie;
    }
}

class Pensionar extends Utilizator {

    Pensionar(String nume) {
        super(nume, "pensionar");
    }
}

class Elev extends Utilizator {
    String scoala;

    Elev(String nume, String scoala) {
        super(nume, "elev");
        this.scoala = scoala;
    }
}

class EntitateJuridica extends Utilizator {
    String reprezentant;

    EntitateJuridica(String nume, String reprezentant) {
        super(nume, "entitate juridica");
        this.reprezentant = reprezentant;
    }
}

public class ManagementPrimarie {

    static int utilizatoriInserati = 0;
    public static Birou birouAngajati, birouElevi, birouPensionari, birouPersoane, birouEntitati;

    // metoda pentru scriere in fisier
    public static void ScriereFisier(String numeFisier, String mesaj) {

        try (FileWriter fw = new FileWriter("src/main/resources/output/" + numeFisier, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(mesaj);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // metoda care intoarce categoria din care face parte utilizatorul
    private static String TipulUtilizatorului(String nume, Vector<Utilizator> vectUtilizatori) {

        for (Utilizator x : vectUtilizatori)
            if ((x.name).contains(nume))
                return x.type;

        return "N/A";
    }

    // metoda care verifica daca utilizatorul poate face o astfel de cerere si apeleaza metoda pentru inserarea cererii
    private static int CerereValida(String[] statements, Vector<Utilizator> vectUtilizatori, String numeFisier) throws IncompatibilCerereUtilizator, ParseException {

        String type = TipulUtilizatorului(statements[1], vectUtilizatori);

        if (type.contains("angajat") && (statements[2].contains("inlocuire buletin") ||
                statements[2].contains("inlocuire carnet de sofer") || statements[2].contains("inregistrare venit salarial"))) {

            ManagementPrimarie.InserareCerere(statements, vectUtilizatori);

        } else if (type.contains("persoana") && (statements[2].contains("inlocuire buletin") || statements[2].contains("inlocuire carnet de sofer"))) {
            ManagementPrimarie.InserareCerere(statements, vectUtilizatori);

        } else if (type.contains("elev") && (statements[2].contains("inlocuire buletin") || statements[2].contains("inlocuire carnet de elev"))){
            ManagementPrimarie.InserareCerere(statements, vectUtilizatori);

        } else if (type.contains("pensionar") && (statements[2].contains("inlocuire buletin") ||
                statements[2].contains("inlocuire carnet de sofer") || statements[2].contains("inregistrare cupoane de pensie"))) {
            ManagementPrimarie.InserareCerere(statements, vectUtilizatori);

        } else if (type.contains("entitate juridica") && (statements[2].contains("creare act constitutiv") || statements[2].contains("reinnoire autorizatie"))) {
            ManagementPrimarie.InserareCerere(statements, vectUtilizatori);

        } else {
            // Daca cererea nu este compatibila cu utilizatorul se afiseaza un mesaj si se arunca o eroare
            String mesaj = "Utilizatorul de tip "+ type +" nu poate inainta o cerere de tip " + statements[2];
            ScriereFisier(numeFisier, mesaj);
            throw new IncompatibilCerereUtilizator("Cerere incompatibila cu Utilizatorul!");
        }
        return 0;
    }

    // metoda care inlocuieste lunile cu echivalentul in cifre (pentru a putea folosi LocalDateTime)
    private static String InlocuireLuni(String str) {

        str = str.replaceAll("Dec", "12");
        str = str.replaceAll("Nov", "11");
        str = str.replaceAll("Oct", "10");
        str = str.replaceAll("Sep", "09");
        str = str.replaceAll("Aug", "08");
        str = str.replaceAll("Jul", "07");
        str = str.replaceAll("Jun", "06");
        str = str.replaceAll("May", "05");
        str = str.replaceAll("Apr", "04");
        str = str.replaceAll("Mar", "03");
        str = str.replaceAll("Feb", "02");
        str = str.replaceAll("Jan", "01");

        return str;
    }

    // metoda care compara datile la cere 2 cereri au fost efectuate, pentru a vedea care este am recenta
    static int ComparareDate(String text1, String text2) {
        String[] first = text1.split(" ", 10);
        String[] second = text2.split(" ", 10);

        // inlocuire luna scrisa cu litere cu echivalentul in cifre
        first[0] = InlocuireLuni(first[0]);
        second[0] = InlocuireLuni(second[0]);

        LocalDateTime now1 = LocalDateTime.parse( first[0].substring(6,10)+ "-" +
                first[0].substring(3,5)  + "-" + first[0].substring(0,2) + "T" + first[1]);

        LocalDateTime now2 = LocalDateTime.parse( second[0].substring(6,10)+ "-" + second[0].substring(3,5)  +
                "-" + second[0].substring(0,2) + "T" + second[1]);

        // se verifica daca prima data este a-2-a cronologic
        if (now1.isAfter(now2)) return 1;
        else return -1;
    }

    // metoda care afiseaza cererile dintr-un vector
    private static void AfisareVector(Vector<Cerere> vect, String numeFisier) {
        for (Cerere cerere : vect)
            ScriereFisier(numeFisier, cerere.text);
    }

    // metoda care afiseaza cererile din lista unui utilizator
    private static void AfisareCereriAsteptare(String[] statements, Vector<Utilizator> vectUtilizatori, String numeFisier) {

        // iterez prin utilizatori pana il gasesc pe cel curent
        for (Utilizator x : vectUtilizatori) {
            if ((x.name).contains(statements[1])) {

                // afisez textul fiecarei cereri in asteptare
                ScriereFisier(numeFisier, x.name + " - cereri in asteptare:");
                ManagementPrimarie.AfisareVector(x.cereriAsteptare, numeFisier);

            }
        }
    }

    // metoda care modeleaza textul cererii inaintate in functie de datele acesteia si tipul utilizatorului
    static String GenerareTextCerere(String[] statements, String type, Utilizator user) {
        String mesajCerere = null;

        if (type.equals("angajat")) {
            mesajCerere = statements[3] + " - Subsemnatul " + statements[1] +
                    ", angajat la compania " + ((Angajat) user).companie +
                    ", va rog sa-mi aprobati urmatoarea solicitare: " + statements[2];

        } else if (type.equals("elev")) {
            mesajCerere = statements[3] + " - Subsemnatul " + statements[1] + ", elev la scoala " + ((Elev) user).scoala +
                    ", va rog sa-mi aprobati urmatoarea solicitare: " + statements[2];

        } else if (type.equals("pensionar") || type.equals("persoana")) {
            mesajCerere = statements[3] + " - Subsemnatul " + statements[1] + ", va rog sa-mi aprobati urmatoarea solicitare: " + statements[2];

        } else if (type.equals("entitate juridica")) {
            mesajCerere = statements[3] + " - Subsemnatul " + ((EntitateJuridica) user).reprezentant +
                    ", reprezentant legal al companiei " + statements[1] +
                    ", va rog sa-mi aprobati urmatoarea solicitare: " + statements[2];
        }

        return mesajCerere;
    }

    static TipCerere TipulCererii(String type) {

        if(type.contains("inregistrare venit salarial")) {
            return TipCerere.VENIT_SALARIAL;
        } else if(type.contains("inlocuire buletin")) {
            return TipCerere.INLOCUIRE_BULETIN;
        } else if(type.contains("reinnoire autorizatie")) {
            return TipCerere.REINOIRE_AUTORIZATIE;
        } else if(type.contains("inlocuire carnet de sofer")) {
            return TipCerere.CARNET_SOFER;
        } else if(type.contains("inregistrare cupoane de pensie")) {
            return TipCerere.CUPOANE_PENSIE;
        } else if(type.contains("inlocuire carnet de elev")) {
            return TipCerere.CARNET_ELEV;
        } else {
            return TipCerere.ACT_CONSTITUTIV;
        }
    }

    // metoda care aduga o cerere noua in lista unui utilizator
    private static void InserareCerere(String[] statements, Vector<Utilizator> vectUtilizatori) throws ParseException {

        for (Utilizator user : vectUtilizatori) {
            if ((user.name).contains(statements[1])) {

                // generez textul cererii
                String Textcerere = GenerareTextCerere(statements, user.type, user);
                Cerere cerereCurenta = new Cerere(Textcerere, Integer.parseInt(statements[4]), statements[1], TipulCererii(statements[2]));

                if (user.cereriAsteptare == null) {
                    user.cereriAsteptare = new Vector<>();
                    user.cereriAsteptare.add(cerereCurenta);

                } else {
                    int inserat = 0;
                    // vector utilizat la inserarea ordonata in functie de data
                    Vector<Cerere> vectorSortare = new Vector<>();

                    // cererea este inserata ordonat pe baza datei cand a fost efectuata
                    for (Cerere cerere : user.cereriAsteptare) {

                        if (ManagementPrimarie.ComparareDate(cerere.text, cerereCurenta.text) > 0 && inserat == 0) {
                            vectorSortare.add(cerereCurenta);
                            vectorSortare.add(cerere);
                            inserat = 1;

                        } else {
                            vectorSortare.add(cerere);
                        }
                    }
                    // daca nu a fost inserata nicaieri, va fi adaugata la coada
                    if (inserat == 0) {
                        vectorSortare.add(cerereCurenta);

                    }
                    user.cereriAsteptare = vectorSortare;
                }
                // se apeleaza metoda care insereaza cererea si in lista biroului aferent
                Birou.InserareCerereBirou(statements, user.type, user);
            }
        }
    }

    // metoda care insereaza un utilizator nou
    private static void InserareUtilizator(String[] statements, Vector<Utilizator> vect) {
        Utilizator obiect = null;

        if (statements[1].contains("angajat")) {
            obiect = new Angajat(statements[2], statements[3]);

        } else if (statements[1].contains("elev")) {
            obiect = new Elev(statements[2], statements[3]);

        } else if (statements[1].contains("pensionar")) {
            obiect = new Pensionar(statements[2]);

        } else if (statements[1].contains("persoana")) {
            obiect = new Persoana(statements[2]);

        } else if (statements[1].contains("entitate juridica")) {
            obiect = new EntitateJuridica(statements[2], statements[3]);
        }

        vect.add(obiect);
        utilizatoriInserati++;
    }

    // metoda care sterge o cerere din lista de asteptare a utilizatorului
    private static void RetragereCerere(Vector<Utilizator> vectUtilizatori, String[] statements) {

        for (Utilizator x : vectUtilizatori) {
            if ((x.name).contains(statements[1])) {

                Vector<Cerere> vectorSortare = new Vector<>();

                /* daca nu este cererea care se doreste a fi stearsa se adauga la loc,
                 in cza contrar apelam functia care o sterge si din lista biroului */

                for (Cerere cerere : x.cereriAsteptare)
                    if (cerere.text.contains(statements[2]) == false) {
                        vectorSortare.add(cerere);
                    } else {
                        Birou.StergereCerereBirou(cerere.text, x.type);
                    }

                x.cereriAsteptare = vectorSortare;
            }
        }
    }

    private static void RezolvaCerere(String[] statements, Vector<Utilizator> vectUtilizatori) throws ParseException {
        Birou birou = Birou.TipBirou(statements[1]);

        if(birou.cereriSolutionate == null) {
            birou.cereriSolutionate = new Vector<>();
        }
        // se selecteaza cea mai importanta cerere la momentul actual
        Cerere cerereAux = birou.cereriAsteptare.firstElement();

        // data la care cererea a fost conceputa
        String data = cerereAux.text.substring(4, 24);

        // Scriu in fisierul functionarului datele despre cererea solutionata
        ScriereFisier("functionar_" + statements[2] + ".txt", data + " - " + cerereAux.numeUtilizator);

        // cererea este stearsa din lista de asteptare a biroului
        Cerere solutionata = birou.cereriAsteptare.remove(0);
        birou.cereriSolutionate.add(solutionata);

        String stergere = "cerere; " + cerereAux.numeUtilizator + "; " + data;
        String[] stat = stergere.split("; ", 3);

        // sterg cererea din lista de asteptare a utilizatorului
        RetragereCerere(vectUtilizatori, stat);

        // cererea noua de inserat in lista de finalizate
        Cerere cerereNoua = new Cerere(solutionata.text.substring(4),
                Integer.parseInt(solutionata.text.substring(0, 1)), solutionata.numeUtilizator, TipulCererii(statements[2]));

        // cererea este adaugata in lista de finalizate a utilizatorului care a facut-o
        for(Utilizator user : vectUtilizatori) {
            if(user.name.contains(cerereNoua.numeUtilizator)) {

                if(user.cereriSolutionate == null)
                    user.cereriSolutionate = new Vector<>();
                user.cereriSolutionate.add(cerereNoua);
            }
        }
    }

    private static void AfisareCereriSolutionate(String[] statements, Vector<Utilizator> vectUtilizatori, String numeFisier) {

        for (Utilizator x : vectUtilizatori) {
            if ((x.name).contains(statements[1])) {

                    ScriereFisier(numeFisier, x.name + " - cereri in finalizate:");
                    for (Cerere cerere : x.cereriSolutionate)
                        ScriereFisier(numeFisier, cerere.text);

            }
        }
    }
    public static void main(String[] args) throws IOException, ParseException {
        Vector<Utilizator> vectUtilizatori = new Vector<>();
        birouAngajati = new Birou();
        birouEntitati = new Birou();
        birouElevi = new Birou();
        birouPersoane = new Birou();
        birouPensionari = new Birou();

        File file = new File("src/main/resources/input/" + args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            String[] statements = line.split("; ", 10);

            if (line.contains("adauga_utilizator")) {
                ManagementPrimarie.InserareUtilizator(statements, vectUtilizatori);

            } else if (line.contains("cerere_noua")) {

                try {
                    ManagementPrimarie.CerereValida(statements, vectUtilizatori, args[0]);

                } catch (IncompatibilCerereUtilizator e) {
                    e.printStackTrace();
                }

            } else if (line.contains("afiseaza_cereri_in_asteptare")) {
                ManagementPrimarie.AfisareCereriAsteptare(statements, vectUtilizatori, args[0]);

            } else if (line.contains("retrage_cerere")) {
                ManagementPrimarie.RetragereCerere(vectUtilizatori, statements);

            } else if (line.contains("afiseaza_cereri_finalizate")) {
                ManagementPrimarie.AfisareCereriSolutionate(statements, vectUtilizatori, args[0]);

            } else if (line.contains("adauga_functionar")) {
                Functionar.AdaugaFunctionar(statements);

            } else if (line.contains("rezolva_cerere")) {
                ManagementPrimarie.RezolvaCerere(statements, vectUtilizatori);

            } else if (line.contains("afiseaza_cereri")) {

                if(line.contains("angajat")) {
                    ScriereFisier(args[0], "angajat - cereri in birou:");
                    ManagementPrimarie.AfisareVector(birouAngajati.cereriAsteptare, args[0]);

                } else if (line.contains("elev")) {
                    ScriereFisier(args[0], "elev - cereri in birou:");
                    ManagementPrimarie.AfisareVector(birouElevi.cereriAsteptare, args[0]);

                } else if (line.contains("pensionar")) {
                    ScriereFisier(args[0], "pensionar - cereri in birou:");
                    ManagementPrimarie.AfisareVector(birouPensionari.cereriAsteptare, args[0]);

                } else if (line.contains("entitate juridica")) {
                    ScriereFisier(args[0], "entitate juridica - cereri in birou:");
                    ManagementPrimarie.AfisareVector(birouEntitati.cereriAsteptare, args[0]);

                } else {
                    ScriereFisier(args[0], "persoana - cereri in birou:");
                    ManagementPrimarie.AfisareVector(birouPersoane.cereriAsteptare, args[0]);

                }
            }
        }
    }
}
