import java.util.*;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> pairList = new ArrayList<>();
        pairList.add(1);
        pairList.add(3);
        pairList.add(2);
        ArrayList<String> pairList1 = new ArrayList<>();
        pairList1.add("白色");
        pairList1.add("5.5");
        pairList1.add("磨砂");
        System.out.println(pairList1);


        HashMap map=new HashMap();
        for(int i=0;i<pairList.size();i++)
        {
            map.put(pairList.get(i),i); //将值和下标存入Map
        }
        Collections.sort(pairList);
        System.out.println(pairList);
       ArrayList<String> originalList = new ArrayList<>();
        for(int i=0;i<pairList.size();i++)
        {
            originalList.add(pairList1.get((Integer) map.get(pairList.get(i))));
            System.out.println(map.get(pairList.get(i)));
        }
        System.out.println(originalList);
    }

}
