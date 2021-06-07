package com.company;

public class Main {

    public static void main(String[] args) {
	//
        ImportDataFromJson importdata=new ImportDataFromJson("data.json");
        TData data=importdata.getdata();

//        System.out.println(data.getNbSeancesHoraireParSemaine());
//        System.out.println(data.getNbSalleTDs());
//        System.out.println(data.getLeNombreMaxDesProf());
//        System.out.println(data.getNbGroups());
//        System.out.println(data.getNbSalleCours());
//        System.out.println(data.getNbSalleTPs());
//        System.out.println(data.getNbSection());
        TTretment2 Tretment=new TTretment2(data);
        Tretment.SolvetProblem();
    }
}
