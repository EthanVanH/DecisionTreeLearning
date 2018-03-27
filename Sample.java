/* Sample.java
 * A set of sample data that represents what has been experienced
 * Author: Ethan VanHoutven
*/

import java.util.*;

public class Sample{
    private List<String> atributes;
    private List<Example> samp;
    private List<String> fxvalues;

    public Sample(String filename){
        UTIL reader;
        List<String> examples;
        List<String> atrbNames = new ArrayList<String>();
        fxvalues = new ArrayList<String>();

        reader = new UTIL();
        examples = reader.ReadFileLines(filename);
        
        atrbNames.addAll(Arrays.asList(examples.get(0).split("\\|")));

        samp = new ArrayList<Example>();
        atributes = atrbNames;
        for(int i = 1; i < examples.size(); i++){
            List<String> atrbVals = new ArrayList<String>();
            atrbVals.addAll(Arrays.asList(examples.get(i).split("\\|")));
            atrbVals.removeAll(Arrays.asList("", null));

            if(!fxvalues.contains(atrbVals.get(atrbVals.size()-1))){
                fxvalues.add(atrbVals.get(atrbVals.size()-1));
            }
            samp.add(new Example(atrbNames,atrbVals));
        }

        
    }


    //Group in the form {atrributename, value1, value2, valuen} where value is the function value of that example
    //k represents the number of possible *function* values
    //      as in the end of tree values.
    //function finds the percent I of choosing a group
    //returns that I
    public double GetInfo(List<String> group, int k){
        double I;
        float prob;
        int size = group.size() - 1;
        int[] count = new int[k];

        for(int i = 0; i < k; i++){
            count[i] = 0;
        }

        for(int i = 0; i < k; i++){
            for(int j = 1; j <= size; j++){
                if(group.get(j).equals(fxvalues.get(i))){
                    count[i]++;
                }
            }
        }

        I = 0.0;

        for(int i = 0; i < k; i++){
            prob = (float)count[i] / (float)size;
            I = I - (prob * (Math.log(prob)/Math.log(2)));
        }
        return I;
    }

    //attribute is an attribute name
    //group is the attribute value to split out
    //k is the number of possible values of fx
    //GetRemainder returns a double representing the percent all examples left to test after testing the given attribute
    public double GetRemainder(Attribute attribute, List<String> group, int k){
        double remainder = 0.0;


        return remainder;
    }

    //TODO getAttribute - returns the attribute to test on
    public void GetAttribute(){

    }

    public void Print(){
        System.out.println("Sample:");
        System.out.println(atributes);
        for(int i = 0; i < samp.size();i++){
            samp.get(i).PrintVals();
        }
        System.out.println("Possible f(x) values : " + fxvalues);
    }
}