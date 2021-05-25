package com.company;

public class TMatiere {
    String Intitule;
    int NbCoursParSemaine;
    int NbTPsParSemaine;
    int NbTDsParSemaine;
    String Profs[] ;

    public TMatiere(String intitule, int nbCoursParSemaine, int nbTDsParSemaine, int nbTPsParSemaine, String[] profs) {
        Intitule = intitule;
        NbCoursParSemaine = nbCoursParSemaine;
        NbTPsParSemaine = nbTPsParSemaine;
        NbTDsParSemaine = nbTDsParSemaine;
        Profs = profs;
    }
}
