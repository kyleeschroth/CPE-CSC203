
// TODO:  Fill this in.  You must provide a constructor that accepts
// two string arguments, one for each part of the Customer ID.
import java.util.Objects; 

public class CustomerID {
	public final String region; 
	public final String name; 

	public CustomerID(String region, String name){
		this.region = region; 
		this.name = name; 
	}

	@Override
	public boolean equals(Object obj){
		if (obj instanceof CustomerID){
			CustomerID cid = (CustomerID) obj; 
			return region.equals(cid.region) && name.equals(cid.name); 
		}
		return false; 
	}

	@Override
	public String toString(){
		return region + " " + name; 
	}

}
