import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import { environment } from '../../environments/environment.development';


@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private client: Client;
  private messages: any[] = [];  
  private brokerUrl= environment.brokerURL;

  constructor() {
    const token = localStorage.getItem('token');
    console.log('Token found in localStorage:', token); 
    this.client = new Client({
      brokerURL: `${this.brokerUrl}?token=${token}`, 
      debug: (str) => {
        console.log(`WebSocket Debug: ${str}`); 
      },
      reconnectDelay: 5000, 
    });
    console.log('WebSocket Client initialized:', this.client);
  }

  connect(onMessageReceived: (message: Message) => void): void {
    this.client.onConnect = () => {
      console.log('Connected to WebSocket');

      this.client.subscribe('/topic/notifications', (message: Message) => {
        this.messages.push(JSON.parse(message.body));
        onMessageReceived(message); 
      });
    };

    this.client.onStompError = (frame) => {
      console.error('STOMP error:', frame.headers['message']);
      console.error('Detailed error frame:', frame);
    };

    this.client.onWebSocketError = (error) => {
      console.error('WebSocket error occurred:', error);
      if (error.target instanceof WebSocket) {
        console.error('WebSocket details:', {
          readyState: error.target.readyState,
          url: error.target.url,
        });
      }
    };
    

    this.client.activate(); 
  }

  sendMessage(destination: string, body: any): void {
    if (this.client.connected) {
      console.log('Sending message:', { destination, body });
      this.client.publish({
        destination,
        body: JSON.stringify(body),
      });
    } else {
      console.warn('WebSocket is not connected');
    }
  }

  disconnect(): void {
    if (this.client.connected) {
      this.client.deactivate();
      console.log('Disconnected from WebSocket');
    } else {
      console.warn('Cannot disconnect: WebSocket is not connected');
    }
  }

  getMessages(): any[] {
    console.log('Returning stored messages:', this.messages);
    return this.messages;
  }
}
