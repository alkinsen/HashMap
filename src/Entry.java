
public class Entry {
	private String key, value;
	private Entry right;
	
	public Entry(String key, String value){
		this.key = key;
		this.value = value;
		this.right = null;
		
	}
	public boolean hasRight(){
		return this.right != null;
	}
	public Entry getRight() {
		return right;
	}
	public void setRight(Entry right) {
		this.right = right;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
