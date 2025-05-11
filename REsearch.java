class Dequeue{
    //Each node holds a value, along with links to previous and next nodes
    private class Node{
        int stateVal;
        Node next;
        Node previous;
        
        Node(int stateVal){
            this.stateVal = stateVal;
        }
    }

    //pointers to each end of the dequeue
    private Node front;
    private Node rear;
    private int size;

    //Init empty dequeue
    public Dequeue(){
        front = null;
        rear = null;
        size = 0;
    }
 
    //add to the front of the dequeue
    public void addToFront(int stateVal){
        Node newNode = new Node(stateVal);
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

    public void addToRear(int stateVal){
        Node newNode = new Node(stateVal);
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
    }

    //remove the front node
    public int removeFront(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        int deletedVal = front.stateVal;
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

    //remove the rear node
    public int removeRear(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        int deletedVal = rear.stateVal;
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

    //get the state val of the front node
    public int getFrontNode(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        return front.stateVal;
    }

    //get the state val of the rear node
    public int getRearNode(){
        if(isEmpty()){
            System.err.println("Dequeue is empty");
        }
        return rear.stateVal;
    }
   
    //check if deque is empty
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }
        return false;
    }

    //get the size of the dequeue
    public int size(){
        return size;
    }

    //check that the dequeue contains a specified value
    public boolean contains(int stateVal){
        Node current = front;
        while (current != null) {
            if (current.stateVal == stateVal) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    //clear the dequeue
    public void clear(){
        front = null;
        rear = null;
        size = 0;
    }
 }
 
 public class REsearch {
     public static void main(String[] args) {
         
     }
 }