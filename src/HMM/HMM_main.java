/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HMM;

/**
 *
 * @author RAGHUTEJA
 */
public class HMM_main {
    /*
     * 0 - N
     * 1 - V
     * 2 - A
     * 3 - R
     * 4 - O
     */
   float states[][] = new float[5][5];
   float start_state[] = new float[5];
   hashtable table;
   treenode nodes_5[] = new treenode[5];
   treenode temp_nodes_5[] = new treenode[5];
   
   HMM_main(float ss[][], float start_s[],  hashtable t){
       table = t;
       for(int i=0; i<5; i++){
           for(int j=0; j<5; j++){
               states[i][j] = ss[i][j];
//               System.out.print(states[i][j] + " - ");
           }
//           System.out.println();
           start_state[i] = start_s[i];
       }
   }
   
   void printnode(treenode n){
//       System.out.println(n.p_NOW + "#" + n.state);
   }
   
   char[] viterbi_res(String s){
       String[] input;
       
       input = s.split(" ");
       char[] output = new char[input.length];
              for(int i=0; i<5; i++){
           treenode t = new treenode();
           t.p_NOW = table.find(input[0],i)*start_state[i];
           if(t.p_NOW < 0) {
//               output = output + "Word " + input[0] + " doesnt found in the corpus.";
//               return output;
               t.exist = false;
               t.p_NOW = start_state[i];
           }
           t.state = i;
           nodes_5[i] = t;
       }
       for(int i=0; i<5; i++){
           printnode(nodes_5[i]);
       }
       for(int i=1; i<input.length; i++){
//           System.out.println(input[i]);
           for(int j=0; j<5; j++){
               treenode t = new treenode();
               t.p_NOW = table.find(input[i],j)*states[0][j]*nodes_5[0].p_NOW;
               if(t.p_NOW < 0) {
//                   output = output + "Word " + input[i] + " doesnt found in the corpus.";
//                   return output;
                   t.exist = false;
                   t.p_NOW = states[0][j]*nodes_5[0].p_NOW;
               }
               t.state = j;
               t.parent = nodes_5[0];
               temp_nodes_5[j] = t;
           }
           for(int j=1; j<5; j++){
               for(int k=0; k<5; k++){
                   if((table.find(input[i],k)*states[j][k]*nodes_5[j].p_NOW) > (temp_nodes_5[k].p_NOW)){
                       treenode t = new treenode();
                       t.p_NOW = table.find(input[i],k)*states[j][k]*nodes_5[j].p_NOW;
                       if(t.p_NOW < 0) {
//                           output = output + "Word " + input[i] + " doesnt found in the corpus.";
//                           return output;
                           t.exist = false;
                           t.p_NOW = states[j][k]*nodes_5[j].p_NOW;
                       }
                       t.state = k;
                       t.parent = nodes_5[j];
                       temp_nodes_5[k] = t;
                   }
               }
           }
           for(int j=0; j<5; j++){
               nodes_5[j] = temp_nodes_5[j];
           }
       }
       treenode max_node = nodes_5[0];
       for(int i=1; i<5; i++){
           if(max_node.p_NOW < nodes_5[i].p_NOW) {
               max_node = nodes_5[i];
           }
       }
       for(int i=input.length-1; i>=0; i--) {
//           System.out.print(max_node.state + ",");
           if(max_node.exist == false) output[input.length -1 -i] = ' ';
           else {
               if(max_node.state == 0) output[input.length -1 -i] = 'n';
               else if(max_node.state == 1) output[input.length -1 -i] = 'v';
               else if(max_node.state == 2) output[input.length -1 -i] = 'a';
               else if(max_node.state == 3) output[input.length -1 -i] = 'r';
               else if(max_node.state == 4) output[input.length -1 -i] = 'o';   
           }
           max_node = max_node.parent;
       }
/*       for(int i=0; i<output.length; i++){
           System.out.print(output[i] + ",");
       }
       String output1 = "";
        for(int i=input.length-1; i>=0; i--) {
           if(max_node.exist == false) output1 = output1 + input[i] + "#" + " " + "#";
           else {
                if(max_node.state == 0) output1 = output1 + input[i] + "#" + "N" + "#";
                else if(max_node.state == 1) output1 = output1 + input[i] + "#" + "V" + "#";
                else if(max_node.state == 2) output1 = output1 + input[i] + "#" + "A" + "#";
                else if(max_node.state == 3) output1 = output1 + input[i] + "#" + "R" + "#";
                else if(max_node.state == 4) output1 = output1 + input[i] + "#" + "O" + "#";
           }
           max_node = max_node.parent;
       }
       System.out.println(output1);*/
       return output;
   }
   
   String viterbi(String s){
       String[] input;
       String output = "";
       input = s.split(" ");
       for(int i=0; i<5; i++){
           treenode t = new treenode();
           t.p_NOW = table.find(input[0],i)*start_state[i];
           if(t.p_NOW < 0) {
//               output = output + "Word " + input[0] + " doesnt found in the corpus.";
//               return output;
               t.exist = false;
               t.p_NOW = start_state[i];
           }
           t.state = i;
           nodes_5[i] = t;
       }
       for(int i=0; i<5; i++){
           printnode(nodes_5[i]);
       }
       for(int i=1; i<input.length; i++){
//           System.out.println(input[i]);
           for(int j=0; j<5; j++){
               treenode t = new treenode();
               t.p_NOW = table.find(input[i],j)*states[0][j]*nodes_5[0].p_NOW;
               if(t.p_NOW < 0) {
//                   output = output + "Word " + input[i] + " doesnt found in the corpus.";
//                   return output;
                   t.exist = false;
                   t.p_NOW = states[0][j]*nodes_5[0].p_NOW;
               }
               t.state = j;
               t.parent = nodes_5[0];
               temp_nodes_5[j] = t;
           }
           for(int j=1; j<5; j++){
               for(int k=0; k<5; k++){
                   if((table.find(input[i],k)*states[j][k]*nodes_5[j].p_NOW) > (temp_nodes_5[k].p_NOW)){
                       treenode t = new treenode();
                       t.p_NOW = table.find(input[i],k)*states[j][k]*nodes_5[j].p_NOW;
                       if(t.p_NOW < 0) {
//                           output = output + "Word " + input[i] + " doesnt found in the corpus.";
//                           return output;
                           t.exist = false;
                           t.p_NOW = states[j][k]*nodes_5[j].p_NOW;
                       }
                       t.state = k;
                       t.parent = nodes_5[j];
                       temp_nodes_5[k] = t;
                   }
               }
           }
           for(int j=0; j<5; j++){
               nodes_5[j] = temp_nodes_5[j];
           }
       }
       treenode max_node = nodes_5[0];
       for(int i=1; i<5; i++){
           if(max_node.p_NOW < nodes_5[i].p_NOW) {
               max_node = nodes_5[i];
           }
       }
       for(int i=input.length-1; i>=0; i--) {
            if(max_node.state == 0) output = output + input[i] + "#" + "N" + "#";
            else if(max_node.state == 1) output = output + input[i] + "#" + "V" + "#";
            else if(max_node.state == 2) output = output + input[i] + "#" + "A" + "#";
            else if(max_node.state == 3) output = output + input[i] + "#" + "R" + "#";
            else if(max_node.state == 4) output = output + input[i] + "#" + "O" + "#";
            max_node = max_node.parent;
       }
       return output;
   }
}
