import { Component } from '@angular/core';
import { Smartphone } from '../models/smartphone.model';
import { SmartphoneService } from '../services/smartphone.service';
import { SmartphoneUploadComponent } from '../smartphone-upload/smartphone-upload.component';
import { SmartphonesListComponent } from '../smartphones-list/smartphones-list.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-smartphones-manager',
  standalone: true,
  templateUrl: './smartphones-manager.component.html',
  styleUrls: ['./smartphones-manager.component.scss'],
  imports: [
    CommonModule,
    SmartphoneUploadComponent,
    SmartphonesListComponent
  ]
})
export class SmartphonesManagerComponent {
  smartphones: Smartphone[] = [];

  constructor(private smartphoneService: SmartphoneService) {}

  ngOnInit(): void {
    this.loadSmartphones();
  }

  loadSmartphones() {
    this.smartphoneService.getSmartphones().subscribe({
      next: data => this.smartphones = data,
      error: err => console.error('Erreur chargement smartphones', err)
    });
  }

  onUploadCompleted() {
    console.log("📂 Upload terminé, rechargement de la liste...");
    this.loadSmartphones();
  }
}
