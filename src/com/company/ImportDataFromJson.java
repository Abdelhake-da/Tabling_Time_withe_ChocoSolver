package com.company;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.security.SecureRandom;

public class ImportDataFromJson {
    String tab[];
    String tab1[];
    String tab2[];
    TJournee journees[]=new TJournee[0];
    TMatiere matieres[]=new TMatiere[0];
    TSection sections[]=new TSection[0];
    TOccupation occupations;
    TPromotion Promotion;
    TData data;

    public  String[] addstrelement(String array[],String val){
        String newArray[]=new String[array.length+1];
        for (int i=0;i<array.length;i++){
            newArray[i]=array[i];
        }
        newArray[array.length]=val;
        return newArray;
    }
    public  TJournee[] addjourelement(TJournee array[],TJournee val){
        TJournee newArray[]=new TJournee[array.length+1];
        for (int i=0;i<array.length;i++){
            newArray[i]=array[i];
        }
        newArray[array.length]=val;
        return newArray;
    }
    public  TMatiere[] addmatiereelement(TMatiere array[],TMatiere val){
        TMatiere newArray[]=new TMatiere[array.length+1];
        for (int i=0;i<array.length;i++){
            newArray[i]=array[i];
        }
        newArray[array.length]=val;
        return newArray;
    }
    public  TSection[] addsectionelement(TSection array[],TSection val){
        TSection newArray[]=new TSection[array.length+1];
        for (int i=0;i<array.length;i++){
            newArray[i]=array[i];
        }
        newArray[array.length]=val;
        return newArray;
    }
    public ImportDataFromJson(String Url){
        JSONParser jsonp= new JSONParser();
        try (FileReader reader = new FileReader(Url)){
            //read data file
            Object obj= jsonp.parse(reader);
            JSONObject promotion= (JSONObject) obj;
            JSONObject promo= (JSONObject) promotion.get("Promotion");

            JSONArray journe_s=(JSONArray) promo.get("Journee");
            journe_s.forEach(journe -> {
                String intitule=String.valueOf(((JSONObject)journe).get("intitule"));
                JSONArray seances=(JSONArray) ((JSONObject) journe).get("seance");
                tab=new String[0];
                seances.forEach(seance-> {
                    tab=addstrelement(tab,String.valueOf(seance));
                });

                journees=addjourelement(journees,new TJournee(intitule,tab));
            });

            JSONArray Matiears= (JSONArray ) promo.get("Matieres");
            Matiears.forEach(matiere ->{
                String intitule=((JSONObject)matiere).get("intitule").toString();
                int nbCoursParSemaine=Integer.parseInt(((JSONObject)matiere).get("nbCoursParSemaine").toString());
                int nbTDsParSemaine=Integer.parseInt(((JSONObject)matiere).get("nbTDsParSemaine").toString());
                int nbTPsParSemaine=Integer.parseInt(((JSONObject)matiere).get("nbTPsParSemaine").toString());
                JSONArray profs= (JSONArray ) (( JSONObject ) matiere).get("Profs");
                tab=new String[0];
                profs.forEach(prof -> {
                    tab= addstrelement(tab,prof.toString());
                });
                matieres=addmatiereelement(matieres,new TMatiere(intitule,nbCoursParSemaine,nbTDsParSemaine,nbTPsParSemaine,tab));
            });
            JSONArray Sections=(JSONArray ) promo.get("section");
            Sections.forEach(section -> {
                String intitule= ((JSONObject)section).get("intitule").toString();
                JSONArray groups=(JSONArray ) (( JSONObject ) section).get("group");
                tab=new String[0];
                groups.forEach(group ->{
                    tab= addstrelement(tab,group.toString());
                });
                sections=addsectionelement(sections,new TSection(intitule,tab));
            });
            JSONObject occupation= (JSONObject ) promo.get("occupation");
            JSONArray sallecours=(JSONArray ) occupation.get("salleCour");
            JSONArray salletps=(JSONArray ) occupation.get("salleTp");
            JSONArray salletds=(JSONArray ) occupation.get("salleTD");
            tab=new String[0];
            tab1=new String[0];
            tab2=new String[0];
            sallecours.forEach(sallecour->{
                tab= addstrelement(tab,sallecour.toString());
            });
            salletds.forEach(salletd->{
                tab1=addstrelement(tab1,salletd.toString());
            });
            salletps.forEach(salletp->{
                tab2=addstrelement(tab2,salletp.toString());
            });
            occupations=new TOccupation(tab,tab1,tab2);
            String promointitule=promo.get("intitule").toString();
            Promotion=new TPromotion(promointitule);
            Promotion.Matieres=matieres;
            Promotion.Sections=sections;
            Promotion.Journees= journees;
            Promotion.Occupation=occupations;
            data=new TData();
            data.promotion=Promotion;
        }catch (Exception e){
            System.out.println("erreur");
            e.printStackTrace();
        }
    }
    public TData getdata(){
        return data;
    }
}
