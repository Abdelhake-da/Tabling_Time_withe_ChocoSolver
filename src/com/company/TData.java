package com.company;

public class TData {
    TPromotion promotion=new TPromotion("L3");
    public TData(){

        promotion.Matieres= new TMatiere[]{
                new TMatiere("bdd"   , 2, 3, 1,new String[]{"toto1","toto2"}),
                new TMatiere("thl"   , 2, 2, 1,new String[]{"toto1","toto2"}),
                new TMatiere("rc"    , 2, 2, 1,new String[]{"toto1","toto2"}),
                new TMatiere("poo"   , 2, 0, 1,new String[]{"toto1","toto2"}),
                new TMatiere("se"    , 2, 2, 1,new String[]{"toto1","toto2"}),
                new TMatiere("devweb", 2, 0, 1,new String[]{"toto1","toto2"})
                };
        promotion.Journees=new TJournee[]{
                new TJournee("dimanche",new String[]{"S1","S2","S3","S4","S5","S6"})
                ,new TJournee("lundi"   ,new String[]{"S1","S2","S3","S4","S5","S6"})
                ,new TJournee("mardi"   ,new String[]{"S1","S2","S3","S4","S5","S6"})
                ,new TJournee("mercredi",new String[]{"S1","S2","S3","S4","S5","S6"})
               , new TJournee("jeudi"   ,new String[]{"S1","S2","S3","S4","S5","S6"})
            };
        promotion.Occupation=new TOccupation(
                new String[]{"A1","A2"},
                new String[]{"Info1","Info2","Info3"},
                new String[]{"Tp1","Tp2","Tp3"}
                );
        promotion.Sections=new TSection[]{
                new TSection("S1",new String[]{"G1","G2","G3"}),
                new TSection("S2",new String[]{"G4","G5","G6"})
        };
    }
    public int getNbSalleCours(){
        return promotion.Occupation.SallesCours.length;
    }
    public int getNbSalleTDs(){
        return promotion.Occupation.SallesTD.length;
    }
    public int getNbSalleTPs(){
        return promotion.Occupation.SallesTP.length;
    }
    public int getNbSeancesCoursParSemaine(){
        int somme=0;
        for (int i=0;i<promotion.Matieres.length;i++){
            somme+=promotion.Matieres[i].NbCoursParSemaine;
        }
        return somme;
    }
    public int getNbSeancesTDsParSemaine(){
        int somme=0;
        for (int i=0;i<promotion.Matieres.length;i++){
            somme+=promotion.Matieres[i].NbTDsParSemaine;
        }
        return somme;
    }
    public int getNbSeancesTPsParSemaine(){
        int somme=0;
        for (int i=0;i<promotion.Matieres.length;i++){
            somme+=promotion.Matieres[i].NbTPsParSemaine;
        }
        return somme;
    }
    public int getNbSeancesHoraireParSemaine(){
        int somme=0;
        for (int i=0;i<promotion.Journees.length;i++){
            somme+=promotion.Journees[i].Seances.length;
        }
        return somme;
    }
    public int getNbGroups(){
        int somme=0;
        for (int i=0;i<promotion.Sections.length;i++){
            somme+=promotion.Sections[i].ListeGroupes.length;
        }
        return somme;
    }
    public int getNbSection(){
        return promotion.Sections.length;
    }
    public int[] getSectionDeGroup (int NbGroup){
        int somme=0;
        for (int i=0; i<getNbSection();i++){
            for (int j=0;j<promotion.Sections[i].ListeGroupes.length;j++){
                if (somme==NbGroup){
                    return new int[]{i,j};
                }
                somme+=1;
            }
        }
        return new int[]{0,0};
    }
    public int[] getJourDeSeanse (int NbSeanse){
        int somme=0;
        for (int i=0; i<promotion.Journees.length;i++){
            for (int j=0;j<promotion.Journees[i].Seances.length;j++){
                if (somme==NbSeanse){
                    return new int[]{i,j};
                }
                somme+=1;
            }
        }
        return new int[]{0,0};
    }
    }



