import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Smartphone } from '../models/smartphone.model';
import { Observable } from 'rxjs';

/*export interface Smartphone {
  id: number;
  marque: string;
  modele: string;
  os: string;
  annee: number;
  tailleEcran: number;
  prix: number;
}*/

@Injectable({
  providedIn: 'root'
})
export class SmartphoneService {

  private apiUrl = 'http://localhost:8080/api/smartphones'; // à adapter si nécessaire

  constructor(private http: HttpClient) { }

  getSmartphones(): Observable<Smartphone[]> {
    return this.http.get<Smartphone[]>(this.apiUrl);
  }

}
