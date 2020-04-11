
public class REdeque {

    //FList class Fields
    private Node head;
    private int count = 0;
    private Node tail;

    //Private Node class
    private class Node{ 
        private int Data;
        Node next;
        Node prev;
     

        //Constructor
        public Node(int newData){
        this.Data = newData;
        }
    }

    //Add
    public void add(int item,Node node){
        //node.next = head;
        //node.prev = tail;
        head = node;
        tail = node;
        count++;
        return;
    }

    //Add to front of deque
    public void addFront(int item){
        Node newNode = new Node(item);
        if(count == 0){
            add(item,newNode);
            return;
        }
        else{
            //Creating new Node object
            newNode.prev = head;
            head.next = newNode;
            //oldHead.prev = newNode;
            //Assigning newNode to the head of the linked list
            head = newNode;
            //Increasing the count
            count++;
            return;
        }
       
    }

    //Add to rear of deque
    public void addRear(int item){
        Node newNode = new Node(item);
        if(count == 0){
            add(item,newNode);
            return;
        }
        else{
        tail.prev = newNode;
        newNode.next = tail;
        tail = newNode;
        count++;
        return;
        }
    
    }

    //Removing from front of deque
    public int removeFront() {
        Node currStackNode = head;
        head = head.prev;
        head.next = null;
         count--;
        return currStackNode.Data;
    }

    //Removing from back of deque
    public int removeRear(){
        Node currStackNode = tail;
        tail = tail.next;
        count--;
        return currStackNode.Data;
    }

    public boolean isEmpty() { 
        // Return true if stack is empty, false otherwise
        if(head == null){
            return true;
        }
        else{
            return false;
        }
    }

    
    //Testing Method

    public void printDeque(){
        Node currNode = tail;
        //Goes through the linked list and prints elements
        while(currNode != null)
        {
            System.out.print(currNode.Data + " ");
            currNode = currNode.next;
        }

    }

    public int getHead(){
        return head.Data;
    }

    public int getRear(){
        return tail.Data;
    }

    public int size(){
        return count;
    }
}
