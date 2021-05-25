package com.company;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

public class TTretment2 {
    Model model=new Model("emploi du temp");
    TData data;
    BoolVar TDs[][][][];
    BoolVar TPs[][][][];
    BoolVar Cours[][][][];
    public TTretment2(TData data) {
        this.data = data;
    }
    public void SolvetProblem(){
        TDs=new BoolVar[data.getNbSeancesHoraireParSemaine()][data.getNbGroups()][data.getNbSalleTDs()][data.getNbSeancesTDsParSemaine()];
        TPs=new BoolVar[data.getNbSeancesHoraireParSemaine()][data.getNbGroups()][data.getNbSalleTPs()][data.getNbSeancesTPsParSemaine()];
        Cours=new BoolVar[data.getNbSeancesHoraireParSemaine()][data.getNbSection()][data.getNbSalleCours()][data.getNbSeancesCoursParSemaine()];
        SolvetLeProblemDeTDs();
        SolvetLeProblemDeTPs();
        SolvetLeProblemDeCours();
        MixedLesProblem();
        Solver solver=model.getSolver();
        solver.showStatistics();

        solver.findSolution();
        printemploi();

    }
    public void SolvetLeProblemDeTDs(){
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTDs();k++){
                    for (int l=0;l<data.getNbSeancesTDsParSemaine();l++){
                        TDs[i][j][k][l]=model.boolVar();
                    }
                }
            }
        }
        //fi seanse wahda may9adoch ya9ro des group ktar mal li salle li kaynin

        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int k=0;k<data.getNbSalleTDs();k++){
                BoolVar tab[]=new BoolVar[0];
                for (int j=0;j<data.getNbGroups();j++){
                    for (int l=0;l<data.getNbSeancesTDsParSemaine();l++){
                        tab=addBoolVar(tab.length,tab,TDs[i][j][k][l]);
                    }
                }
                model.sum(tab,"<",2).post();
            }

        }
        //kol group y9ad ya9ra hisa wahda ta3 td fi ga3 lhisas
        for (int j=0;j<data.getNbGroups();j++){
            for (int l=0;l<data.getNbSeancesTDsParSemaine();l++){
                BoolVar tab[]=new BoolVar[0];
                for (int k=0;k<data.getNbSalleTDs();k++){
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        tab=addBoolVar(tab.length,tab,TDs[i][j][k][l]);
                    }
                }
                model.sum(tab,"=",1).post();
            }
        }
        //
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTDs();k++){
                    BoolVar tab[]=new BoolVar[0];
                    for (int l=0;l<data.getNbSeancesTDsParSemaine();l++){
                        tab=addBoolVar(tab.length,tab,TDs[i][j][k][l]);
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }

    }
    public void SolvetLeProblemDeTPs(){
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTPs();k++){
                    for (int l=0;l<data.getNbSeancesTPsParSemaine();l++){
                        TPs[i][j][k][l]=model.boolVar();
                    }
                }
            }
        }
        //fi seanse wahda may9adoch ya9ro des group ktar mal li salle li kaynin

        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int k=0;k<data.getNbSalleTPs();k++){
                BoolVar tab[]=new BoolVar[0];
                for (int j=0;j<data.getNbGroups();j++){
                    for (int l=0;l<data.getNbSeancesTPsParSemaine();l++){
                        tab=addBoolVar(tab.length,tab,TPs[i][j][k][l]);
                    }
                }
                model.sum(tab,"<",2).post();
            }

        }
        //kol group y9ad ya9ra hisa wahda ta3 td fi ga3 lhisas
        for (int j=0;j<data.getNbGroups();j++){
            for (int l=0;l<data.getNbSeancesTPsParSemaine();l++){
                BoolVar tab[]=new BoolVar[0];
                for (int k=0;k<data.getNbSalleTPs();k++){
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        tab=addBoolVar(tab.length,tab,TPs[i][j][k][l]);
                    }
                }
                model.sum(tab,"=",1).post();
            }
        }
        //
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTPs();k++){
                    BoolVar tab[]=new BoolVar[0];
                    for (int l=0;l<data.getNbSeancesTPsParSemaine();l++){
                        tab=addBoolVar(tab.length,tab,TPs[i][j][k][l]);
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }

    }
    public void SolvetLeProblemDeCours(){
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbSection();j++){
                for (int k=0;k<data.getNbSalleCours();k++){
                    for (int l=0;l<data.getNbSeancesCoursParSemaine();l++){
                        Cours[i][j][k][l]=model.boolVar();
                    }
                }
            }
        }

        //fi seanse wahda may9adoch ya9ro des group ktar mal li salle li kaynin

        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int k=0;k<data.getNbSalleCours();k++){
                BoolVar tab[]=new BoolVar[0];
                for (int j=0;j<data.getNbSection();j++){
                    for (int l=0;l<data.getNbSeancesCoursParSemaine();l++){
                        tab=addBoolVar(tab.length,tab,Cours[i][j][k][l]);
                    }
                }
                model.sum(tab,"<",2).post();
            }

        }
        //kol group y9ad ya9ra hisa wahda ta3 td fi ga3 lhisas
        for (int j=0;j<data.getNbSection();j++){
            for (int l=0;l<data.getNbSeancesCoursParSemaine();l++){
                BoolVar tab[]=new BoolVar[0];
                for (int k=0;k<data.getNbSalleCours();k++){
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        tab=addBoolVar(tab.length,tab,Cours[i][j][k][l]);
                    }
                }
                model.sum(tab,"=",1).post();
            }
        }
        //
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbSection();j++){
                for (int k=0;k<data.getNbSalleCours();k++){
                    BoolVar tab[]=new BoolVar[0];
                    for (int l=0;l<data.getNbSeancesCoursParSemaine();l++){
                        tab=addBoolVar(tab.length,tab,Cours[i][j][k][l]);
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }

    }
    public void MixedLesProblem(){
        for (int js=0;js<data.getNbSection();js++){
            for (int jg=SumNbGroupDansLesSectionPrecedent(js);jg<(data.promotion.Sections[js].ListeGroupes.length+SumNbGroupDansLesSectionPrecedent(js));jg++){
                for (int i=0;i< data.getNbSeancesHoraireParSemaine();i++){
                    BoolVar tab[]=new BoolVar[0];

                    for (int k=0;k< data.getNbSalleCours();k++){
                        for (int l=0;l<data.getNbSeancesCoursParSemaine();l++){
                            tab=addBoolVar(tab.length,tab,Cours[i][js][k][l]);
                        }
                    }
                    for (int k=0;k< data.getNbSalleTDs();k++){
                        for (int l=0;l<data.getNbSeancesTDsParSemaine();l++){
                            tab=addBoolVar(tab.length,tab,TDs[i][jg][k][l]);
                        }
                    }
                    for (int k=0;k< data.getNbSalleTPs();k++){
                        for (int l=0;l<data.getNbSeancesTPsParSemaine();l++){
                            tab=addBoolVar(tab.length,tab,TPs[i][jg][k][l]);
                        }
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }
    }
    public void AfficheTds(){
        System.out.println("\nTD: ");
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int l=0;l<data.getNbSeancesTDsParSemaine();l++){
                    for (int k=0;k<data.getNbSalleTDs();k++){

                        if ( TDs[i][j][k][l].getValue()==1){
                            System.out.println("le group "+(j+1)+" fair la seanse "+(l+1)+" dans la salle "+(k+1)+" pendant la seanse "+(i+1));
                        }
                    }
                }
            }
        }
    }
    public void AfficheTps(){
        System.out.println("\nTP: ");
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int l=0;l<data.getNbSeancesTPsParSemaine();l++){
                    for (int k=0;k<data.getNbSalleTDs();k++){

                        if ( TPs[i][j][k][l].getValue()==1){
                            System.out.println("le group "+(j+1)+" fair la seanse "+(l+1)+" dans la salle "+(k+1)+" pendant la seanse "+(i+1));
                        }
                    }
                }
            }
        }
    }
    public void AfficheCours(){
        System.out.println("\nCours: ");
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbSection();j++){
                for (int l=0;l<data.getNbSeancesCoursParSemaine();l++){
                    for (int k=0;k<data.getNbSalleCours();k++){

                        if ( Cours[i][j][k][l].getValue()==1){
                            System.out.println("la section "+(j+1)+" fair la seanse "+(l+1)+" dans la salle "+(k+1)+" pendant la seanse "+(i+1));
                        }
                    }
                }
            }
        }
    }
    public int SumNbGroupDansLesSectionPrecedent(int SectionActuel){
        int somme=0;
        if (SectionActuel!=0){
            for(int i=SectionActuel-1;i>=0;i--){
                somme+=data.promotion.Sections[i].ListeGroupes.length;
            }
        }
        return somme;
    }
    public static BoolVar[] addBoolVar(int tailleDeTableQuiYEstAjoutee, BoolVar TableQuiYestAjoutee[], BoolVar valeurAjoutee){
        BoolVar newarr[] = new BoolVar[tailleDeTableQuiYEstAjoutee + 1];
        for (int i = 0; i < tailleDeTableQuiYEstAjoutee; i++){
            newarr[i] = TableQuiYestAjoutee[i];
        }
        newarr[tailleDeTableQuiYEstAjoutee] = valeurAjoutee;
        return newarr;
    }
    public void printemploi(){

        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            if (data.getJourDeSeanse(i)[1]==0){
                System.out.println(data.promotion.Journees[data.getJourDeSeanse(i)[0]].Intitule+" : ");
            }
            System.out.print("seanse "+(data.getJourDeSeanse(i)[1]+1)+" -> ");
            for (int j=0;j<data.getNbSection();j++){
                for (int l=0;l<data.getNbSeancesCoursParSemaine();l++){
                    for (int k=0;k<data.getNbSalleCours();k++){
                        if ( Cours[i][j][k][l].getValue()==1){
                            System.out.print("Cour: "+data.promotion.Sections[j].Intitule+" "+data.promotion.Matieres[getmodule(l,1)].Intitule+"("+data.promotion.Occupation.SallesCours[k]+")  |");
                        }
                    }
                }
            }
            for (int j=0;j<data.getNbGroups();j++){
                for (int l=0;l<data.getNbSeancesTPsParSemaine();l++){
                    for (int k=0;k<data.getNbSalleTPs();k++){
                        if ( TPs[i][j][k][l].getValue()==1){
                            System.out.print("TP: "+data.promotion.Sections[data.getSectionDeGroup(j)[0]].ListeGroupes[data.getSectionDeGroup(j)[1]]+" "+data.promotion.Matieres[getmodule(l,3)].Intitule+"("+data.promotion.Occupation.SallesTD[k]+")  |");
                        }
                    }
                }
                for (int l=0;l<data.getNbSeancesTDsParSemaine();l++){
                    for (int k=0;k<data.getNbSalleTDs();k++){
                        if ( TDs[i][j][k][l].getValue()==1){
                            System.out.print("TD: "+data.promotion.Sections[data.getSectionDeGroup(j)[0]].ListeGroupes[data.getSectionDeGroup(j)[1]]+" "+data.promotion.Matieres[getmodule(l,2)].Intitule+"("+data.promotion.Occupation.SallesTP[k]+")  |");
                        }
                    }
                }
            }
            System.out.println();

        }
    }
    public int getmodule(int nseanse,int type){
        int somme=0;
        switch (type){
            case 1:{
                for (int i=0;i<data.promotion.Matieres.length;i++){
                    for (int j=0;j<data.promotion.Matieres[i].NbCoursParSemaine;j++){
                        if (somme==nseanse){
                            return i;
                        }
                        somme++;
                    }
                }
            }

            case 2:{
                for (int i=0;i<data.promotion.Matieres.length;i++){
                    for (int j=0;j<data.promotion.Matieres[i].NbTDsParSemaine;j++){
                        if (somme==nseanse){
                            return i;
                        }
                        somme++;
                    }
                }
            }

            case 3:{
                for (int i=0;i<data.promotion.Matieres.length;i++){
                    for (int j=0;j<data.promotion.Matieres[i].NbTPsParSemaine;j++){
                        if (somme==nseanse){
                            return i;
                        }
                        somme++;
                    }
                }
            }

        }
        return 0;
    }
}
