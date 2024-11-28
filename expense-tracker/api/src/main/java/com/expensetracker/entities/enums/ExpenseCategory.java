package com.expensetracker.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

	    ExpenseCategory(String name) {
	        this.name = name;
	    }

	    @JsonValue
	    public String getName() {
	        return name;
	    }

	    @JsonCreator
	    public static ExpenseCategory fromName(String name) {
	        for (ExpenseCategory category : ExpenseCategory.values()) {
	            if (category.name.equalsIgnoreCase(name)) {
	                return category;
	            }
	        }
	        throw new IllegalArgumentException("Invalid category: " + name);
	    }
}
