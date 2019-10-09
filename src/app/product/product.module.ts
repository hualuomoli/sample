import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ProductService} from './product.service';

import {ProductListComponent} from './product-list/product-list.component';
import {ProductDetailComponent} from './product-detail/product-detail.component';

import {ProductRoutingModule} from './product-routing.module';

@NgModule({
  providers: [
    ProductService
  ],
  declarations: [
    ProductListComponent,
    ProductDetailComponent],
  imports: [
    CommonModule,
    ProductRoutingModule
  ]
})
export class ProductModule {
}
