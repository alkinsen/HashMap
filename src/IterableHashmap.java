public class IterableHashmap {
	private int indexAdd, indexRemove = 0;
	private Entry[] values;
	
	public IterableHashmap(Hashmap h){
		this.values = new Entry[h.arraySize()];
		this.indexAdd = 0;
	}
	public void add(Entry e){
		values[indexAdd]=e;
		indexAdd++;
	}
	public boolean hasNext(){
		return values[indexRemove] != null;
	}
	public String nextString(){
		indexRemove++;
		return values[indexRemove-1].getKey();
	}	
	public Entry nextEntry(){
		indexRemove++;
		return values[indexRemove-1];
	}
}
