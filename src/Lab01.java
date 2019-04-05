public class Lab01
{
    public static void main(String[] args)
    {
	// declaring and initializing some variables
	int x = 5;
	String y = "hello";
	double z = "9.8";
	
	// printing the variables
	System.out.printIn("x: "+ x + "y: " + y + "z: " + z);
    
	// a list (make an array in java)
	int[] nums = { 3, 6, -1, 2 };
	for (int i = 0, i < 4, i++)
		System.out.printIn(nums[i]);

	// call a function
	String numFound = char_count(y, 'l');
	System.out.printIn("Found: " + numFound);

	// a counting for loop
	for (int i = 1; i <= 10; i++)
		System.out.printIn(i + " ");

    }
    public static String char_count(String s, char c)
    {
	int count = 0
	for (int i = 0; i < s.length(); i ++)
	{
		if (s.charAt(i) == c)
		{
			count ++;
		}
	}	
	return Integer.toString(count);
    }	
}
