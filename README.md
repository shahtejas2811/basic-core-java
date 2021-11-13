# basic-core-java
## 1. Collection framework  
###	a. HashMap Internal
	- default size of an array of Entry Object is 16
	- Whenever the size of the hashMap reaches to 75% (default load factor) of its current size, i.e, 12, 
	it will double its size by recomputing the Hashcode of existing data structure elements.
	- To avoid rehashing of the data structure as elements grow it is the best practice to explicitly give the size of the hashmap while creating it.	
	- Internal working of hashMap put method
		* re-generates the hashcode using hash(int h) method by passing user defined hashcode as an argument
		* call indexFor method to generate index based on the re-generated hashcode and length of the data structure.
			static int indexFor(int h, int length) {
				return h & (length-1);
			}
		* if key exists, it over-rides the element, else it will create a new entry in the hashMap at the index generated
	- Internal working of hashmap get method
		* re-generates the hashcode using hash(int h) method by passing user defined hashcode as an argument
		* call indexFor method to generate index based on the re-generated hashcode and length of the data structure.
		* point to the right bucket, i.e, table[i], and traverse through the linked list, which is constructed based on Entry inner class
		* when keys are equal and their hashcodes are equal then return the value mapped to that key
		* in case of collision using hashcode we go to the right bucket and using equals we find the right element in the bucket and then return it.	
	- What happens when two different keys have same hashcode?
		* if the keys are equal, then over-ride the previous key-value pair with the current key-value pair.
		* if keys are not equal, then store the key-value pair in the same bucket as that of the existing keys. (collision happens)
	-  What is race condition with respect to hashmaps? 
		* When two or more threads see the need for resizing of HashMap
		* They might end up adding the elements of old bucket to the new bucket simultaneously and hence might lead to infinite loops.
		* In case of collision, i.e, when there are different keys with same hashcode, internally we use single linked list to store the elements.
		* We add new element at head of the linked list  
		* Hence at the time of resizing entire sequence of objects in linked list gets reversed during which there are chances of infinite loops.
	- What things needs to take care if employee object used as key 
		* Object must be immutable
			^ The containsKey uses hashcode,hash and indexFor function to find the bucket number where it should search for the object.
			^ The containsKey get a different hashcode,hash and indexFor values as the state of the object is changed.
			^ It will search in a different bucket this time. 
			^ So when we inserted the object the indexFor calculation was based on one state and after we change the state of the object, 
			^ the hashcode changes and thus might result in searching in a different bucket and result false. 
		
		* Override hashCode & equals method : 
			 ref : https://www.geeksforgeeks.org/override-equalsobject-hashcode-method/
	
### b. HashSet Internal
			
	- HashSet use HashMap internally to store data
	- there is drawback in terms of memory management as it stores unnecessary dummy object as value of Hashmap for every element
	- to avoid this problem we can use unifiedSet from Eclipse Collections Library, it is internally use flattened array data structure 
		 
### c. ConcurrentHashMap Internal
		
	- HashMap vs Collections.SynchronizedMap vs HashTable vs ConcurrentHashMap 
		* HashMap:
			^ HashMap allows one null key and any number of null values.
			^ Stores key-value pairs,and is best suitable in non-threaded applications
			^ not synchronized and hence not thread-safe.
		* Collections.SynchronizedMap:
			^ It returns an instance of SynchronizedMap which is an inner class to the Collections class. 
			^ This inner class has all its methods in a synchronized block with a mutex.
			^ The inner class SynchronizedMap has two constructors,
					
					private final Map<K, V> m;
        			final Object mutex;
			        SynchronizedMap(Map<K, V> map) {
			            m = map;
			            mutex = this;
			        }
			        SynchronizedMap(Map<K, V> map, Object mutex) {
			            m = map;
			            this.mutex = mutex;
			        }      
			        @Override 
			        public V get(Object key) {
			            synchronized (mutex) {
			                return m.get(key);
			            }
			        }
			
			 ^ 1st constructors which takes only Map as an argument.By default,if one uses the first constructor of passing only a Map
			 ^ this is used as a mutex. these is same as HashTable
			 ^ 2nd constructors which takes two argument, one is Map and second is object of the mutex by which the lock on the Map methods
			 ^ Map mehod would be lock only on that Object and hence less restrictive than HashTable.
		* HashTable:
			 ^ HashTable uses method level synchronization 
			 ^ Collections.SynchronizedMap(Map) provides flexibility to developer lock on provided mutex with Synchronized block.
			 ^ Does not allow Null Values, is the part of legacy Dictionary class
		* ConcurrentHashMap:
		
		- Internal Working of CHM
		- Is ConCurrentHashmap fully Thread-safe ? 
		- Concurrent hashmap and how it will work when reader and writer both try to access the same chunk at the same time.
		Reader is going to read wrong value. What would you do to resolve this
		what data structure has been used internally in ConcurrentHashmap
		- Limitation of using ConcurrentHashmap
		I said .. Reader thread doesnt lock CHM and at the same time when writer threads write anything there is probability of dirty read by reader thread in CHM
		- In case of that we can use Volatile keyword which write directly in Memory