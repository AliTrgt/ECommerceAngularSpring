import { Component } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { response } from 'express';
import { Product } from '../../model/product';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Review } from '../../model/review';
import { ReviewService } from '../../services/review.service';
import { User } from '../../model/user';
import { last } from 'rxjs';
import { error } from 'node:console';
import { AuthServiceService } from '../../services/security_and_guard/auth.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-product-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-page.component.html',
  styleUrl: './product-page.component.css'
})
export class ProductPageComponent {

  constructor(private productService: ProductService, private route: ActivatedRoute, private reviewService: ReviewService,private authService:AuthServiceService,private userService:UserService) { }
  relatedProducts: Product[] = [];
  reviewList: Review[] = [];
  isBlank: boolean = false;
  haveStock:boolean = true;
  localImage: string | null = null;
  isLoading: boolean = false;
  isAdding:boolean = false;
  isSameItem:boolean = false;
  isBlankRating: boolean = false;
  role:String[] = [];
  review: Review = {
    id: 0,
    rating: 0,
    comment: '',
    user: null,
    product: null
  };
  productId: number = 0;
  product: Product = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    stock: 0,
    reviewList: [],
    imageUrl: '',
  }
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
  }

  ngOnInit() {
    const data = localStorage.getItem('userData');
    if (data) {
      this.user = JSON.parse(data);
    }
    
    this.route.paramMap.subscribe(params => {
      this.productId = Number(params.get('id'));
      console.log("productId", this.productId);
    })
    if (this.productId) {
      this.findProductById();
    }
    
  }
  getUserData() {
    this.authService.getUserInfo().subscribe(response => {
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
 
    getUserRole(){
        this.role = this.user.authorities.map(x => x.role);
        return this.role.includes('ROLE_SELLER');
    }

  addProductToCart(){
        const product = this.product;
        if(product && product.stock <= 0 ){
            this.haveStock = false;
            setTimeout(() => {
              this.haveStock = true;  
          }, 3000);
          return;
        }
        if(this.user && this.user.cart?.id){
         let productId = this.product.id;
         let cartId = this.user.cart?.id;
          const recentProduct = this.user.cart.cartItems.some(x => x.product?.id === productId);
          
          if(!recentProduct){
            this.productService.addProductToCart(productId,cartId,1).subscribe({
              next: () => {
                this.isAdding = true;
                this.getUserData();
                setTimeout( () => {
                    this.isAdding = false;
                },3000 )
  
              },
              error: (error) => {
                      console.log("error : "+error);
                      this.isAdding = false;
              }
            });
          }else this.isSameItem = true;
            setTimeout( () => {
                  this.isSameItem = false;
            },3000 )

          }

        
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input?.files?.length) {
      const file = input.files[0];
      this.productService.uploadPhoto(this.product.id, file).subscribe(response => {
        this.product.imageUrl = response;
        console.log("Fotoğraf yüklendi: ",response);
      },error => {
          console.log("Error mu ne bu : ",error);
      });
      
      setTimeout(() => {
        window.location.reload();
      },3000)
    }
  }



  findProductById() {
    this.productService.getProductById(this.productId).subscribe(response => {
      this.product = response;
      if (this.product.imageUrl) {
        this.product.imageUrl = `http://localhost:8080${response.imageUrl}`;
    }
      console.log('Gelen Ürün: ', this.product); // Debug log
      this.reviewList = this.product.reviewList;
    });
}


  createReview() {
    if (!this.review.comment.trim()) {
      this.isBlank = true;
      setTimeout(() => {
        this.isBlank = false;  // alerti kapatmak için
      }, 3000);
      return;
    }
    if (this.review.rating === 0) {
      this.isBlankRating = true;
      setTimeout(() => {
        this.isBlankRating = false;  // hata mesajını 3 saniye sonra kapat
      }, 3000);
      return;
    }
    this.review.user = this.user;
    this.review.product = this.product;
    this.reviewService.createReview(this.review).subscribe(response => {
      this.findProductById();
      this.review = response;
      this.review = { id: 0, rating: 0, comment: '', user: null, product: null };
    })
  }

  deleteReview(reviewId: number) {
    this.reviewService.deleteReview(reviewId).subscribe(response => {
      this.reviewList = this.reviewList.filter(x => x.id !== reviewId);
      console.log("Deleted !!!");
    })
  }

  canDeleteReview(review: Review): boolean {
    return this.user.id === review.user?.id;
  }

  closeAlertBox() {
    this.isBlank = false;
  }

  closeAddingAlertBox(){
      this.isAdding = false;
  }

  closeAlreadyHave(){
      this.isSameItem = false;
  }

  closeHaveStock(){
      this.haveStock = true
  }

  isLoggedIn():boolean {
      return this.authService.isLoggedIn();
  }


  randomNumberGenerate() : number{
      return Math.round( (Math.random() * 5) + 1);  
  }

}
