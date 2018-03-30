/* Sample.java
 * A set of sample data that represents what has been experienced
 * Author: Ethan VanHoutven
*/

import java.util.*;
import java.util.Map.Entry;

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
    
    public Sample(String name, String val, Sample og){
        //Creates a sample without attributes named name

        fxvalues = og.Getfxvalues();
        samp = new ArrayList<Example>();
        atributes = new ArrayList<String>();
        atributes.addAll(og.GetAttributes());
        atributes.remove(val);
        for(Example e : og.GetExamples()){
            int index = e.GetIndexOf(name);
            
            if(index != -1){
                e.RemoveAtt(index);
                if(e.GetValAt(index).equals(val)){
                samp.add(e);
                }
            }
            else{
                samp.add(e);
            }
            
        }
        
        
    }

    public Sample(String val, Sample og){
        //Creates a sample of all the examples witha  given value
        fxvalues  = new ArrayList<String>();
        fxvalues.add(val);
        samp = new ArrayList<Example>();
        atributes = new ArrayList<String>();
        atributes.addAll(og.GetAttributes());
        samp.addAll(og.GetAllSampsWith(val));
          
    }

    public List<String> GetAttributes(){
        return atributes;
    }

    public boolean AllAreOneVal(){
        String v1 = fxvalues.get(0);
        for(int i = 0; i < fxvalues.size();i++){
            if(!v1.equals(fxvalues.get(i))){
                return false;
            }
        }
        return true;
    }

    public String getMajVal(){
        Map<String, Integer> map = new HashMap<>();

        for(String s: fxvalues){
            Integer val = map.get(s);
            map.put(s, val == null ? 1: val+1);
        }

        Map.Entry<String, Integer> max = null;

        for(Map.Entry<String, Integer> e : map.entrySet()){
            if(max == null || e.getValue() > max.getValue()){
                max = e;
            }
        }

        return max.getKey();
    }

    public String Getfxvalue(){
        return fxvalues.get(0);
    }

    public List<String> Getfxvalues(){
        return fxvalues;
    }

    //Returns the index of a given attribute name
    public int GetIndex(String attrname){
        for(int i = 0; i < atributes.size(); i++){
            if(atributes.get(i).equals(attrname)){
                return i;
            }
        }
        return -1;
    }

    public List<Example> GetAllSampsWith(String givenfx){
        List<Integer> indicies = new ArrayList<Integer>();

        for(int i = 0; i < samp.size(); i++){
            if(samp.get(i).getFX().equals(givenfx)){
                indicies.add(i);
            }
        }

        List<Example> samps = new ArrayList<Example>();

        for(int i = 0; i < indicies.size(); i++){
            samps.add(samp.get(indicies.get(i)));
        }
        return samps;
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

    //getAttribute - returns the attribute to test on
    public Attribute GetAttribute(List<Attribute> a, List<Example> g){
        int k = fxvalues.size();
        double info = GetInfo(g,k);
        double maxGain = -1;
        Attribute bestA = null;

        for(int i = 0; i < a.size(); i++){//for each possible value of a
            Attribute b = a.get(i);
            double remainder = GetRemainder(b,g,k);
            double gain = info - remainder;
            //System.out.println("Gain of " + b + " is " + gain + "|" + info + "|" + remainder);
            System.out.println("  Test " + b.GetName() + ": gain = " + gain);
            if(gain > maxGain){
                maxGain = gain;
                bestA = b;
            }
        }
        
        return bestA;
    }

    public boolean isEmpty(){
        return samp.isEmpty();
    }

    public List<Example> GetExamples(){
        return samp;
    }

    public String toString(){
        return atributes.toString() + samp.toString();
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