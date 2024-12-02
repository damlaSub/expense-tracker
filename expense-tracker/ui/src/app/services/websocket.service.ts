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
    this.client = new Client({
      brokerURL: this.brokerUrl,
      connectHeaders: {
        Authorization: `Bearer ${token}`, 
      }
    });
    console.log('Token sent:', `Bearer ${token}`);
    console.log(this.client)

  }

  connect(onMessageReceived: (message: Message) => void): void {
    this.client.onConnect = () => {
      console.log('Connected to WebSocket');

      // Subscribe to the notifications topic
      this.client.subscribe('/topic/notifications', (message: Message) => {
        this.messages.push(JSON.parse(message.body));
        onMessageReceived(message); // Can call this to update the UI
      });
    };

    this.client.onStompError = (frame) => {
      console.error('Broker error:', frame.headers['message']);
    };

    this.client.activate(); // Activate the WebSocket connection
  }
 

  sendMessage(destination: string, body: any): void {
    if (this.client.connected) {
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
    }
  }

  getMessages(): any[] {
    return this.messages;
  }
}
