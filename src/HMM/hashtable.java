/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HMM;

/**
 *
 * @author RAGHUTEJA
 */

/*
 * Quardratic hashing
 */
public class hashtable {
    hashnode table[] = new hashnode[10000];
    
    hashtable() {
        for(int i=0; i<10000; i++) {
            table[i] = null;
        }
    }
    
    void printtable(){
        for(int i=0; i<10000; i++) {
            if(table[i] != null) {
                System.out.println(i+"-" + table[i].word + "-" + table[i].p_N + "-" + table[i].p_V + "-" + table[i].p_A + "-" + table[i].p_R + "-" + table[i].p_O);
            }
        }
    }
    
    void insert(String s, int j){
//        System.out.println(s);
        int index = hash_code(s);
        int i = 1;
        while(table[index] != null) {
            if(table[index].word.equals(s))
                break;
            else {
                index = index + (i*i);
                index = index % 10000;
                i = i + 1;
            }
        }
        if(table[index] == null) {
            hashnode n = new hashnode();
            n.word = s;
            if(j == 0) n.p_N = 1;
            else if(j == 1) n.p_V = 1;
            else if(j == 2) n.p_A = 1;
            else if(j == 3) n.p_R = 1;
            else if(j == 4) n.p_O = 1;
            table[index] = n;
        } else {
            hashnode n = table[index];
            if(j == 0) n.p_N += 1;
            else if(j == 1) n.p_V += 1;
            else if(j == 2) n.p_A += 1;
            else if(j == 3) n.p_R += 1;
            else if(j == 4) n.p_O += 1;
        }
    }
    
    float find(String s, int code){
        int index = hash_code(s);
        int i = 1;
        while(table[index] != null) {
            if(i > 10000) break;
            if(table[index].word.equals(s))
                break;
            else {
                index = index + (i*i);
                index = index % 10000;
                i = i + 1; 
            }
        }
        if((i > 10000) || (table[index] == null)) return -1;
        else if(code == 0) return table[index].p_N;
        else if(code == 1) return table[index].p_V;
        else if(code == 2) return table[index].p_A;
        else if(code == 3) return table[index].p_R;
        else if(code == 4) return table[index].p_O;
        else return -1;
    }
    
    int hash_code(String s){
        char ch[];
        ch = s.toCharArray();
        int i, sum;
        for (sum=0, i=0; i < s.length(); i++)
            sum += ch[i];
        return sum % 10000;
    }
    
    void complete_insertion() {
        float sum = 0;
        for(int i=0; i<10000; i++){
            if(table[i] != null){
                sum = table[i].p_A + table[i].p_N + table[i].p_O + table[i].p_R + table[i].p_V;
                table[i].p_A = table[i].p_A/sum;
                table[i].p_N = table[i].p_N/sum;
                table[i].p_V = table[i].p_V/sum;
                table[i].p_R = table[i].p_R/sum;
                table[i].p_O = table[i].p_O/sum;
            }
        }
    }
}
