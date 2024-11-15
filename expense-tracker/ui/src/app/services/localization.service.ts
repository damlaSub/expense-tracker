import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root', // Makes the service globally available
})
export class LocalizationService {
  private currentLanguage = 'en';
  private translate: { [key: string]: string } = {};

  async loadLanguage(lang: string) {
     try {
    //const response = await fetch(`/assets/i18n/${lang}.json`);
    const response = await fetch(`/assets/i18n/${lang}.json`);
    if (!response.ok) {
      throw new Error(`Failed to fetch language file: ${response.statusText}`);
    }
    const data = await response.json(); 
    this.translate = data;
  } catch (error) {
    console.error('Error loading language file:', error);
  }
  }

  $t(key: string): string {
    return this.translate[key] || key;
  }

  get language() {
    return this.currentLanguage;
  }
}
