// Assignment Name: Programming Assignment 1 -- List ADT
// File: List.java 
// Description: 
//-----------------------------------------------------------------------------
// List.java is an implementation of the  List ADT, whose set of mathematical 
// structures is any sequence of finite integers such that at most one element 
// is "underscored", i.e. is pointed to by the cursor (iterator). 
//-----------------------------------------------------------------------------

public class List{

	private class Node{

		// field 
		int data;

		// to reference next
		// and previous node 
		Node next; 
		Node prev;

		// Constructor for the Node class
		Node(int data){

			this.data = data;
			this.next = this.prev = null; 
		}


		// determines whether data of two Nodes
		// are the same integer
		public boolean equals(Object o){

			if (o instanceof Node){

				Node temp = (Node)o; 
				return temp.data == this.data; 
			}
			else
				return false; 
		}

		// Overloaded toString method 
		public String toString(){

			return String.valueOf(this.data);
		}


	}

	// members of the List class

	int len; // length of List
	int index; 
	Node front; // references front of list
	Node back; // references back of list
	Node iterator; // cursor 


	// constructor for List 
	List(){

		len = 0;
		index = -1;  
		front = back = iterator = null; 
	}


	// Access Functions

	// Returns the number of elements in this List.
	// Post: Integer is returned representing length of 
	// the list
	int length(){

		return len; 
	}

	// If cursor is defined, returns the index of the 
	// cursor element, otherwise returns -1 
	// Pre: iterator is referencing List element
	// Post: Returns data contained in element, otherwise
	// returns -1
	int index(){
		
		return index; 
	}

	// Returns front element. 
	// Pre: len > 0
	int front(){

		if (this.length() > 0)
			return front.data; 
		else throw new RuntimeException("Error, List is empty."); 

	}

	// Returns back element. 
	// Pre: len > 0
	int back(){

		if (this.length() > 0)
			return back.data; 
		else throw new RuntimeException("Error, List is empty."); 

	}

	// Returns cursor element.
	// Pre: len > 0, index() >= 0
	int get(){

		if (this.length() > 0 && this.index() >= 0)
			return iterator.data; 
		else throw new RuntimeException("Error, cursor off end."); 
	}

	// Returns true iff this List and L are the  same 
	// integer sequence. The cursors of either List are 
	// not used in the comparison of the respective
	// Lists. 
	boolean equals(List L){

		if (L.length() != this.length())
			return false; 

		// iterator for each list starting at front
		Node thisIterator = this.front;
		Node thatIterator = L.front;  

		// while loop to iterate through n elements of 
		// each list and compare the respective elements
		while (thisIterator != null && thatIterator != null) {

			if (!thisIterator.equals(thatIterator))
				return false;

			// update each iterator and dec index
			thisIterator = thisIterator.next; 
			thatIterator = thatIterator.next;   
		}
		return true; 
	}


	// Manipulation Procedures 

	// Resets this List to its original empty state. 
	void clear(){

		// setting all fields to original 
		// intialized state
		len = 0; 
		index = -1; 
		front = back = iterator = null; 
	}

	// If List is non-empty, places the cursor under the 
	// front element, else does nothing. 
	void moveFront(){

		if (this.length() > 0) {
			iterator = front;
			index = 0; 	
		}
	}

	// If List is non-empty, places the cursor under the back
	// element, otherwise does nothing. 
	void moveBack(){

		if (this.length() > 0) {
			iterator = back; 
			index = this.length() - 1; 
		}
	}

	// If cursor is defined and not at front, moves cursor one 
	// step toward front of this List, if cursor is defined and 
	// at front, it becomes undefined, else cursor does nothing. 
	void movePrev(){

		// if iterator is defined and not at the back
		// move it back one element and dec index
		if (iterator != null && !iterator.equals(front)){
			iterator = iterator.prev;
			index--;  
		} // else set it to undefined if front
		else if (iterator == front) {
			iterator = null; 
			index = -1; 
		}
	}

	// If cursor is defined and not at back, moves cursor one step
	// toward back of this List, if cursor is defined and at back, 
	// cursor becomes undefined, if cursor is undefined does nothing. 
	void moveNext(){

		// if iterator is defined and not at the front
		// move it forward one element and inc index
		if (iterator != null && !iterator.equals(back)){

			iterator = iterator.next; 
			index++; 
		} // else set it to undefined if back
		else if (iterator == back) {
			iterator = null; 
			index = -1; 
		}

	}

	// Insert new element into this List. If List is non-empty, 
	// insertion takes place before front element. 
	// f -> f' 
	void prepend(int data){

		Node temp = new Node(data); 	

		// if length is zero, set front and back 
		// to new node
		if (this.length() == 0) 
			front = back = temp; 
		else {
			// other wise make it new front
			temp.next = front; 
			front.prev = temp; 
			front = temp; 

			// update index if cursor is defined
			if (index != -1)
				index++; 
		}
		len++; 
	}

	// Insert new element into this List. If List is non-empty, 
	// insertion takes place after back element. 
	// b' -> b
	void append(int data){

		Node temp = new Node(data); 	

		// if length is zero, set front and back 
		// to new node		
		if (this.length() == 0)
			front = back = temp; 
		else {
			// other wise make it new back
			back.next = temp; 
			temp.prev = back; 
			back = temp;
		}
		len++; 
	}


