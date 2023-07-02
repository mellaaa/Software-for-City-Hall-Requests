package org.example;

import java.util.Vector;

public class Functionar {
    String nume;
    Functionar(String nume) {
        this.nume = nume;
    }
    static void AdaugaFunctionar(String[] statements) {
        Functionar functionarNou = new Functionar(statements[2]);

        if(statements[1].contains("angajat")) {
            if (ManagementPrimarie.birouAngajati.vectFunctionari == null)
                ManagementPrimarie.birouAngajati.vectFunctionari = new Vector<>();
            ManagementPrimarie.birouAngajati.vectFunctionari.add(functionarNou);

        } else if (statements[1].contains("persoana")) {
            if (ManagementPrimarie.birouPersoane.vectFunctionari == null)
                ManagementPrimarie.birouPersoane.vectFunctionari = new Vector<>();
            ManagementPrimarie.birouPersoane.vectFunctionari.add(functionarNou);

        } else if (statements[1].contains("pensionar")) {
            if (ManagementPrimarie.birouPensionari.vectFunctionari == null)
                ManagementPrimarie.birouPensionari.vectFunctionari = new Vector<>();
            ManagementPrimarie.birouPensionari.vectFunctionari.add(functionarNou);

        } else if (statements[1].contains("elev")) {
            if (ManagementPrimarie.birouElevi.vectFunctionari == null)
                ManagementPrimarie.birouElevi.vectFunctionari = new Vector<>();
            ManagementPrimarie.birouElevi.vectFunctionari.add(functionarNou);

        } else {
            if (ManagementPrimarie.birouEntitati.vectFunctionari == null)
                ManagementPrimarie.birouEntitati.vectFunctionari = new Vector<>();
            ManagementPrimarie.birouPensionari.vectFunctionari.add(functionarNou);
        }
    }
}
