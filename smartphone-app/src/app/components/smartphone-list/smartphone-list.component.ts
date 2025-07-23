import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SmartphoneService } from '../../services/smartphone.service';
import { Smartphone } from '../../models/smartphone.model';

@Component({
  selector: 'app-smartphone-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './smartphone-list.component.html',
  styleUrls: ['./smartphone-list.component.scss']
})
export class SmartphoneListComponent implements OnInit {
  @Input() smartphones: Smartphone[] = [];
  //smartphones: Smartphone[] = [];

  constructor(private smartphoneService: SmartphoneService) {
      console.log('SmartphoneListComponent instancié');
    }

/*
  ngOnInit(): void {
    this.smartphoneService.getSmartphones().subscribe(
      (data) => {
        this.smartphones = data;
        console.log(data);  // Vérifier les données reçues
        },
      (error) => {
        console.error('Erreur lors du chargement des smartphones', error)
        }
    );
  }*/
  ngOnInit(): void {
    this.smartphoneService.getSmartphones().subscribe({
      next: data => {
        this.smartphones = data;
        console.log('📲 Smartphones chargés :', data);
      },
      error: err => {
        console.error('❌ Erreur chargement smartphones', err);
      }
    });
  }

  refreshSmartphones(): void {
    this.smartphoneService.getSmartphones().subscribe({
      next: data => {
        this.smartphones = data;
        console.log('🔁 Smartphones rafraîchis après upload :', data);
      },
      error: err => {
        console.error('❌ Erreur lors du rafraîchissement', err);
      }
    });
  }

}

