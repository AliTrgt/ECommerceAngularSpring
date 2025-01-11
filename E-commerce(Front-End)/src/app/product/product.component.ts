import { Component, ViewChild } from '@angular/core';
import { LoginComponent } from "../login/login.component";
import { CategoryComponent } from "../category/category.component";
import { ProductService } from '../services/product.service';
import { Product } from '../model/product';
import { CommonModule } from '@angular/common';
import { Category } from '../model/category';
import { CategoryService } from '../services/category.service';
import { CategoryFilterPipe } from '../pipe/category-filter.pipe';
import { FormsModule } from '@angular/forms';
import { UserService } from '../services/user.service';
import { User } from '../model/user';
import { RouterLink, RouterModule } from '@angular/router';
import { AuthServiceService } from '../services/security_and_guard/auth.service';
import { ProductFilterPipe } from "../pipe/product-filter.pipe";
import { SearchPipe } from '../pipe/search.pipe';

@Component({
    selector: 'app-product',
    standalone: true,
    imports: [CommonModule, CategoryFilterPipe, FormsModule, RouterLink, RouterModule, ProductFilterPipe, SearchPipe],
    templateUrl: './product.component.html',
    styleUrl: './product.component.css'
})
export class ProductComponent {
    isSameProduct: boolean = false;
    isSuccessful: boolean = false;
    isAdding: boolean = false;
    products: Product[] = [];
     category: Category[] = [];
    
    cartId: number = 0;
    role: string = '';
    haveStock: boolean = true;
    selectedCategory: string = '';
    user: User = {
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
        balance: 0,
        authorities: [],
        reviewList: [],
        orderList: []
    };
    lastRoles: string[] = [];
    lowPrice: number = 0; // Başlangıç değeri
    highPrice: number = 1000; // Başlangıç değeri
    minPrice: number = 0;
    maxPrice: number = 1000;
    searchText:string = '';
    onSliderInput() {
      if (this.lowPrice > this.highPrice) {
        const temp = this.lowPrice;
        this.lowPrice = this.highPrice;
        this.highPrice = temp;
      }
    }
    


    constructor(private productService: ProductService, private categoryService: CategoryService, private userService: UserService, private authservice: AuthServiceService) { }

    ngOnInit() {
        this.getUserData();
        this.getAllProductData();
        this.getAllCategoryData();


    }
    getAllProductData() {
        this.productService.getAllProduct().subscribe(data => {
            this.products = data.map(product => {
                product.imageUrl = 'http://localhost:8080' + product.imageUrl;
                return product;
            })
        }, error => {
            console.log("Can Not Fetch Product Data : " + error);
        }
        )
        
    }

    getAllCategoryData() {
        this.categoryService.getAllCategory().subscribe(
            (data) => {
                this.category = data;
                this.lastRoles = this.user.authorities.map(x => x.role);  // Rolleri hemen al
            }, (error) => {
                console.log("Can Not Fetch Categoy : " + error);
            }
        )
    }
    getUserData() {
        this.authservice.getUserInfo().subscribe(response => {
            this.user = response;
            this.updateCartInLocalStorage();
            console.log(this.user);
        }, error => {
            console.log("error : ", error);
        }
        )
    }
      

    updateCartInLocalStorage() {
        localStorage.setItem('userData', JSON.stringify(this.user));
        const updatedUserData = localStorage.getItem('userData');
        if (updatedUserData) {
            this.user = JSON.parse(updatedUserData);
        }
    }

    compareRole(): boolean {
        this.lastRoles = this.user.authorities.map(x => x.role);
        return this.lastRoles.includes('ROLE_SELLER');
    }

    addProductToCart(productId: number): void {
        const selectedProduct = this.products.find(x => x.id === productId);
        if (selectedProduct && selectedProduct.stock === 0) {
            this.haveStock = false;  // Stok olmayan ürün için uyarıyı göster
            setTimeout(() => {
                this.haveStock = true;  // 3 saniye sonra uyarıyı gizle
            }, 3000);
            return;  // Stok olmayan ürünü sepete eklemeyi durdur
        }
        if (this.user && this.user.cart?.id) {
            const cartId = this.user.cart.id;
            const isProductInCart = this.user.cart.cartItems.some(item => item.product?.id === productId);
            if (!isProductInCart) {
                this.productService.addProductToCart(productId, cartId, 1).subscribe({
                    next: () => {
                        this.isSuccessful = true;
                        this.getUserData();
                        setTimeout(() => {
                            this.isSuccessful = false;
                        }, 3000); // 3 saniye sonra kapanır
                    },
                    error: (err) => {
                        console.error('Error Adding Product to Cart:', err);
                        this.isSuccessful = false;
                    }
                });
            } else {
                this.isSameProduct = true;
                setTimeout(() => {
                    this.isSameProduct = false;
                }, 3000); // 3 saniye sonra kapanır
            }
        } else {
            console.error('User or Cart ID is not available');
        }
    }

    selectCategory(categoryName: string) {
        this.selectedCategory = categoryName;
    }

    closeAlert() {
        this.isSuccessful = false;
    }

    closeRepeatAlert() {
        this.isSameProduct = false;
    }

    closeHaveStock() {
        this.haveStock = true
    }

    isLoggedIn(): boolean {
        return this.authservice.isLoggedIn();
    }





}
