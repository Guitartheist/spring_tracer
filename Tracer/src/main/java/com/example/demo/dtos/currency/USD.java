package com.example.demo.dtos.currency;

public class USD {
	private int dollars;
	private int cents;
	
	public USD() {	
		this.dollars = 0;
		this.cents = 0;
	}
	
	public USD(String price) {
		String[] priceArray = price.split("\\.");
		this.dollars = Integer.valueOf(priceArray[0]);
		this.cents = Integer.valueOf(priceArray[1]);
	}
	
	public USD(int dollars, int cents) {	
		this.dollars = dollars;
		this.setCents(cents);
	}
	
	public String getPrice() {
		return Integer.toString(dollars)+"."+Integer.toString(cents);
	}
	
	public int getDollars() {
		return this.dollars;
	}
	public int getCents() {
		return this.cents;
	}
	
	public void setDollars(int dollars) {
		this.dollars = dollars;
	}
	public void setCents(int cents) {
		if (cents >= 100 ) {
			this.dollars += cents / 100;
			this.cents = cents % 100;
		}
		else {
			this.cents = cents;			
		}
	}
	
	public String addToPrice(String priceToAdd) {
		String[] priceArray = priceToAdd.split("\\.");
		int newDollars = this.dollars + Integer.valueOf(priceArray[0]);		
		int newCents = this.cents + Integer.valueOf(priceArray[1]);
		return String.valueOf(newDollars)+"."+String.valueOf(newCents);
	}	
	public String subtractFromPrice(String priceToSubtract) {
		String[] priceArray = priceToSubtract.split("\\.");
		int newDollars = this.dollars - Integer.valueOf(priceArray[0]);		
		int newCents = this.cents - Integer.valueOf(priceArray[1]);
		return String.valueOf(newDollars)+"."+String.valueOf(newCents);
	}
}
