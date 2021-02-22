package testpack;

import java.util.Arrays;
import java.util.List;

public final class ResizableArrayBag<T> implements BagInterface<T>
{
	private T[] bag;
	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 15;
	private boolean integrityOK = true;
	private static final int MAX_CAPACITY = 10000;


	/** Creates an empty bag whose capacity is 25. */
	public ResizableArrayBag() 
	{
		this(DEFAULT_CAPACITY);
	} // end default constructor

	/** Creates an empty bag having a given capacity.
       @param desiredCapacity  The integer capacity desired. */
	public ResizableArrayBag(int desiredCapacity)
	{
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] tempBag = (T[])new Object[desiredCapacity]; // Unchecked cast
      bag = tempBag;
      numberOfEntries = 0;
	} // end constructor
	
	  // Throws an exception if this object is not initialized.
	  private void checkIntegrity()
	  {
	     if (!integrityOK)
	        throw new SecurityException("ArrayBag object is corrupt.");
	  } // end checkIntegrity


	/** Adds a new entry to this bag.
       @param newEntry  The object to be added as a new entry.
       @return  True if the addition is successful, or false if not. */
	public boolean add(T newEntry)
	{
      boolean result = true;
      if (isArrayFull())
      {
         result = false;
         throw new IllegalStateException("no");
      }
      else
      {  // Assertion: result is true here
         bag[numberOfEntries] = newEntry;
         numberOfEntries++;
      } // end if
      
      return result;
	} // end add
   
	
	/** Retrieves all entries that are in this bag.
       @return  A newly allocated array of all the entries in this bag. */
	public T[] toArray()  
	{
      // The cast is safe because the new array contains null entries.
      @SuppressWarnings("unchecked")
      T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast
      
      for (int index = 0; index < numberOfEntries; index++)
      {
         result[index] = bag[index];
      } // end for
      
      return result;
      // Note: The body of this method could consist of one return statement,
      // if you call Arrays.copyOf
	} // end toArray
	
   // Returns true if the array bag is full, or false if not.
	public boolean isArrayFull()
	{
		return numberOfEntries >= bag.length;
	} // end isArrayFull
   
	
	/** Sees whether this bag is empty.
	    @return  True if this bag is empty, or false if not */
	public boolean isEmpty()
	{
		return numberOfEntries == 0;
	} // end isEmpty

	/** Gets the number of entries currently in this bag.
	    @return  The integer number of entries currently in this bag */
	public int getCurrentSize()
	{
		return numberOfEntries;
	} // end getCurrentSize


	/** Removes one unspecified entry from this bag, if possible.
       @return  Either the removed entry, if the removal
                was successful, or null */
	public T remove()
	{
		checkIntegrity();
		T result = removeEntry(numberOfEntries - 1);		
		return result;
	} // end remove

   
	/** Removes one occurrence of a given entry from this bag.
       @param anEntry  The entry to be removed
       @return  True if the removal was successful, or false otherwise */
	public boolean remove(T anEntry)
	{
	  checkIntegrity();
      int index = getIndexOf(anEntry);
      T result = removeEntry(index);         
      return anEntry.equals(result);
	} // end remove   
	
	private T removeEntry(int givenIndex)
	{
			T result = null;
	      
			if (!isEmpty() && (givenIndex >= 0))
			{
	         result = bag[givenIndex];                   // Entry to remove
	         bag[givenIndex] = bag[numberOfEntries - 1]; // Replace entry with last entry
	         bag[numberOfEntries - 1] = null;            // Remove last entry
	         numberOfEntries--;
			} // end if
	      
	      return result;
	}

	/** Removes all entries from this bag. */
	public void clear()
   {
      // STUB
   } // end clear
	
	/** Counts the number of times a given entry appears in this bag.
		 @param anEntry  The entry to be counted
		 @return  The number of times anEntry appears in the bag */
	public int getFrequencyOf(T anEntry)
	{
	 checkIntegrity();
      int counter = 0;
      
      for (int index = 0; index < numberOfEntries; index++)
      {
         if (anEntry.equals(bag[index]))
         {
            	counter++;
         } // end if
     			 } // end for
      return counter;
	} // end getFrequencyOf

	
	/** Tests whether this bag contains a given entry.
		 @param anEntry  The entry to locate
		 @return  True if this bag contains anEntry, or false otherwise */
	public boolean contains(T anEntry)
	{
	 checkIntegrity();
	 return getIndexOf(anEntry) > -1; // or >= 0
	} // end contains

	
	private int getIndexOf(T anEntry)
		{
			int where = -1;
			boolean found = false;
			int index = 0;

				while (!found && (index < numberOfEntries))
			{
					if (anEntry.equals(bag[index]))
				{
						found = true;
						where = index;
				} // end if
				index++;
	 } // end while

	// Assertion: If where > -1, anEntry is in the array bag, and it
	// equals bag[where]; otherwise, anEntry is not in the array
	      
	return where;
	} // end getIndexOf

	/** Combines two bags into one, accepting repeats
	 * @param an object that uses BagInterface<T>
	 * @return a new bag that follows the specified rules */
	@Override
	public BagInterface<T> union(BagInterface<T> item) {
		int bigBag = this.getCurrentSize() + item.getCurrentSize();
        BagInterface<T> everything = new ResizableArrayBag<T>(bigBag);
        for (int index = 0; index < item.getCurrentSize(); index++) {
        	everything.add(item.toArray()[index]);
        }
        for (int index = 0; index < this.getCurrentSize(); index++) {
        	everything.add(this.toArray()[index]);
        }
        return everything;
}

	/** Finds the common terms between two bags and creates a new bag that holds them
	 * @param an object that uses BagInterface<T>
	 * @return a new bag that follows the specified rules */
	@Override
	public BagInterface<T> intersection(BagInterface<T> item) {
		int thisSize = this.getCurrentSize();
		int itemSize = item.getCurrentSize();
		int bigBag = thisSize + itemSize;
		BagInterface<T> everything = new ResizableArrayBag<T>(bigBag);
        BagInterface<T> temp = new ResizableArrayBag<T>(bigBag);
        for (int index = 0; index < itemSize; index++) {
        		if (this.getIndexOf(item.toArray()[index]) > -1) {
        			everything.add(item.toArray()[index]);
        		}
        }
		return everything;
	}

	/** Finds the common terms between two bags and creates a new bag that does not hold them
	 * @param an object that uses BagInterface<T>
	 * @return a new bag that follows the specified rules */
	@Override
	public BagInterface<T> difference(BagInterface<T> item) {
		int bigBag = this.getCurrentSize() + item.getCurrentSize();
        BagInterface<T> everything = new ResizableArrayBag<T>(bigBag);
        BagInterface<T> temp = new ResizableArrayBag<T>(bigBag);
        for (int index = 0; index < item.getCurrentSize(); index++) {
        	everything.add(item.toArray()[index]);
        }
        for (int index = 0; index < this.getCurrentSize(); index++) {
        	everything.add(this.toArray()[index]);
        }
        for (int index = 0; index < item.getCurrentSize(); index++) {
    		if (this.getIndexOf(item.toArray()[index]) > -1) {
    			everything.remove(item.toArray()[index]);
    			everything.remove(item.toArray()[index]);
    		}
    }
		return everything;
	}
}
