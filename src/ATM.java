import java.util.Scanner;
public class ATM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc=new Scanner(System.in);
		Bank theBank=new Bank("Bank of India");
		User aUser=theBank.addUser("ABC","CDF", "1234");
		
		Account newAccount=new Account("checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while(true) {
			curUser=ATM.mainMenuPrompt(theBank,sc);
			ATM.printUserMenu(curUser, sc);
		}
		
	}
	
	public static User mainMenuPrompt(Bank theBank,Scanner sc) {
		String userID;
		String pin;
		User authUser;
		
		do {
			System.out.printf("\n\nwelcome to %s \n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID=sc.nextLine();
			System.out.print("Enter the pin: ");
			pin=sc.nextLine();
			
			authUser=theBank.userLogin(userID, pin);
			if(authUser==null) {
				System.out.println("Incorrect user ID or pin."+"please try again.");
			}
		}while(authUser==null);
		return authUser;
		
	}
	public static void printUserMenu(User theUser, Scanner sc) {
		theUser.printAccountsSummary();
		int choice;
		do {
			System.out.printf("welcome %s, what would you like to do?\n", theUser.getFirstName());
			System.out.println(" 1) Show account transction histroy");
			System.out.println(" 2) Withdrawl");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Histroy");
			System.out.println();
			System.out.print("Enter choice: ");
			choice=sc.nextInt();
			
			if(choice<1||choice>5) {
				System.out.println("Invalid choice. Please choose between 1 to 5. ");
			}
		}while(choice<1||choice>5);
		
		switch(choice) {
		
		case 1:
			ATM.showTransHistroy(theUser,sc);
			break;
		case 2:
			ATM.withdrawlFunds(theUser,sc);
			break;
		case 3:
			ATM.depositFunds(theUser,sc);
			break;
		case 4:
			ATM.transferFunds(theUser,sc);
			break;
		case 5:
			sc.nextLine();
			break;	
		}
		if(choice!=5) {
			ATM.printUserMenu(theUser, sc);
		}
	}
	public static void showTransHistroy(User theUser, Scanner sc) {
		int theAcct;
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+"whose transaction you want to see", theUser.numAccounts());
		theAcct=sc.nextInt()-1;
		if(theAcct<0||theAcct>=theUser.numAccounts()) {
			System.out.println("Invalid account. please try again.");
		}
		}while(theAcct<0||theAcct>=theUser.numAccounts());
	}
	public static void transferFunds(User theUser, Scanner sc) {
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+"to transfer from: ", theUser.numAccounts());
			fromAcct=sc.nextInt()-1;
		if(fromAcct<0||fromAcct>=theUser.numAccounts()) {
			System.out.println("Invalid account. please try again.");
		}
		}while(fromAcct<0||fromAcct>=theUser.numAccounts());
		acctBal=theUser.getAcctBalance(fromAcct);
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+"to transfer to: ", theUser.numAccounts());
			toAcct=sc.nextInt()-1;
		if(toAcct<0||toAcct>=theUser.numAccounts()) {
			System.out.println("Invalid account. please try again.");
		}
		}while(toAcct<0||toAcct>=theUser.numAccounts());
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount=sc.nextDouble();
		if(amount<0) {
			System.out.println("Amount must be greater than zero.");
		}else if (amount>acctBal) {
			System.out.printf("Amount must be not greater than \n"+ "balance of $%.02f.\n", acctBal);
		}
		}while(amount<8||amount>acctBal);
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));

	}
	
	public static void withdrawlFunds(User theUser, Scanner sc) {
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+"to withdrawl from : ", theUser.numAccounts());
			fromAcct=sc.nextInt()-1;
		if(fromAcct<0||fromAcct>=theUser.numAccounts()) {
			System.out.println("Invalid account. please try again.");
		}
		}while(fromAcct<0||fromAcct>=theUser.numAccounts());
		acctBal=theUser.getAcctBalance(fromAcct);
		
		do {
			System.out.printf("Enter the number to withdraw (max $%.02f): $", acctBal);
			amount=sc.nextDouble();
		if(amount<0) {
			System.out.println("Amount must be greater than zero.");
		}else if (amount>acctBal) {
			System.out.printf("Amount must be not greater than \n"+ "balance of $%.02f.\n", acctBal);
		}
		}while(amount<0||amount>acctBal);
		sc.hasNextLine();
		System.out.print("Enter amemo:");
		memo=sc.nextLine();
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);

	}	
	
	public static void depositFunds(User theUser, Scanner sc) {
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+"to deposit in : ", theUser.numAccounts());
			toAcct=sc.nextInt()-1;
		if(toAcct<0||toAcct>=theUser.numAccounts()) {
			System.out.println("Invalid account. please try again.");
		}
		}while(toAcct<0||toAcct>=theUser.numAccounts());
		acctBal=theUser.getAcctBalance(toAcct);
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount=sc.nextDouble();
		if(amount<0) {
			System.out.println("Amount must be greater than zero.");
		}
		}while(amount<0);
		sc.hasNextLine();
		System.out.print("Enter amemo:");
		memo=sc.nextLine();
		theUser.addAcctTransaction(toAcct, amount, memo);

	}	
	
}
