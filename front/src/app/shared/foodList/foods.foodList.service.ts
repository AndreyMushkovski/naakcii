import {Injectable} from '@angular/core';
import {FoodList} from './foods.foodList.model';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class FoodsFoodListService {
  private productsLocal = 'assets/json/FoodList.json';
  // private productsUrl = 'http://' + window.location.hostname +':8080/api/product';
  private productsUrl = 'http://178.124.206.42:8080/api/product';
  constructor(private http: HttpClient) {
  }

  getFoodList(selectedSubCatListID, first, last) {  //FoodList[]
    let listID = '';
    selectedSubCatListID.map(id => {
      listID += String(id.id) + ',';
    });
    const dataGet = {SubcategoryList: listID, first: first, last: last};

    //return this.http.get<FoodList[]>(this.productsLocal)                      //для отладки - из файла
    return this.http.get<FoodList[]>(this.productsUrl, {params: dataGet})   //для реального запроса с бэка
      .map(productList => {
        return productList.map(product => {
          let index = product['picture'].indexOf('%');
          if (index !== -1) {
            let first = product['picture'].substring(0, index);
            let second = product['picture'].substring(index + 1);
            product['picture'] = first + '%25' + second;
          }
          return {
            id: product['id'],
            name: product['name'],
            allPrice: product['price'],
            discount: product['discount'],
            totalPrice: product['discountPrice'],
            boxWeight: '',
            idStrore: product['chainId'],
            img: product['picture'],
            selected: false,
            selectAmount: 1
          };
        });
      });
  }
}


