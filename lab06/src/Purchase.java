import java.security.PublicKey; 

public class Purchase{
	public int amount; 
	public Product product; 

	public Purchase(Product p, int amount){
		this.amount = amount; 
		this.product = p; 
	}
}