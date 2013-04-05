/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HMM;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author RAGHUTEJA
 */
public class Precision_Recall {
    String filename;
    
    Precision_Recall(String file_name) {
        filename = file_name;
    }
    
    float[][] PR() throws FileNotFoundException {
        FileReader file = null;
        int size  = 0;
        int partsize = 0;
        String[] sentences = null;
        float[][] final_output;
        
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br_scan = new BufferedReader(new InputStreamReader(in));
            String line = "";
            
            while ((line = br_scan.readLine()) != null)   {
                size++;
            }
            partsize = size/5;
            if(size%5 != 0) partsize++;
            sentences = new String[size];
            
            FileInputStream fstream_parse = new FileInputStream(filename);
            DataInputStream in_parse = new DataInputStream(fstream_parse);
            BufferedReader br_parse = new BufferedReader(new InputStreamReader(in_parse));
            int ind = 0;
            while ((line = br_parse.readLine()) != null)   {
                line = line.toLowerCase();
                sentences[ind] = line;
                ind++;
            }
        } catch (Exception e) {
//            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
        // Ignore issues during closing 
                }
            }
        }
        final_output = new float[3][size+1];

        System.out.println(size);
        System.out.println(partsize);

        float exact_matches = 0;
        float hmm_assigned_tags = 0;
        float actual_tags = 0;
        
        for(int i=0; i<5; i++){
            String[] train_data;
            String[] test_data;
            hashtable table;
            float states[][];
            float start_state[];
            float end_state[];
            if(i != 4){
                test_data = new String[partsize];
                train_data = new String[size-partsize];
                int train_index = 0, test_index = 0;
                for(int j=0; j<size; j++){
                    String[] input;
                    input = sentences[j].split(" ");
                    
                    if((j >= (i*partsize)) && (j<((i+1)*partsize))) {
//                        System.out.println(sentences[j]);
                        String line = "";
                        for(int k=0; k<input.length-1; k++){
                            line = line + input[k].subSequence(0, input[k].length()-2) + " ";
                        }
                        line = line + input[input.length-1].subSequence(0, input[input.length-1].length()-2);
                        test_data[test_index] = line;
                        test_index++;
                    }
                    else {
                        train_data[train_index] = sentences[j];
                        train_index++;
                    }
                }
            } else {
                test_data = new String[size-(4*partsize)];
                train_data = new String[4*partsize];
                int train_index = 0, test_index = 0;
                for(int j=0; j<size; j++){
                    String[] input;
                    input = sentences[j].split(" ");
                
                    if(j >= (4*partsize)) {
                        String line = "";
                        for(int k=0; k<input.length-1; k++){
                            line = line + input[k].subSequence(0, input[k].length()-2) + " ";
                        }
                        line = line + input[input.length-1].subSequence(0, input[input.length-1].length()-2);
                        test_data[test_index] = line;
                        test_index++;
                    }
                    else {
                        train_data[train_index] = sentences[j];
                        train_index++;
                    }
                }
            }
/*            for(int l=0; l<test_data.length; l++){
                System.out.println(test_data[l]);
            }*/
            buildtables bt = new buildtables(train_data);
            table = bt.table;
//            table.printtable();
            states = bt.states;
            start_state = bt.start_state;
            end_state = bt.end_state;
            
            HMM_main main = new HMM_main(states,start_state,table);
            for(int j=0; j<test_data.length; j++){
                char[] output = main.viterbi_res(test_data[j]);
                float matches = 0;
                float hmm_assigntags = 0;
                
                String[] input;
                input = sentences[(i*partsize)+j].split(" ");
                
                for(int k=0; k<input.length; k++){
//                    System.out.print(output[k] + ",");
                    if(output[k] != ' ') hmm_assigntags++;
                    if(Character.toLowerCase(output[k]) == Character.toLowerCase(input[k].charAt(input[k].length()-1))){
                        matches++;
                    }
                }
                
                exact_matches = exact_matches + matches + 2;
                hmm_assigned_tags = hmm_assigned_tags + hmm_assigntags + 2;
                actual_tags = actual_tags + test_data[j].length() + 2;
//                System.out.println(hmm_assigntags);
                final_output[0][(i*partsize)+j] = (matches+2)/(hmm_assigntags+2);
                final_output[1][(i*partsize)+j] = (matches+2)/(test_data[j].length()+2);
                final_output[2][(i*partsize)+j] = (2*final_output[0][(i*partsize)+j]*final_output[1][(i*partsize)+j])/(final_output[0][(i*partsize)+j] + final_output[1][(i*partsize)+j]);
                
            }
        }
//        size = final_output[0].length;
//        for(int z=0; z<size; z++){
//            System.out.println(final_output[0][z] + "," + final_output[1][z] + "," + final_output[2][z]);
//        }
        final_output[0][size] = exact_matches/hmm_assigned_tags;
        final_output[1][size] = exact_matches/actual_tags;
        final_output[2][size] = (2*exact_matches*actual_tags)/(exact_matches+actual_tags);
        
        return final_output;
    }
}
