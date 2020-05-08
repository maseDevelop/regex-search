//Name: Mason Elliot, Connor Jones


public class REdeque {

    //Restricted Deque Only can be removed from the top 

    //FList class Fields
    private Node head;
    private int count = 0;
    private Node tail;

    //Private Node class
    private class Node{ 
        private int Data;
        Node next;

        //Constructor
        public Node(int newData){
        this.Data = newData;
        }
    }

    //Add
    private void add(int item,Node node){
        node.next = head;
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
            newNode.next = head;
            //Assigning newNode to the head of the linked list
            head = newNode;
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
        tail.next = newNode;
        tail = newNode;
        count++;
        return;
        }
    }

    //Removing from front of deque
    public int removeFront() {
        Node currStackNode = head;
        head = head.next;
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
        Node currNode = head;
        //Goes through the linked list and prints elements
        while(currNode != null)
        {
            System.out.println(currNode.Data);
            currNode = currNode.next;
        }
        System.out.println("________");
    }
    
    public void clear(){
    	head = null;
    	tail = null;
    	count = 0;
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
