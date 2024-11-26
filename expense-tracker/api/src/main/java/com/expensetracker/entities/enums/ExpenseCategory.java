package com.expensetracker.entities.enums;

public enum ExpenseCategory {
		FOOD("Food"),
		TRAVEL("Travel"),
		ENTERTAINMENT("Entertainment"),
		UTILITIES("Utilities"),
		HEALTH("Health"),
		GROCERIES("Groceries"),
		TRANSPORTATION("Transportation"),
		EDUCATION("Education"),
		PERSONAL_CARE("Personal care"),
		SHOPPING("Shopping"),
		BILLS("Bills"),
		INVESTMENTS("Investments"),
		OTHER("Other");
		
		private final String name;
	
	ExpenseCategory(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
