/* Scheme.java
 * Maintains the list of attributes
 * Author: Ethan VanHoutven
*/

import java.util.*;

public class Scheme{
    private int count;
    private List<Attribute> atrbs;

    private void LoadScheme(String fname){
        UTIL reader; 
        String scheme;
        List<String> attributes = new ArrayList<String>();

        reader = new UTIL();
        scheme = reader.ReadFile(fname);

        //Scheme now in form "NumberOfAttributes Attributename numPossibleVals Val1 Val2 Valn AttributeName ..."
        attributes.addAll(Arrays.asList(scheme.split(" ")));
        
        //Remove spaces and trailing /r/n 
        for(int i = 0; i < attributes.size(); i++){
            attributes.set(i, attributes.get(i).trim());
        }

        count = Integer.parseInt(attributes.get(0).trim()); 
        int i = 1;
        int cntr = 0;
        atrbs = new ArrayList<Attribute>();

        while(cntr < count){
            int startv = i+2;
            int diff = Integer.parseInt(attributes.get(i+1));
            int endv = startv + diff;
            
            atrbs.add(new Attribute(attributes.get(i), attributes.subList(startv, endv)));
            i = endv;
            cntr++;
        }
        // System.out.println(atrbs);
    }

    public List<Attribute> GetAttributes(){
        return atrbs;
    }

    public List<Attribute> GetAttrWithoutFX(){
        List<Attribute> temp = new ArrayList<Attribute>();

        for(int i = 0; i < atrbs.size()-1;i++){
            temp.add(atrbs.get(i));
        }
        return temp; 
    }

    public Scheme(String filename){
        this.LoadScheme(filename);
    }

    public void Print(){
        System.out.println(atrbs);
    }

}