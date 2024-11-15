import { Injectable } from '@angular/core';

interface Translation {
  [key: string]: string | Translation;  
}

@Injectable({
  providedIn: 'root', // Makes the service globally available
})
export class LocalizationService {
  private currentLanguage = 'en';
  //private translate: Translation = {};  // Use the Translation interface here
  private $t: Translation = {}; 
  private loaded = false;

  // Loads the language JSON file and stores it in `translate`
  async loadLanguage(lang: string) {
    try {
      const response = await fetch(`/assets/i18n/${lang}.json`);
      if (!response.ok) {
        throw new Error(`Failed to fetch language file: ${response.statusText}`);
      }
      const data: Translation = await response.json();
      this.$t = data;
      this.loaded = true;
    } catch (error) {
      console.error('Error loading language file:', error);
    }
  }

  translate(key: string): string {
    if (!this.loaded) {
      return key;  
    }
  
    const keys = key.split('.');  // Split key for dot notation (e.g. 'home.welcome')
    let result: any = this.$t;  
  
    // Traverse the nested structure to find the translation
    for (const part of keys) {
      // If the key exists, move to the next level in the object
      if (result && typeof result === 'object' && part in result) {
        result = result[part];
      } else {
        return key; 
      }
    }
  
    return typeof result === 'string' ? result : key;
  }

  get language() {
    return this.currentLanguage;
  }
}
