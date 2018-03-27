/* Util.java
 * Utility class for decision tree learning
 * loads text into strings
 * Author: Ethan VanHoutven
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class UTIL{

    public UTIL(){

    }

    public String ReadFile(String filename){
        BufferedReader reader;
        String content;
        content = "";

        try{
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while((line = reader.readLine()) != null){
                if(line.length() != 0){
                    content += line;
                    content += " ";
                }
            }
            reader.close();
        }
        catch(Exception e){
            System.out.println("Exception in UTIL.ReadFile - " + e);
        }
        return content;
    }

    public List<String> ReadFileLines(String filename){
        BufferedReader reader;
        List<String> content;

        content = new ArrayList<String>();
        
        try{
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while((line = reader.readLine()) != null){
                line = line.replaceAll("\\s+","|");
                content.add(line);
            }
            reader.close();
        }
        catch(Exception e){
            System.out.println("Exception in UTIL.ReadFile - " + e);
        }
        return content;
    }
}