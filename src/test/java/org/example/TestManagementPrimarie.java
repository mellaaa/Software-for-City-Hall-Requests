package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.io.IOUtils;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;

public class TestManagementPrimarie {

    String antetOutput = "src/main/resources/output/";
    String antetRef = "src/main/resources/references/";
    @Test
    public void emptyOutput() {
        File filesList[] = new File(antetOutput).listFiles();
        for(File file : filesList) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                file.delete();
            }
        }
        assertTrue(true);
    }
    @Test
    public void adaugaCerere1() throws IOException, ParseException {
        String file = "01_adauga_cerere_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void exceptieCerere1() throws IOException, ParseException {
        String file = "02_exceptie_cerere_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void stergeCerere1() throws IOException, ParseException {
        String file = "03_sterge_cerere_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatorCerereCombinat1() throws IOException, ParseException {
        String file = "04_utilizator_cerere_combinat_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatorCerereCombinat2() throws IOException, ParseException {
        String file = "05_utilizator_cerere_combinat_2.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatorCerereCombinat3() throws IOException, ParseException {
        String file = "06_utilizator_cerere_combinat_3.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatorCerereCombinat4() throws IOException, ParseException {
        String file = "07_utilizator_cerere_combinat_4.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatorCerereCombinat5() throws IOException, ParseException {
        String file = "08_utilizator_cerere_combinat_5.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatoriCombinat1() throws IOException, ParseException {
        String file = "09_utilizatori_combinat_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatoriCombinat2() throws IOException, ParseException {
        String file = "10_utilizatori_combinat_2.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void utilizatoriCombinat3() throws IOException, ParseException {
        String file = "11_utilizatori_combinat_3.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void birouCereri1() throws IOException, ParseException {
        String file = "12_birou_cereri_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void birouCereri2() throws IOException, ParseException {
        String file = "13_birou_cereri_2.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out;
        Reader ref;
        out = new BufferedReader(new FileReader(antetOutput + file));
        ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
        // Functionari
        out = new BufferedReader(new FileReader(antetOutput + "functionar_Alina Popescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "13_functionar_Alina Popescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));

        out = new BufferedReader(new FileReader(antetOutput + "functionar_Ion Popescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "13_functionar_Ion Popescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void birouCereri3() throws IOException, ParseException {
        String file = "14_birou_cereri_3.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out;
        Reader ref;
        out = new BufferedReader(new FileReader(antetOutput + file));
        ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
        // Functionari
        out = new BufferedReader(new FileReader(antetOutput + "functionar_Ina Ionescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "14_functionar_Ina Ionescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void birouCereri4() throws IOException, ParseException {
        String file = "15_birou_cereri_4.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out;
        Reader ref;
        out = new BufferedReader(new FileReader(antetOutput + file));
        ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
        // Functionari
        out = new BufferedReader(new FileReader(antetOutput + "functionar_Andra Functionarescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "15_functionar_Andra Functionarescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));

        out = new BufferedReader(new FileReader(antetOutput + "functionar_Cosmin Functionarescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "15_functionar_Cosmin Functionarescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));

        out = new BufferedReader(new FileReader(antetOutput + "functionar_Ionel Functionarescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "15_functionar_Ionel Functionarescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));

        out = new BufferedReader(new FileReader(antetOutput + "functionar_Mihai Functionarescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "15_functionar_Mihai Functionarescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void birouCereri5() throws IOException, ParseException {
        String file = "16_birou_cereri_5.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader ref;
        Reader out;
        out = new BufferedReader(new FileReader(antetOutput + file));
        ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
        // Functionari
        out = new BufferedReader(new FileReader(antetOutput + "functionar_Danut Functionarescu.txt"));
        ref = new BufferedReader(new FileReader(antetRef + "16_functionar_Danut Functionarescu.txt"));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }
}
