import java.util.Scanner;

public class CDL_Kata {

	private static double total = 0; // Creating a global variable to make it easier to calculate a running total
	private static Integer x = null; // As can't set int to be null
	private static int[] count = { 0, 0, 0, 0 }; // Creating the counter variables for each of the items (for special price)
	private static int[] quantity = { 3, 2, 0, 0 }; // Default values provided by spec - Can be overwritten
	private static double[] specialPrice = { 1.30, 0.45, 0.00, 0.00 }; // Same as above
	private static double[] price = { 0.50, 0.30, 0.20, 0.15 }; // Same as above
	private static char[] products = { 'A', 'B', 'C', 'D' }; // The default items we have available from the spec
	private static Scanner scan = new Scanner(System.in); // Initialise a scanner to be used throughout the program

	public char[] findItems(String input) { // This method is to convert the String input we get from the user into a char array instead (and make it upper case)		
		if (input.isBlank()) {
			input = scan.nextLine();
		}
		
		input = input.replaceAll("( ,)|(,,)|(,)", "").trim(); // Avoid from counting commas as a char
		input = input.replaceAll(" ", "").trim(); // Remove whitespace too
		
		return input.toUpperCase().toCharArray();
	}

	public String getTotal() { // Format the 'total' figure to allow it to have the extra '0' on the end when it outputs the variable
		return String.format("%.2f%n", total);
	}

	public void getCurrentTotal(char item) { // This method is to avoid repeating code - To output what the current total
		System.out.println("Item: '" + item + "' has been scanned. Your current bill is now: £" + getTotal());
	}
	
	public void throwErrorToUserForMissingItem(char item) { // This method is to avoid repeating code - To output a simple error message when a missing item is input
		System.out.println("Sorry, there is no price for item: '" + item + "'\n");
	}
	
	public boolean yesOrNo(String input) { // Method to read yes/no input from the user
		if (input.isBlank()) {
			input = scan.nextLine();
		}
		
		while (!input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("NO") && !input.equalsIgnoreCase("Y")
				&& !input.equalsIgnoreCase("Yes")) { // Logic to cater for any other values being input by the user

			System.out.println("Please either input: 'Yes / Y' or 'No / N'.");
			input = scan.nextLine();
		}
		
		if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
			return true;
		}
		
		return false;
	}
	
	public void assignX(char item) { // Assign the 'x' value to allow the global arrays to be in sync with the corresponding items
		switch (item) {
		case 'A': {
			x = 0;
			break;
		}
		case 'B': { 
			x = 1;
			break;
		}
		
		case 'C': {
			x = 2;
			break;
		}

		case 'D': {
			x = 3;
			break;
		}
		
		default: { // IF item doesn't match the criteria above then default 'x' back to be null (can't be 0 as that is same as 'A')
			x = null;
			break;
		}
		
		}
		
		if (x != null) { // Avoid the count being incremented when item doesn't match any of above criteria
			count[x]++; // Count = count + 1
		}

	}

	public void getSpecialPrice(char item) { // Method to calculate special price on the item scanned
		total -= ((price[x] * count[x]) - specialPrice[x]); // Take off the discount of the items for them being part of the 'special price'
		System.out.println(quantity[x] + " of item '" + item + "' has been scanned, this gives you a special price of: £" + String.format("%.2f%n", specialPrice[x])); // Output to the user the special price and the reason for it
		System.out.println("Your current bill is now: £" + getTotal());
	}

	public void checkout(char[] items) { // Checkout items that have been scanned and show the pricing per item
		for (int i = 0; i < items.length; i++) { // FOR loop to iterate through entire char array
			assignX(items[i]);
			
			if (x == null || items[i] != products[x]) { // Cater for any other value being input and output an error message if so
				throwErrorToUserForMissingItem(items[i]);  // Throw a simple error message to user
				continue; // Continue with FOR loop (so it doesn't continue and output a price when we haven't added anything to it)
			}
			
			else {
			total += price[x]; // Total = total + price (of whatever item has been input)
			getCurrentTotal(items[i]); // Output current total to the user
			
				if (count[x] == quantity[x]) { // IF count of item = quantity of item eligible for special pricing then...
					getSpecialPrice(items[i]); // Calculate special price of item
					count[x] = 0; // Reset count variable
					continue;
				}
			}
		}
	}

	public void getPricingStructure(boolean changesToPricing) {		
		if (changesToPricing) { // IF true...
			for (int i = 0; i < products.length; i++) {
				System.out.print("Has item: '" + products[i] + "' changed pricing? ");
				String input = scan.nextLine();
				
				if (yesOrNo(input)) { // IF input is YES (true) then...
					try {
						System.out.print("What is the new price for item: '" + products[i] + "'? £"); // Get new price of item
						price[i] = scan.nextDouble();

						System.out.print("What is the new quantity to be eligible for the special price for item: '" + products[i] + "'? "); // Get new quantity criteria of item
						quantity[i] = scan.nextInt();

						System.out.print("What is the new special price for item: '" + products[i] + "'? £"); // Get new discount for item
						specialPrice[i] = scan.nextDouble();
						
						count[i] = 0; // Reset count variable when pricing structure changes
						
						System.out.println("");
					}
					 catch(Exception e) { // Capture exception to avoid program crashing
						 System.out.println(e.toString()); // Output exception to user for their convenience 
						 scan.next(); // Clear the scanned text, ready for next input
						 i--; // Replay the loop for the selected item
					}
				}
			}
		}
	}
	
	public boolean changesToPricing() {
		System.out.print("Are there any changes to the pricing structure? ");
		String input = scan.nextLine();
		
		if (yesOrNo(input)) { // IF input is YES (true) then...
			return true;
		}
		
		return false; // IF input is NO (false) then...
	}
	
	public void run() {
		System.out.println("Hi, welcome to the supermarket!\n");
		
		while (1 > 0) { // Infinite loop in case the user wants to input one item at a time rather than all at once
			System.out.print("Are there any items you'd like to buy? ");
			
			if (yesOrNo(scan.nextLine())) {
				getPricingStructure(changesToPricing()); // Find out new pricing structure per item
				System.out.println("Please enter your items: "); // Output a prompt to user to input items
				checkout(findItems(scan.nextLine())); // Checkout items that have just been input
			}
			
			else {
				break; // Break from infinite loop as finished processing
			}
		}

		scan.close(); // Close scanner variable to avoid data leaks

		System.out.println("\nYour total bill is: £" + getTotal()); // Output final total
	}
	
	public static void main(String[] args) { // Main method
		CDL_Kata cdl = new CDL_Kata(); // Non-static methods means I have to create a new object of the CDL_Kata class
		cdl.run(); // Run the program
	}
}
