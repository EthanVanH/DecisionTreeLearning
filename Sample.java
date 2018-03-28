/* Sample.java
 * A set of sample data that represents what has been experienced
 * Author: Ethan VanHoutven
*/

import java.util.*;

public class Sample{
    private List<String> atributes;
    public List<Example> samp;
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


    //Group is the spliting 'out' of a possible value of an attribute
    //k represents the number of possible *function* values
    //      as in the end of tree values.
    //function finds the percent I of choosing a group
    //returns that I
    public double GetInfo(List<Example> group, int k){
        double I;
        float prob;
        int size = group.size();
        int[] count = new int[k];

        for(int i = 0; i < k; i++){
            count[i] = 0;
        }

        for(int i = 0; i < k; i++){
            for(int j = 1; j < size; j++){
                if(group.get(j).getFX().equals(fxvalues.get(i))){
                    count[i]++;
                }
            }
        }

        I = 0.0;

        for(int i = 0; i < k; i++){
            prob = (float)count[i] / (float)size;
            if(count[i] == 0){
                prob = 1;
            }

            I = I - (double)(prob * (Math.log(prob)/Math.log(2)));
            
        }
        return I;
    }

    //B is an attribute to split on.
    //group is the attribute value to split out
    //k is the number of possible values of fx
    //GetRemainder returns a double representing the percent all examples left to test after testing the given attribute
    public double GetRemainder(Attribute b, List<Example> g, int k){
        double remainder = 0.0;
        int size = g.size();
        int m = b.GetNumVal();
        int index = 0;
        int[] counts = new int[m];// list of number of examples with value at the index

        List<Example>[] subg = new ArrayList[m]; //sublists oh my java doesnt like this

        for(int i = 0; i < m; i++){
            subg[i] = new ArrayList<Example>();
            counts[i] = 0;
        }

        //Gets the index of the attribute b in the string of values of each example
        for(int i = 0; i < atributes.size(); i++){
            if(b.GetName().equals(atributes.get(i))){
                index = i;
                break;
            }
        }

        for(Example e : g){
            for(int i = 0; i < m; i ++){
                if(e.GetValAt(index).equals(b.GetVal(i))){
                    subg[i].add(e);
                    counts[i]++;
                }
            }
        }

        for(int i = 0; i < m; i++){
            double prob = (double)counts[i] / (double)size;
            double inf = GetInfo(subg[i], k);
            remainder += prob*inf;
        }

        return remainder;
    }

    //TODO getAttribute - returns the attribute to test on
    public Attribute GetAttribute(List<Attribute> a, List<Example> g){
        int k = fxvalues.size();
        double info = GetInfo(g,k);
        double maxGain = -1;
        Attribute bestA = null;

        for(int i = 0; i < a.size()-1; i++){//for each possible value of a
            Attribute b = a.get(i);
            double remainder = GetRemainder(b,g,k);
            double gain = info - remainder;
            //System.out.println("Gain of " + b + " is " + gain + "|" + info + "|" + remainder);
            if(gain > maxGain){
                maxGain = gain;
                bestA = b;
            }
        }

        return bestA;
    }

    public List<Example> GetExamples(){
        return samp;
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