	// Insert new element before cursor. 
	// Pre: len > 0, index() >= 0
	void insertBefore(int data){

		// if there is only one element, call prepend
		if (this.index() == 0) 
			this.prepend(data); 
		// else if the length and index is greater than zero, 
		// insert element before cursor
		else if (this.length() > 0 && this.index() > 0){

			Node temp = new Node(data); 

			temp.prev = iterator.prev; 
			iterator.prev.next = temp; 
			temp.next = iterator; 
			iterator.prev = temp; 

			// update index and len
			index++; 
			len++; 
		}
		// else its assumed iterator is off end 
		else 
			throw new RuntimeException("Error, List is empty" +
				" or cursor is off end."); 
		
	}

	// Inserts new element after cursor. 
	// Pre: len > 0, index() >= 0
	void insertAfter(int data){

		// if iterator is at end of the list, and it is defined
		// call append
		if (index == len - 1 && index != -1) 
			this.append(data);
		// else, if the length is greater than 0 and index > 0, 
		// insert a new node after iterator/cursor 
		else if (this.length() > 0  && this.index() >= 0) {

			Node temp = new Node(data); 
										// failed here 3 times

			temp.next = iterator.next; 
			iterator.next.prev = temp; 
			temp.prev = iterator; 
			iterator.next = temp;

			len++; 

		} else  // else cursor is undefined, so throw exception    
		throw new RuntimeException("Error, List is empty" +
			" or cursor is off end."); 
	}

	// Deletes the front element
	// Pre: len > 0 
	void deleteFront(){

		if (this.length() > 0) {

			front = front.next; 
			front.prev = null; 
			len--;

			// update index if iterator
			// not off end
			if (index != -1)
				index--;  
		} else
		throw new RuntimeException("Error, List is empty.");

	}

	// Deletes the back element
	// Pre: len > 0
	void deleteBack(){

		if (this.length() > 0){ // failed here

			// if the index is at the end of the list and 
			// not off end, dec index and make it off end
			if (index == this.length() - 1) { 
				iterator = null; 
				index = -1; 
			}

			if (len == 1){

				front = back = null; 
				len = 0; 
			}
			else {

				back = back.prev; 
				back.next = null; 
				len--; 
			}
			

		} else 
		throw new RuntimeException("Error, List is empty."); 

	}

	// Deletes cursor element, making cursor undefined. 
	// Pre: length()>0, index()>=0
	void delete(){

		// if there is only one element in list, delete it
		// and make iterator undefined
		// this is also the case where index == 0
		if (this.length() == 1 || index == 0) {
			this.deleteFront(); 
			iterator = null;
			index = -1;  
		}
		// if iterator is at the end of the list and not undefined,
		//  delete it and make iterator undefined 
		else if (index == len - 1 && index != -1 ) {
			this.deleteBack(); 
			iterator = null; 
			index = -1; 
		}
		// else if the length is > 0 and index is not undefined
		else if (this.length() > 0 && this.index() > 0) {


			// make prev element and next elment point to eachother
			iterator.prev.next = iterator.next; 
			iterator.next.prev = iterator.prev;

			// make element iterator is referencing detached from
			// the rest of the list
			iterator.next = iterator.prev = null;  
			iterator = null;
			index = -1; // forgot to add this 
			len--;  

		} else 
		throw new RuntimeException("Error, List is empty" +  
			" or cursor is undefined."); 

	}

	// Other methods 

	// Overrides Object's toString method. Returns
	// a String representation of this List consisting
	// of a space separated sequence of integers, with 
	// front on left. 
	public String toString(){

		StringBuilder output = new StringBuilder(); 

		if (this.length() == 0)
			return ""; 

		Node temp = front; 

		while(temp.next != null){ 

			// append it to output string
			output.append(temp + " ");
			temp = temp.next;  
		}

		// to get rid of space at end
		output.append(temp);

		// make last character a ""

		// convert StringBuffer to String 
		return output.toString(); 

	}

	// Returns a new List representing the same integer 
	// sequence as this List. The cursor in the new list 
	// is undefined, regardless of the state of the cursor 
	// in this List. This List is unchanged. 
	List copy(){

		List copyList = new List();  

		Node temp = this.front; 

		while (temp != null) {

			copyList.append(temp.data); 
			temp = temp.next; 
		}
		return copyList; 
	}

	// Returns a new List which is the concatenation of
	// this list followed by L. The cursor in the new List
	// is undefined, regardless of the states of the cursors 
	// in this List and L. The states of this List and L are 
	// unchanged.
	List concat(List L) {

		// make copies of both this List and parameter List  
		List firstHalf = this.copy();
		List secondHalf = L.copy(); 

		// make last node of firstHalf point to 
		// first node of secondHalf and vice versa
		firstHalf.back.next = secondHalf.front; 
		secondHalf.front.prev = firstHalf.back; 

		// firstHalf will be concatenation of firstHalf 
		// and secondHalf
		return firstHalf; 
	}


}


