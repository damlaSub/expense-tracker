package com.expensetracker.entities;

public class ExpenseLimitMessage {

	private String subject;
    private String body;

    public ExpenseLimitMessage(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

	@Override
	public String toString() {
		return "ExpenseLimitMessage [subject=" + subject + ", body=" + body + "]";
	}
    
}
