package com.expensetracker.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity {

	
	 @Column(name = "first_name")
	    private String firstName;

	    @Column(name = "last_name")
	    private String lastName;

	    @Column(name = "email")
	    private String email;

	    @Column(name = "password")
	    private String password;
	    
	    @Column(name = "expense_limit", nullable = false, columnDefinition = "NUMERIC(6, 2) DEFAULT 0")
	    private Double expenseLimit;

	    public Account() {
		super();
	    }


		public String getFirstName() {
			return firstName;
		}


		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}


		public String getLastName() {
			return lastName;
		}



		public void setLastName(String lastName) {
			this.lastName = lastName;
		}



		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getPassword() {
			return password;
		}



		public void setPassword(String password) {
			this.password = password;
		}



		public Double getExpenseLimit() {
			return expenseLimit;
		}


		public void setExpenseLimit(Double expenseLimit) {
			this.expenseLimit = expenseLimit;
		}


		@Override
		public int hashCode() {
			return Objects.hash(email);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Account other = (Account) obj;
			return Objects.equals(email, other.email);
		}


		@Override
		public String toString() {
			return "Account [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
					+ password + ", expenseLimit=" + expenseLimit + "]";
		}
 

	
}
