/* DTLearn.java
 * 
 * Decision tree learning in java
 * Allows an agent to learn from datasets
 * Produces a guess at the relation between given datasets
 * Author: Ethan VanHoutven
*/

import java.lang.*;
import java.util.*;

public class DTLearn{

    public static void main(String[] args){
        Sample sample;
        Scheme scheme;

        if(args.length < 2){
            System.out.println("Please provide input files \"sample\" \"scheme\"");
            System.exit(0);
        }

        System.out.println("Learning starts:");

        sample = new Sample(args[0]);
        scheme = new Scheme(args[1]);
        Node tree = LearnDecisionTree(sample, scheme.GetAttrWithoutFX(), "");
        
        AskToPrint(tree);

        /*Tests for each major class of this program*/
        // testScheme();
        // testExample();
        // testSample();
        // testMaths();   
        // testDT();
        
    }

    public static Node LearnDecisionTree(Sample g, List<Attribute> attrbs, String sMajor){
        if(g.isEmpty()){
            return new Node(sMajor);
        }
        if(g.AllAreOneVal()){ // If all examples in group are q
            List<Example> q;
            q = g.GetExamples();

            return new Node(g);
        }
        if(attrbs.isEmpty()){
            return new Node(majorityVal(g));
        }
        Attribute b = g.GetAttribute(attrbs,g.GetExamples());

        System.out.println("    Split on: " + b.GetName());

        Node tr = new Node(b);

        String m = g.getMajVal();
        //m.setParent(sMajor);

        for(String s: b.GetVals()){
            //create subgroup of examples with value s
            Sample subg = new Sample(b.GetName(),s,g);
            List<Attribute> temp = new ArrayList<Attribute>();
            temp = attrbs;
            temp.remove(b);
            //System.out.println(temp);
            Node subtr = LearnDecisionTree(subg,temp,m);
            tr.addChild(subtr);
        }

        return tr;
    }

    public static Sample majorityVal(Sample g){
        return new Sample (g.getMajVal(),g);
    }

    public static void AskToPrint(Node tree){
        Scanner kbd = new Scanner (System.in);
        System.out.println("Print tree? y/n");
        String decision = kbd.nextLine();

        tree.Print();
    }

    /*Tests code*/
    private static void testScheme(){
        Scheme test;
        System.out.println("Testing Scheme class");
        System.out.println("Test1: -----------------");
        test = new Scheme("Pasta_sche.txt");
        test.Print();
        System.out.println("Test2: -----------------");
        test = new Scheme("Loan_sche.txt");
        test.Print();
        System.out.println("Test3: -----------------");
        test = new Scheme("Rest_sche.txt");
        test.Print();
        System.out.println("-----------------------Test Complete------------------------");
    }

    private static void testExample(){
        Example test;
        List<String> t1n = Arrays.asList("One", "Two", "Func");
        List<String> t1v = Arrays.asList("YES","NO","FAIL");

        List<String> t2n = Arrays.asList("");
        List<String> t2v = Arrays.asList("YES","NO","BLUE","FAIL");

        List<String> t3n = Arrays.asList("One", "Two", "Func", "AGAIN", "AGAIN", "One_MORE~adf");
        List<String> t3v = Arrays.asList("YES","NO","FAIL");
        System.out.println("Testing Example class");
        System.out.println("Test1: -----------------");
        test = new Example(t1n, t1v);
        test.Print();
        System.out.println("Test2: -----------------");
        test = new Example(t2n,t2v);
        test.Print();
        System.out.println("Test3: -----------------");
        test = new Example(t3n, t3v);
        test.Print();
        System.out.println("-----------------------Test Complete------------------------");
    }

    private static void testSample(){
        Sample test;

        System.out.println("Testing Sample class");
        System.out.println("Test1: -----------------");
        test = new Sample("Pasta.txt");
        test.Print();
        
        System.out.println("Test2: -----------------");
        test = new Sample("Loan.txt");
        test.Print();
        
        System.out.println("Test3: -----------------");
        test = new Sample("Rest.txt");
        test.Print();

        System.out.println("-----------------------Test Complete------------------------");
    }

    private static void testMaths(){
        Sample samp;
        Scheme scheme;

        System.out.println("Testing Get Attribute - and by proxy getRemainder");
        samp = new Sample("Pasta.txt");
        scheme = new Scheme("Pasta_sche.txt");
        System.out.println(samp.GetAttribute(scheme.GetAttrWithoutFX(), samp.GetExamples()));
        
        samp = new Sample("Loan.txt");
        scheme = new Scheme("Loan_sche.txt");
        System.out.println(samp.GetAttribute(scheme.GetAttrWithoutFX(), samp.GetExamples()));
        
        samp = new Sample("Rest.txt");
        scheme = new Scheme("Rest_sche.txt");
        System.out.println(samp.GetAttribute(scheme.GetAttrWithoutFX(), samp.GetExamples()));
        
        System.out.println("-----------------------Test Complete------------------------");
    }

    private static void testDT(){
        Sample samp;
        Scheme scheme;
        System.out.println("Testing Get Learn Decision tree");
        System.out.println("Test 1");
        samp = new Sample("Loan.txt");
        scheme = new Scheme("Loan_sche.txt");

        LearnDecisionTree(samp, scheme.GetAttrWithoutFX(), "");
        
        System.out.println("Test 2");
        samp = new Sample("Rest.txt");
        scheme = new Scheme("Rest_sche.txt");

        LearnDecisionTree(samp, scheme.GetAttrWithoutFX(), "");
        
        System.out.println("Test 3");
        samp = new Sample("Pasta.txt");
        scheme = new Scheme("Pasta_sche.txt");

        LearnDecisionTree(samp, scheme.GetAttrWithoutFX(), "");
        System.out.println("-----------------------Test Complete------------------------");
 
    }
}