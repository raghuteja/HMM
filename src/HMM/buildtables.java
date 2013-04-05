/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HMM;

/**
 *
 * @author RAGHUTEJA
 */
public class buildtables {
    float states[][] = new float[5][5];
    float start_state[] = new float[5];
    float end_state[] = new float[5];
    hashtable table = new hashtable();
    
    buildtables (String[] sent){
        String [] sentences = sent;
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                states[i][j] = 0;
            }
            start_state[i] = 0;
            end_state[i] = 0;
        }
        String line;
        for(int j=0; j<sentences.length; j++){
            line = sentences[j];
            String[] input;
            input = line.split(" ");
            char last = input[0].charAt(input[0].length()-1);
            String remaining = input[0].substring(0, input[0].length() - 2);
            int code = -1;
            if((last == 'N') || (last == 'n')) code = 0;
            else if((last == 'V') || (last == 'v')) code = 1;
            else if((last == 'A') || (last == 'a')) code = 2;
            else if((last == 'R') || (last == 'r')) code = 3;
            else if((last == 'O') || (last == 'o')) code = 4;
            table.insert(remaining, code);
            start_state[code] = start_state[code] + 1;
            int prev_code = code;
            for(int i=1; i<input.length; i++){
                last = input[i].charAt(input[i].length()-1);
                remaining = input[i].substring(0, input[i].length() - 2);
                code = -1;
                if((last == 'N') || (last == 'n')) code = 0;
                else if((last == 'V') || (last == 'v')) code = 1;
                else if((last == 'A') || (last == 'a')) code = 2;
                else if((last == 'R') || (last == 'r')) code = 3;
                else if((last == 'O') || (last == 'o')) code = 4;
                table.insert(remaining, code);
                states[prev_code][code] = states[prev_code][code] + 1;
                prev_code = code;
                if(i == input.length -1){
                    end_state[code] = end_state[code] + 1;
                }
            }
        }
//        table.printtable();
        for(int i=0; i<5; i++){
            float sum = 0;
            for(int k=0; k<5; k++){
                sum = sum + states[i][k];
            }
            for(int k=0; k<5; k++){
                states[i][k] = states[i][k]/sum;
            }
        }
        float sum = 0;
        for(int i=0; i<5; i++){
            sum = sum + start_state[i];
        }
        for(int i=0; i<5; i++){
            start_state[i] = start_state[i]/sum;
        }
        sum = 0;
        for(int i=0; i<5; i++){
            sum = sum + end_state[i];
        }
        for(int i=0; i<5; i++){
            end_state[i] = end_state[i]/sum;
        }
        table.complete_insertion();
    }
}