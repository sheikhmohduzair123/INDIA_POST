import java.util.HashSet;
import java.util.Set;

public class Isogram {
    public static void main(String[] args) {

        System.out.println(isogram("asdfgert"));

    }

    public static Boolean isogram( String str) {
        char[] ch = str.toCharArray();
       // System.out.println(ch);
        Set<Character> set = new HashSet<Character>();
        Boolean flag = false;
        for (Character c : ch) {
            if (set.contains(c)) {
                return flag;
            }
            set.add(c);
        }
      return flag=true;
    }
}
