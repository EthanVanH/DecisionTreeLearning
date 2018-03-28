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
        testScheme();
        testExample();
        testSample();
        testMaths();    
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

        System.out.println(samp.GetAttribute(scheme.GetAttributes(), samp.GetExamples()));
        System.out.println("-----------------------Test Complete------------------------");
    }
}