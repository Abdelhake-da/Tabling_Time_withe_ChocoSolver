package com.company;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

public class TTretment2 {
    Model model=new Model("emploi du temp");
    TData data;
    BoolVar TDs[][][][][];
    BoolVar TPs[][][][][];
    BoolVar Cours[][][][][];
    public TTretment2(TData data) {
        this.data = data;
    }
    public void SolvetProblem(){
        TDs=new BoolVar[data.getNbSeancesHoraireParSemaine()][data.getNbGroups()][data.getNbSalleTDs()][data.promotion.Matieres.length][data.getLeNombreMaxDesProf()];
        TPs=new BoolVar[data.getNbSeancesHoraireParSemaine()][data.getNbGroups()][data.getNbSalleTPs()][data.promotion.Matieres.length][data.getLeNombreMaxDesProf()];
        Cours=new BoolVar[data.getNbSeancesHoraireParSemaine()][data.getNbSection()][data.getNbSalleCours()][data.promotion.Matieres.length][data.getLeNombreMaxDesProf()];




        SolvetLeProblemDeTDs();
        SolvetLeProblemDeTPs();
        SolvetLeProblemDeCours();
        MixedLesProblem();
        Solver solver=model.getSolver();
        solver.showStatistics();

        solver.findSolution();
        printemploi();
//        AfficheTds();
//        AfficheTps();
//        AfficheCours();

    }
    public void SolvetLeProblemDeTDs(){
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTDs();k++){
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m< data.getLeNombreMaxDesProf();m++){
                            TDs[i][j][k][l][m]=model.boolVar();
                        }

                    }
                }
            }
        }
        //Dans une session, on ne peut enseigner plus de groupes que le nombre de salles
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int k=0;k<data.getNbSalleTDs();k++){
                BoolVar tab[]=new BoolVar[0];
                for (int j=0;j<data.getNbGroups();j++){
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m< data.getLeNombreMaxDesProf();m++) {
                            tab = addBoolVar(tab.length, tab, TDs[i][j][k][l][m]);
                        }
                    }
                }
                model.sum(tab,"<",2).post();
            }
        }
        //Le groupe ne peut pas étudier plus que le total de ses sessions (Tps, Tds, Cours)
        for (int j=0;j<data.getNbGroups();j++){
            for (int l=0;l<data.promotion.Matieres.length;l++){
                BoolVar tab[]=new BoolVar[0];
                for (int k=0;k<data.getNbSalleTDs();k++){
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        for (int m=0;m<data.getLeNombreMaxDesProf();m++){
                            tab=addBoolVar(tab.length,tab,TDs[i][j][k][l][m]);
                        }

                    }
                }
                model.sum(tab,"=",data.promotion.Matieres[l].NbTDsParSemaine).post();
            }
        }
        //Pour chaque session, groupe et salle, nous pouvons enseigner une seul matière
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTDs();k++){
                    BoolVar tab[]=new BoolVar[0];
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++) {
                            tab = addBoolVar(tab.length, tab, TDs[i][j][k][l][m]);
                        }
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }
        //Chaque groupe étudie toutes les leçons d'une matière(Tds ou Tps ou Cours) par un enseignant
        for (int j=0;j<data.getNbGroups();j++){
            for (int l=0;l<data.promotion.Matieres.length;l++){
                for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++) {
                    BoolVar tab[]=new BoolVar[0];
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        for (int k=0;k<data.getNbSalleTDs();k++){
                            tab = addBoolVar(tab.length, tab, TDs[i][j][k][l][m]);
                        }
                    }
                    model.sum(tab,"=", model.intVar( new int[]{0,data.promotion.Matieres[l].NbTDsParSemaine})).post();
                }

            }
        }
    }
    public void SolvetLeProblemDeTPs(){
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTPs();k++){
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m< data.getLeNombreMaxDesProf();m++){
                            TPs[i][j][k][l][m]=model.boolVar();
                        }

                    }
                }
            }
        }
        //Dans une session, on ne peut enseigner plus de groupes que le nombre de salles
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int k=0;k<data.getNbSalleTPs();k++){
                BoolVar tab[]=new BoolVar[0];
                for (int j=0;j<data.getNbGroups();j++){
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m< data.getLeNombreMaxDesProf();m++) {
                            tab = addBoolVar(tab.length, tab, TPs[i][j][k][l][m]);
                        }
                    }
                }
                model.sum(tab,"<",2).post();
            }

        }
        //Le groupe ne peut pas étudier plus que le total de ses sessions (Tps, Tds, Cours)
        for (int j=0;j<data.getNbGroups();j++){
            for (int l=0;l<data.promotion.Matieres.length;l++){
                BoolVar tab[]=new BoolVar[0];
                for (int k=0;k<data.getNbSalleTPs();k++){
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        for (int m=0;m<data.getLeNombreMaxDesProf();m++){
                            tab=addBoolVar(tab.length,tab,TPs[i][j][k][l][m]);
                        }

                    }
                }
                model.sum(tab,"=",data.promotion.Matieres[l].NbTPsParSemaine).post();
            }
        }

        //Pour chaque session, groupe et salle, nous pouvons enseigner une seul matière
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int k=0;k<data.getNbSalleTPs();k++){
                    BoolVar tab[]=new BoolVar[0];
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++) {
                            tab = addBoolVar(tab.length, tab, TPs[i][j][k][l][m]);
                        }
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }
        //Chaque groupe étudie toutes les leçons d'une matière(Tds ou Tps ou Cours) par un enseignant
        for (int j=0;j<data.getNbGroups();j++){
            for (int l=0;l<data.promotion.Matieres.length;l++){
                for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++) {
                    BoolVar tab[]=new BoolVar[0];
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        for (int k=0;k<data.getNbSalleTPs();k++){
                            tab = addBoolVar(tab.length, tab, TPs[i][j][k][l][m]);
                        }
                    }
                    model.sum(tab,"=", model.intVar( new int[]{0,data.promotion.Matieres[l].NbTPsParSemaine})).post();
                }

            }
        }
    }
    public void SolvetLeProblemDeCours(){
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbSection();j++){
                for (int k=0;k<data.getNbSalleCours();k++){
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m< data.getLeNombreMaxDesProf();m++){
                            Cours[i][j][k][l][m]=model.boolVar();
                        }

                    }
                }
            }
        }
        //Dans une session, on ne peut enseigner plus de groupes que le nombre de salles
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int k=0;k<data.getNbSalleCours();k++){
                BoolVar tab[]=new BoolVar[0];
                for (int j=0;j<data.getNbSection();j++){
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m< data.getLeNombreMaxDesProf();m++) {
                            tab = addBoolVar(tab.length, tab, Cours[i][j][k][l][m]);
                        }
                    }
                }
                model.sum(tab,"<",2).post();
            }

        }

        //Le groupe ne peut pas étudier plus que le total de ses sessions (Tps, Tds, Cours)
        for (int j=0;j<data.getNbSection();j++){
            for (int l=0;l<data.promotion.Matieres.length;l++){
                BoolVar tab[]=new BoolVar[0];
                for (int k=0;k<data.getNbSalleCours();k++){
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        for (int m=0;m<data.getLeNombreMaxDesProf();m++){
                            tab=addBoolVar(tab.length,tab,Cours[i][j][k][l][m]);
                        }

                    }
                }
                model.sum(tab,"=",data.promotion.Matieres[l].NbCoursParSemaine).post();
            }
        }

        //Pour chaque session, groupe et salle, nous pouvons enseigner une seul matière
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbSection();j++){
                for (int k=0;k<data.getNbSalleCours();k++){
                    BoolVar tab[]=new BoolVar[0];
                    for (int l=0;l<data.promotion.Matieres.length;l++){
                        for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++) {
                            tab = addBoolVar(tab.length, tab, Cours[i][j][k][l][m]);
                        }
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }

       //Chaque groupe étudie toutes les leçons d'une matière(Tds ou Tps ou Cours) par un enseignant
        for (int j=0;j<data.getNbSection();j++){
            for (int l=0;l<data.promotion.Matieres.length;l++){
                for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++) {
                    BoolVar tab[]=new BoolVar[0];
                    for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
                        for (int k=0;k<data.getNbSalleCours();k++){
                            tab = addBoolVar(tab.length, tab, Cours[i][j][k][l][m]);
                        }
                    }
                    model.sum(tab,"=", model.intVar( new int[]{0,data.promotion.Matieres[l].NbCoursParSemaine})).post();
                }

            }
        }
    }
    public void MixedLesProblem(){
        //Les groupes appartenant à un  section ne peuvent pas étudier le cours en même temps que td ou tp
        for (int js=0;js<data.getNbSection();js++){
            for (int jg=SumNbGroupDansLesSectionPrecedent(js);jg<(data.promotion.Sections[js].ListeGroupes.length+SumNbGroupDansLesSectionPrecedent(js));jg++){
                for (int i=0;i< data.getNbSeancesHoraireParSemaine();i++){
                    BoolVar tab[]=new BoolVar[0];

                    for (int k=0;k< data.getNbSalleCours();k++){
                        for (int l=0;l<data.promotion.Matieres.length;l++){
                            for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++){
                                tab=addBoolVar(tab.length,tab,Cours[i][js][k][l][m]);
                            }

                        }
                    }
                    for (int k=0;k< data.getNbSalleTDs();k++){
                        for (int l=0;l<data.promotion.Matieres.length;l++){
                            for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++){
                                tab=addBoolVar(tab.length,tab,TDs[i][jg][k][l][m]);
                            }
                        }
                    }
                    for (int k=0;k< data.getNbSalleTPs();k++){
                        for (int l=0;l<data.promotion.Matieres.length;l++){
                            for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++){
                                tab=addBoolVar(tab.length,tab,TPs[i][jg][k][l][m]);
                            }
                        }
                    }
                    model.sum(tab,"<",2).post();
                }

            }
        }
        //Chaque enseignant peut enseigner à un groupe en une session
        for (int l=0;l<data.promotion.Matieres.length;l++){
            for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++){
                for (int i=0;i< data.getNbSeancesHoraireParSemaine();i++){
                    BoolVar tab[]=new BoolVar[0];
                    for (int js=0;js<data.getNbSection();js++){
                        for (int k=0;k< data.getNbSalleCours();k++){
                            tab=addBoolVar(tab.length,tab,Cours[i][js][k][l][m]);
                        }
                    }
                    for (int jg=0;jg<data.getNbGroups();jg++){
                        for (int k=0;k< data.getNbSalleTDs();k++){
                            tab=addBoolVar(tab.length,tab,TDs[i][jg][k][l][m]);
                        }
                        for (int k=0;k< data.getNbSalleTPs();k++){
                            tab=addBoolVar(tab.length,tab,TPs[i][jg][k][l][m]);
                        }
                    }
                    model.sum(tab,"<",2).post();
                }
            }
        }

    }
    /*public void AfficheTds(){
        System.out.println("\nTD: ");
        for (int i=0;i<data.getNbSeancesHoraireParSemaine();i++){
            for (int j=0;j<data.getNbGroups();j++){
                for (int l=0;l<data.promotion.Matieres.length;l++){
                    for (int k=0;k<data.getNbSalleTDs();k++){
                        for (int m=0;m<data.promotion.Matieres[l].Profs.length;m++){
                            if ( TDs[i][j][k][l][m].getValue()==1){
                                System.out.println("le group "+(j+1)+" fair la seanse "+(l+1)+" dans la salle "+(k+1)+" pendant la seanse "+(i+1)+" par le prof "+(m+1));
                            }
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
                for (int l=0;l<data.promotion.Matieres.length;l++){
                    for (int k=0;k<data.getNbSalleTPs();k++){
                        for (int m=0;m<data.promotion.Matieres[l].Profs.length;m++){
                            if ( TPs[i][j][k][l][m].getValue()==1){
                                System.out.println("le group "+(j+1)+" fair la seanse "+(l+1)+" dans la salle "+(k+1)+" pendant la seanse "+(i+1)+" par le prof "+(m+1));
                            }
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
                for (int l=0;l<data.promotion.Matieres.length;l++){
                    for (int k=0;k<data.getNbSalleCours();k++){
                        for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++){
                            if ( Cours[i][j][k][l][m].getValue()==1){
                                System.out.println("la section "+(j+1)+" fair la seanse "+(l+1)+" dans la salle "+(k+1)+" pendant la seanse "+(i+1)+" par le prof "+(m+1));
                            }
                        }

                    }
                }
            }
        }
    }*/
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
                for (int l=0;l<data.promotion.Matieres.length;l++){
                    for (int k=0;k<data.getNbSalleCours();k++){
                        for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++)
                            if ( Cours[i][j][k][l][m].getValue()==1){
                                System.out.print("Cour: "+data.promotion.Sections[j].Intitule+" "+data.promotion.Matieres[l].Intitule+"("+data.promotion.Occupation.SallesCours[k]+") "+data.promotion.Matieres[l].Profs[m]+" | ");
                            }
                    }
                }
            }
            for (int j=0;j<data.getNbGroups();j++){
                for (int l=0;l<data.promotion.Matieres.length;l++){
                    for (int k=0;k<data.getNbSalleTPs();k++){
                        for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++)
                            if ( TPs[i][j][k][l][m].getValue()==1){
                                System.out.print("TP: "+data.promotion.Sections[data.getSectionDeGroup(j)[0]].ListeGroupes[data.getSectionDeGroup(j)[1]]+" "+data.promotion.Matieres[l].Intitule+"("+data.promotion.Occupation.SallesTP[k]+") "+data.promotion.Matieres[l].Profs[m]+" | ");
                            }
                    }
                }
                for (int l=0;l<data.promotion.Matieres.length;l++){
                    for (int k=0;k<data.getNbSalleTDs();k++){
                        for(int m=0;m<data.promotion.Matieres[l].Profs.length;m++)
                            if ( TDs[i][j][k][l][m].getValue()==1){
                                System.out.print("TD: "+data.promotion.Sections[data.getSectionDeGroup(j)[0]].ListeGroupes[data.getSectionDeGroup(j)[1]]+" "+data.promotion.Matieres[l].Intitule+"("+data.promotion.Occupation.SallesTD[k]+") "+data.promotion.Matieres[l].Profs[m]+" | ");
                            }
                    }
                }
            }
            System.out.println();

        }
    }

}
