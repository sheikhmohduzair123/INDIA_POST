import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListIteration {


    public static void main(String[] args) {

        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1, "saroj", 24));
        list.add(new Employee(2, "sahil", 20));
        list.add(new Employee(3, "suhani", 26));
        list.add(new Employee(4, "sunit", 28));

       /* for (Employee emp:list
             ) {
            System.out.println(emp);
        }*/
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("********************************************");
        Comparator<Employee> comparator=new Comparator<Employee>() {
            @Override
            public int compare(Employee e1,Employee e2) {
                return e1.name.compareTo(e2.name);
            }
        };
        Collections.sort(list,comparator);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


    }

}

class Employee implements Comparable<Employee> {
    Integer id;
    String name;
    Integer age;

    public Employee(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Employee o) {
        return this.age - o.age;
    }

}
