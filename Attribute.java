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

    public ArrayList<String> GetVals(){
        return possibleValues;
    }

    public String name(){
        return name;
    }

    public String toString(){
        return name + " " + possibleValues;
    }
}