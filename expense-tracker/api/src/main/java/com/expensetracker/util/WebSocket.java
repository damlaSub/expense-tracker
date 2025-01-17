package com.expensetracker.util;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.expensetracker.entities.ExpenseLimitMessage;

@Component
public class WebSocket {

	private final SimpMessagingTemplate expenseLimitMsgTemplate;

    
    public WebSocket(SimpMessagingTemplate expenseLimitMsgTemplate) {
        this.expenseLimitMsgTemplate = expenseLimitMsgTemplate;
    }

    public void sendExpenseLimitNotification(String userName, Double currentAmount, Double limit, Double remainingAmount) {
        String subject = "Expense Limit Alert: 80% Reached";
        String body = String.format("Hello %s,\n\nYou’ve reached 80%% of your allocated expense limit for this month, with a current spend of $%.2f, a limit of $%.2f, and $%.2f remaining.", 
                userName, currentAmount, limit, remainingAmount);

        ExpenseLimitMessage expenseLimitMsg = new ExpenseLimitMessage(subject, body);
        System.out.println( "WebSocket utils package " + subject);
        expenseLimitMsgTemplate.convertAndSend("/topic/notifications", expenseLimitMsg);
    }

    public void sendExpenseOverLimitNotification(String userName, Double currentAmount, Double limit) {
        String subject = "Expense Limit Exceeded";
        String body = String.format("Hello %s,\n\nYou have exceeded your allocated expense limit for this month. Your current spending is $%.2f, which surpasses your limit of $%.2f. Please review your expenses.", 
                    userName, currentAmount, limit);

        ExpenseLimitMessage expenseOverLimitMsg = new ExpenseLimitMessage(subject, body);
        System.out.println("WebSocket utils package " + subject);
        expenseLimitMsgTemplate.convertAndSend("/topic/notifications", expenseOverLimitMsg);
    }

}
