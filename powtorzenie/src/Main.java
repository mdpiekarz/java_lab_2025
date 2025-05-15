import java.util.List;

public class Main {


    public static void main(String[] args) {
        //Zadanie 1
//        DeathCauseStatistic statistic=DeathCauseStatistic.fromCsvLine("A02.1          ,5,-,-,-,-,-,-,-,-,-,-,-,-,1,2,-,1,1,-,-,-");
//        System.out.println(statistic.getKey());
////        int[] arr=statistic.getAgeBracket();
////        for(var a: arr){
////            System.out.println(a);
////        }
//
//        System.out.println(statistic.getDeaths(60));

        //Zadanie 3a
        DeathCauseStatisticsList statisticsList=new DeathCauseStatisticsList();
        statisticsList.repopulate("zgony.csv");
//        System.out.println(statisticsList.get(5).getKey());



        List<DeathCauseStatistic> mostDeadly= statisticsList.mostDeadlyDiseases(1,100);
//        for(var diseases : mostDeadly)
//            System.out.println(diseases.getKey());


        //Zadanie 4
        //DeathCauseStatisticsList deaths = new DeathCauseStatisticsList();
        //deaths.repopulate("zgony.csv");

        ICDCodeTabular codes = new ICDCodeTabularOptimizedForMemory("icd10.txt");
        //System.out.println(codes.getDescription("A17.0"));

        for(var diseases : mostDeadly) {
            System.out.print(diseases.getKey());
            System.out.print(" - ");
            try {
                System.out.println(codes.getDescription(diseases.getKey()));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("???");
            }
        }


    }


}
