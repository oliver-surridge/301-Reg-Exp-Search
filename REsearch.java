/**
 * Regular Expression (RE) string search algorithm
 * Last modified 15/05/2025
 * @author Oliver Surridge - ID: 1607940
 */
import java.io.*;
class Dequeue<T>{
    /**
     * Each node holds a value, along with links to previous and next nodes
     */
    private class Node{
        T dequeueVal;
        Node next;
        Node previous;
        
        Node(T dequeueVal){
            this.dequeueVal = dequeueVal;
        }
    }

    //pointers to each end of the dequeue
    private Node front;
    private Node rear;
    private int size;

    /**
     * Init empty dequeue
     */
    public Dequeue(){
        front = null;
        rear = null;
        size = 0;
    }
 
    /**
     * add to the front of the dequeue
     * @param dequeueVal the val to add to the queue
     */
    public void addToFront(T dequeueVal){
        Node newNode = new Node(dequeueVal);
        //if deque is empty, both front and rear point to new node
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            //link the newNode in front of the current front
            newNode.next = front;
            front.previous = newNode;
            front = newNode;
        }
        size ++;
    }
    /**
     * Add to the rear of the queue
     * @param dequeueVal the val to add to the queue
     */
    public void addToRear(T dequeueVal){
        Node newNode = new Node(dequeueVal);
        //if deque is empty, both front and rear point to new node
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            //link the newNode behind the current rear
            rear.next = newNode;
            newNode.previous = rear;
            rear = newNode;
        }
        size++;
    }

    /**
     * remove the front node
     * @return the val of the deleted node
     */
    public T removeFront(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        T deletedVal = front.dequeueVal;
        if(front == rear){ //if there is only one node in the dequeue
            front = null;
            rear = null;
        }else{
            front = front.next;
            front.previous = null;
        }
        size--;
        return deletedVal;
    }

    /**
     * remove the rear node
     * @return
     */
    public T removeRear(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        T deletedVal = rear.dequeueVal;
        if(rear == front){
            front = null;
            rear = null;
        } else{
            rear = rear.previous;
            rear.next = null;
        }
        size --;
        return deletedVal;
        
    }

    /**
     * get the val of the front node
     * @return the val of the front
     */
    public T getFrontNode(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        return front.dequeueVal;
    }

    /**
     * get the val of the rear node
     * @return the val of the rear node
     */
    public T getRearNode(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        return rear.dequeueVal;
    }
   
    /**
     * check if deque is empty
     * @return if the dequeue is empty
     */
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        return false;
    }

    /**
     * get the size of the dequeue
     * @return size of dequeue
     */
    public int size(){
        return size;
    }

    /**
     * check that the dequeue contains a specified value
     * @param dequeueVal the value we are looking for
     * @return whether the value exists in the queue
     */
    public boolean contains(T dequeueVal){
        Node current = front;
        while (current != null) {
            if (current.dequeueVal == dequeueVal) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * clear the dequeue
     */
    public void clear(){
        front = null;
        rear = null;
        size = 0;
    }
}

class fsmState{
    int stateVal; 
    String type; //literal,BR or WC
    int nextState1;
    int nextState2;

    public fsmState(int stateVal, String type, int nextState1,int nextState2){
        this.stateVal = stateVal;
        this.type = type;
        this.nextState1 = nextState1;
        this.nextState2 = nextState2;
    }
}

 public class REsearch {
     public static void main(String[] args) {
        if (args.length != 1){
            System.err.println("Usage: java REsearch <filename>");
            System.exit(1);
        }
        //read the fsm from std input
        fsmState[] fsm = readFSM();
        //read the txt file
        try(BufferedReader inputReader = new BufferedReader(new FileReader(args[0]))){
            String line;
            while((line = inputReader.readLine()) != null){
                if(searchLine(line, fsm)){
                    System.out.println(line); //print the matching line
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        
        

    } 
       
    /**
    * reads the fsm from standard input
    * @return an array containing the inputted fsm states
    */
    private static fsmState[] readFSM() {
        try {
            BufferedReader fsmReader = new BufferedReader(new InputStreamReader(System.in));
            //use a list to store each line
            java.util.List<String> fsmLines = new java.util.ArrayList<>();
            String line;
            
            //read each line of the input file
            while ((line = fsmReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    fsmLines.add(line);
                }
            }
            
            //find the highest state value used to set the size of the fsm array
            int topState = 0;
            for (String currentLine : fsmLines) { //split to extract the stateVal from each line
                String[] fsmParts = currentLine.split(",");
                if (fsmParts.length != 4) {
                    System.err.println("FSM formatted incorrectly at line: " + currentLine);
                    System.exit(1);
                }
                int stateVal = Integer.parseInt(fsmParts[0].trim());
                topState = Math.max(topState, stateVal); //find the topState
            }
            
            //use an array to hold the fsm states 
            fsmState[] fsm = new fsmState[topState + 1];
            for (String currentLine : fsmLines) { //split to extract each fsm part from each line
                String[] fsmParts = currentLine.split(",");
                int stateVal = Integer.parseInt(fsmParts[0].trim());
                String type = fsmParts[1].trim();//BR or WC
                int nextState1 = Integer.parseInt(fsmParts[2].trim());
                int nextState2 = Integer.parseInt(fsmParts[3].trim());
                fsm[stateVal] = new fsmState(stateVal, type, nextState1, nextState2);
            }
            
            return fsm;
        } catch (Exception e) {
            System.err.println("FSM formatted incorrectly: " + e.getMessage());
            System.exit(1);
        }
        return new fsmState[0];
    }

     /**
      * attempt to match the fsm against each position in the line
      * @param line the line being searched
      * @param fsm 
      * @return if the line satisfies the fsm
      */
     private static boolean searchLine(String line, fsmState[] fsm){
        for(int start = 0; start  < line.length(); start++){
            if (matchFromPosition(line, start, fsm)) { //is there a match
                return true; 
            }
        }
        return false;
     }
     /**
      * try match from a position within a line using the given fsm
      * @param line line to match against
      * @param startthe index in the line to start matching from
      * @param fsm the fsm used to match
      * @return
      */
     private static boolean matchFromPosition(String line, int start, fsmState[] fsm) {
        /**
         * class to hold the current stateVal and position in the line
         */
        class positionOfState {
            int stateVal;
            int position;
    
            positionOfState(int stateVal, int position) {
                this.stateVal = stateVal;
                this.position = position;
            }
        }
    
        Dequeue<positionOfState> queue = new Dequeue<>();
        queue.addToRear(new positionOfState(0, start)); //start from stateVal 0
    
        while (!queue.isEmpty()) {
            positionOfState pOfState = queue.removeFront();
            int currentStateVal = pOfState.stateVal;
            int position = pOfState.position;
            
            //skip invalid state numbers
            if (currentStateVal >= fsm.length || fsm[currentStateVal] == null) {
                continue;
            }
    
            fsmState current = fsm[currentStateVal];//get the state at this state val
    
            if (current.nextState1 == -1 && current.nextState2 == -1) {
                return true;//accepting state reached
            }
        
            //handle BR and WC
            if (current.type.equals("BR")) {
                if (current.nextState1 >= 0) queue.addToRear(new positionOfState(current.nextState1, position));
                if (current.nextState2 >= 0) queue.addToRear(new positionOfState(current.nextState2, position));
            } else if (position < line.length()) {
                char currentChar = line.charAt(position);
                boolean match = false;
    
                if (current.type.equals("WC")) {//wildcard
                    match = true;
                } else if (current.type.length() == 1) {//literal
                    match = (currentChar == current.type.charAt(0));
                }
    
                if (match) {//if a match, move to next states and increment position in the dequeue
                    if (current.nextState1 >= 0) queue.addToRear(new positionOfState(current.nextState1, position + 1));
                    if (current.nextState2 >= 0) queue.addToRear(new positionOfState(current.nextState2, position + 1));
                }
            }
        }
        return false;//return false on empty queue and no state found
    }
 }
