
import java.util.List;
import javax.net.ssl.SSLSession; 
import java.util.ArrayList; 
import java.util.Comparator;

// TODO:  Fill this in

public class Customer {

	public final CustomerID id;
	private final List<Session> sessions;

	public Customer(CustomerID id){
		this.id = id; 
		sessions = new ArrayList<>();
	}

    public List<Session> getSessions() {
        //throw new RuntimeException("TODO:  Fill this in");
        return this.sessions; 
    }

    public void addSession(Session session) {
        //throw new RuntimeException("TODO:  Fill this in");
        if (!sessions.contains(session)){
        	sessions.add(session); 
        }
    }

    public boolean madePurchase(){
    	for (Session s : sessions){
    		if (s.purchases.size()>0){
    			return true;
    		}
    	}
    	return false; 
    }
    public List<Product> getPurchases(){
    	List<Product> p = new ArrayList<>(); 

    	for (Session s : sessions){
    		for (Purchase pc : s.purchases){
    			p.add(pc.product); 
    		}
    	}
    	return p; 
    }

    public int getViewsOfProduct(Product p){
    	int total = 0; 
    	for (Session s : sessions){
    		for (View v : s.views){
    			if (v.product.equals(p)){
    				total ++; 
    				break;
    			}
    		}
    	}
    	return total; 
    }

    @Override
    public String toString(){
    	return id.toString(); 
    }
}
