import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SmartphoneListComponent } from './components/smartphone-list/smartphone-list.component';
import { SmartphonePageComponent } from './smartphones-page/smartphones-page.component';

export const routes: Routes = [
  //{ path: '', component: SmartphoneListComponent },   // Par défaut, afficher la liste des smartphones
  { path: '', redirectTo: 'smartphones', pathMatch: 'full' },
  { path: 'smartphones', component: SmartphonePageComponent },  // Route dédiée pour la liste des smartphones
  // Ajoute ici d'autres routes si nécessaire (comme /smartphone/:id pour un détail de smartphone)
];
