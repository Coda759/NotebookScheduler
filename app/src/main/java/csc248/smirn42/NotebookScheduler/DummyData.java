package csc248.smirn42.NotebookScheduler;

import java.util.ArrayList;

public class DummyData {

  public static ArrayList<String> arrayList = new ArrayList<>();
  public static ArrayList<String> eventsOn1542021 = new ArrayList<>();

   public DummyData(){
        fillArrayList("06-May-2021" , "10-May-2021");
        eventsOn1542021.add("home work");
        eventsOn1542021.add("online meetings");
   }

   public static void fillArrayList(String d1 , String d2){
       arrayList.add("06-May-2021");
       arrayList.add("07-May-2021");
       arrayList.add("08-May-2021");
       arrayList.add("09-May-2021");

   }

}
