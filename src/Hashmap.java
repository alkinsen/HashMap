import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Hashmap {
	private int arraySize, size; //Size: Size of the array which will hold the entries, Load: number of elements added to the hashmap
	private Entry[] entries; //Entries: Array where all the entries will be held

	public Hashmap(int arraySize){ //constructor with just using size
		this.arraySize = arraySize;
		this.size = 0;
		this.entries = new Entry[this.arraySize];
	}
	
	public int hash(String key){ //hash function -> provided by the question
		int hash = 0;
		int p=23; 
		for (int i = 0; i < key.length(); i++){
			hash = (p * hash + key .charAt(i)) % this.arraySize;
		}
		return hash;

	}
	
	public int arraySize(){ //size() will return the size of the entries array
		return this.arraySize;
	}
	
	public int size(){//load will return how many entries are added to the hashmap
		return this.size;
	}
	
	public void put(String key, String value){ // Put(.. , ..) will add a key-value pair to it's corresponding hash-key.
		remove(key); 							//Tries to removes the key, 
		Entry newEntry = new Entry(key, value);// If it is full, it will link it to the last entry
		Entry current;

		if(entries[hash(newEntry.getKey())]== null){
			entries[hash(newEntry.getKey())] = newEntry;
		}else{
			current = entries[hash(newEntry.getKey())];
			while(current.hasRight()){
				current=current.getRight();
			}
			current.setRight(newEntry);
		}


		size++;
	}
	
	public void remove(String key){ //Removes the Entry with the given key
		int hashindex = hash(key);
		Entry current = entries[hashindex];
		Entry before;
		if(current == null) return;
		if(current.getKey().equals(key)){
			if(current.hasRight()){
				entries[hashindex] = current.getRight();
			}
			entries[hashindex] = null;
			current = null;
			size--;
			return;
		} else while(current.hasRight()){
			before = current;
			current = current.getRight();
			if(current.getKey().equals(key)){
				if(current.hasRight()){
					before.setRight(current.getRight());
					current = null;
					size--;
					return;
					
				}else{
					before.setRight(null);
					current=null;
					size--;
					return;
				}
			}
		}
		
	}
	
	public boolean constainsKey(String key){ //checks if the there is an entry with the given key.
		int hashindex = hash(key);
		Entry current = entries[hashindex];
		while(current != null){
			if(current.getKey().equals(key)) return true;
			current = current.getRight();
		}
		return false;
	}
	
	public IterableHashmap keySet(){ //returns an iterator with next and hasNext methods with the keySet
		IterableHashmap keySet = new IterableHashmap(this);
		for(int i = 0; i < this.arraySize; i++){
			Entry current = this.entries[i];
			if(current != null){
				keySet.add(this.entries[i]);
			}
			
			if(current!= null){
				while(current.hasRight()){
					current = current.getRight();
					keySet.add(current);
					
				}
			}
		}

		return keySet;

	}
	
	public void Empty(){ //Clears the array
		for(int i = 0; i < entries.length; i++){
			entries[i] = null;
		}
		size = 0;
	}
	
	public void printHashmap(){ //Prints hashmap to console
		System.out.println("------------Printing the hashmap-----------");
		for(int i = 0; i < this.arraySize; i++){
			if(this.entries[i] != null){
				System.out.println(i + ": " +this.entries[i].getKey() + " "+ this.entries[i].getValue());

				Entry current = this.entries[i];
				while(current.hasRight()){
					current = current.getRight();
					System.out.println(i+ ": " +current.getKey() + " " + current.getValue());

				}
			}
		}
	}
	
	public String toStringHashmap(){ //Returns the elements of hashmap as a string.
		String result = "";
		for(int i = 0; i < this.arraySize; i++){
			if(this.entries[i] != null){
				result += i + ": " +this.entries[i].getKey() + " "+ this.entries[i].getValue() + "\r\n";

				Entry current = this.entries[i];
				while(current.hasRight()){
					current = current.getRight();
					result += i+ ": " +current.getKey() + " " + current.getValue() + "\r\n";

				}
			}
		}
		return result;
	}
	
	public static void main(String arg[]){ //Main function
		System.out.println("Reading the data from the Input");
		String fileNameInput="input.txt";
		String outputFile = "output.txt";

		try {
			BufferedReader bufferedReaderInput = new BufferedReader(new FileReader(fileNameInput)); 
			BufferedWriter write = new BufferedWriter(new FileWriter(outputFile)); //Clears the text File.
			write.flush();
			write.close();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true)); //True argument gives the append to the FileWriter.

			Hashmap hashmap = new Hashmap(53);

			System.out.println("**************Reading Input*************");
			String line = bufferedReaderInput.readLine();
			while (line != null && !line.isEmpty())   {
				String[] linesplit = line.split("\\s+", 2);
				hashmap.put(linesplit[0], linesplit[1]);

				line = bufferedReaderInput.readLine();
			}  

			writer.write(hashmap.toStringHashmap()); // Writing the inputs to the output file.
			writer.write("\r\n");

			System.out.println("**********Reading Queries***************");
			line = bufferedReaderInput.readLine();
			while ( line != null && !line.isEmpty())   {
				System.out.println("\n\n The query is: " + line  );
				writer.write("The query is: " + line  + "\r\n");
				String[] linesplit = line.split("\\s+", 3);
				//linesplit[0] : query , linesplit[1] : key, linesplit[2] : value
				if(linesplit[0].equalsIgnoreCase("put")){
					hashmap.put(linesplit[1], linesplit[2]);
					hashmap.printHashmap();

					writer.write(hashmap.toStringHashmap() + "\r\n");

				}else if(linesplit[0].equalsIgnoreCase("remove")){
					hashmap.remove(linesplit[1]);
					hashmap.printHashmap();			
					writer.write(hashmap.toStringHashmap() + "\r\n");
					
				}else if(linesplit[0].equalsIgnoreCase("size")){
					System.out.println("Size of the table is: " + hashmap.size());

					writer.write("Size of the table is: " + hashmap.size() + "\r\n\r\n");

				}else if(linesplit[0].equalsIgnoreCase("containsKey")){
					System.out.println(hashmap.constainsKey(linesplit[1]));

					writer.write(hashmap.constainsKey(linesplit[1])+ "\r\n");
				
				}else if(linesplit[0].equalsIgnoreCase("keyset")){
					IterableHashmap iterator = hashmap.keySet();				
					
					while(iterator.hasNext()){
						String key = iterator.nextString();
						System.out.println(key);
						writer.write(key + "\r\n");	
					}
					writer.write("\r\n");
				}
				line = bufferedReaderInput.readLine();
			} 			
			bufferedReaderInput.close();
			writer.flush();
			writer.close();

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}

