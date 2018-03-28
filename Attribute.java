/* Attribute.java
 * Author: Ethan VanHoutven
*/

import java.util.*;

public class Attribute{
    private String name;
    private List<String> possibleValues;

    Attribute(String nm, List<String> values){
        name = nm;
        possibleValues = new ArrayList<String>(); 
        possibleValues.addAll(values);
    }
    
    public int GetNumVal(){
        return possibleValues.size();
    }

    public String GetVal(int index){
        return possibleValues.get(index);
    }

    public String GetName(){
        return name;
    }

    public String toString(){
        return name + " " + possibleValues;
    }
}