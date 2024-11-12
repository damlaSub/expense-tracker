package com.expensetracker.entities;

import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


public class ExpenseCategory {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
	
private String name;

private Boolean isCustom;

@Override
public String toString() {
	return "ExpenseCategory [id=" + id + ", name=" + name + ", isCustom=" + isCustom + "]";
}

	public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Boolean getIsCustom() {
	return isCustom;
}

public void setIsCustom(Boolean isCustom) {
	this.isCustom = isCustom;
}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseCategory other = (ExpenseCategory) obj;
		return Objects.equals(name, other.name);
	}
}
