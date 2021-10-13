
 
 /*  Linear Probing Hash Table */ 
class Hashtable
{
    private int currentSize, maxSize;       
  
   public Pair[] map = null;
 
    // Constructor 
    public Hashtable(int capacity) 
    {
        currentSize = 0;
        maxSize = capacity;
    	map = new Pair[maxSize];
		for(int i =0; i < maxSize ; i++){
			map[i]=null;
		}
    }  
 
 
    // Function to check if hash table is empty 
    public boolean isEmpty() 
    {
        return getSize() == 0;
    }
    
    // Function to get size of hash table 
    public int getSize() 
    {
        return currentSize;
    }
 

    // Function to check if hash table is full 
    public boolean isFull() 
    {
        return currentSize == maxSize;
    }
 

 
    // Function to check if hash table contains a key
    public boolean contains(String key) 
    {
        return get(key) !=  null;
    }
 
    // Function to get hash-code/hash-value for a given key 
    public int hash(String key) 
    {

        return Math.abs(key.hashCode()) % maxSize;
    }    
 
    // Function to insert key-value pair 
    public void put(String key, String val)  
    {                
		// TODO(1)
		// Remember to call rehash() when the table is full
    	
    	int hash = hash(key);
    	map[hash] = new Pair(key, val);
    	
////    	for(int i=0; i<getSize(); i++){
////    		
////    		System.out.println("i: "+i);
////    		if(map[i]!= null) System.out.println("k: "+map[i].getKey()+" v: "+map[i].getValue());
////    	}
//    	System.out.println(getSize()+", "+isFull());
    	
    	if(isFull()) rehash();
    	currentSize++;
		
		
    }
    /// Function to rehash when the table is full
    public void rehash()  
    {   
		// TODO(2)
		//Hint: 1) make a copy of the old array, 
		//      2) create a new table of Size=2*old_table_Size and 
	    //	3) hash all the old table elements into new table.
    	maxSize*=2;
    	Pair[] newMap = new Pair[maxSize];
    	int newHash;
    	for(int i=0; i<getSize(); i++){
    		if(map[i] != null){
    			newHash = hash(map[i].getKey());
    			newMap[newHash] = map[i];
    		}
    	}
    	
    	map = newMap;
    	
		
    }
    
    // Function to get value for a given key 
    public String get(String key) 
    {
      int i = hash(key);
        while (map[i] != null)
        {
            if (map[i].getKey()==key)
                return map[i].getValue();
            i = (i + 1) % maxSize;
        }            
        return null;
        
		
    }
 
     
 
    // Function to print HashTable 
    public void printHashTable()
    {
        System.out.println("\nHash Table: Key, Value ");
        for (int i = 0; i < maxSize; i++)
            if (map[i] != null)
            	System.out.println(map[i].getKey()+", "+map[i].getValue());
        System.out.println();
    }   
}

class Pair{

	private String key;
	private String value;

	public Pair(String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey(){
		return key;
	}

	public String getValue(){
		return value;
	}

}


