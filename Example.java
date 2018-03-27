/* Example.java
 * Maintains a list of attribute values and a function value given in an sample
 * very similar to attribute
 * a function value is for guessing the structure of the function
 * Author: Ethan VanHoutven
*/

import java.util.*;

public class Example{
    private List<String> names;
    private List<String> vals;

    public Example(List<String> nm, List<String> v){
        names = new ArrayList<String>();
        vals = new ArrayList<String>();
        names.addAll(nm);
        vals.addAll(v);
    }

    public void Print(){
        System.out.println(names);
        System.out.println(vals);
    }

    public void PrintVals(){
        System.out.println(vals);
    }
}