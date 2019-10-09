import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {ProductListComponent} from './product-list/product-list.component';
import {ProductDetailComponent} from './product-detail/product-detail.component';

// 指定路由
const routes: Routes = [
  {path: 'products', component: ProductListComponent},
  {path: 'product/:id', component: ProductDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule {
}
