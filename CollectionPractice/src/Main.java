import com.sun.jdi.Value;

import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        HashMap<Integer, String> employee = new HashMap<>();
        employee.put(1, "saroj");
        employee.put(2, "hitesh");
        employee.put(3, "saleem");
        employee.put(4, "kareem");


        //Iterating using Entryset

       /* Iterator<Map.Entry<Integer, String>> integerStringIterator = employee.entrySet().iterator();
        while (integerStringIterator.hasNext()) {
            Map.Entry<Integer, String> entry = integerStringIterator.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());


        }*/

        //Using Keyset

        /*Iterator<Integer> iterator = employee.keySet().iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            System.out.println(key);
            System.out.println(employee.get(key));

        }*/

        //using foreach loop

       /* for (Map.Entry<Integer,String > entry:employee.entrySet()
             ) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }*/

        //Using Lambda Expression

       /* employee.forEach((Key, Value)->{ System.out.println(    Key);
                    System.out.println(Value);
        }
        );*/
        //Using Stream Api

     /*   employee.entrySet().stream().forEach((face)->{ System.out.println(face.getValue());
            System.out.println(face.getKey());});
    }*/

        //using the entryset
      /*  Iterator<Map.Entry<Integer,String>> iterator =employee.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer,String> entry=iterator.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());

        }*/

        //using the keyset
    /* Iterator<Integer> key = employee.keySet().iterator();
        while (key.hasNext()){
            Integer k=key.next();
            System.out.println(k);
            System.out.println(employee.get(k));

        }*/
        //using foreach
       /* for (Map.Entry<Integer,String> emp:
             employee.entrySet()) {
            System.out.println(emp.getValue());
            System.out.println(emp.getKey());
        }*/
        //using the lambda expression
       /* employee.forEach((key,value)->{
            System.out.println(key);
            System.out.println(value);
        });*/

        //using the stream api
        employee.entrySet().stream().forEach(u-> {
            System.out.println(u.getKey());
            System.out.println(u.getValue());
        });


    }
}
