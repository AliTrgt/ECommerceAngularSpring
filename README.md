# E-Ticaret Platformu

https://github.com/user-attachments/assets/5b4b6eb3-11f0-4506-9cc5-fafe75739917

![Database Diagram](./DatabaseDiagram.png)


Bu proje, hem alıcılar hem de satıcılar için modern ve kullanışlı bir e-ticaret deneyimi sunmayı amaçlayan bir platformdur. Kullanıcılar, ürünleri inceleyip satın alabilirken; satıcılar, kendi ürünlerini listeleyip yönetebilirler. Platform, JWT tabanlı kimlik doğrulama ile güvenlik altına alınmış ve çağdaş teknolojilerle geliştirilmiştir. ve modern web teknolojileri ile desteklenmiştir.

## Özellikler

- **Kullanıcı Kayıt ve Giriş:** Kullanıcılar siteye kayıt olabilir ve giriş yapabilir.
- **Kimlik Doğrulama:** JWT (JSON Web Token) kullanılarak güvenli giriş ve işlem yapılabilir.
- **Ürün Listeleme:** Satıcılar yeni ürünler ekleyebilir ve mevcut ürünlerini güncelleyebilir.
- **Ürün Yorumlama:** Kullanıcılar ürünlere yorum yapabilir ve diğer kullanıcıların yorumlarını görüntüleyebilir.
- **Sepet Yönetimi:** Kullanıcılar ürünleri sepete ekleyebilir, sepetlerini düzenleyebilir ve ödeme işlemini gerçekleştirebilir.
- **Sipariş Yönetimi:** Kullanıcılar verdikleri siparişlerin durumunu görüntüleyebilir.
- **Bakiye Kontrolü:** Kullanıcılar, bakiye durumlarına göre ödeme yapabilirler.

## Kullanılan Teknolojiler

- **Backend:**
  - Java
  - Spring Boot
  - Hibernate (ORM)
  - Maven
  - JWT (Kimlik Doğrulama)

- **Frontend:**
  - Angular
  - TypeScript
  - JavaScript
  - Angular Router (Routing için)

- **Veritabanı:**
  - MySQL

## Kurulum

### 1. Projeyi Klonlayın

```bash
git clone https://github.com/kullaniciadi/e-ticaret-platformu.git
```

### 2. Backend Kurulumu

Backend klasörüne gidin ve bağımlılıkları yükleyin:

```bash
cd backend
./mvnw install
./mvnw spring-boot:run
```

### 3. Frontend Kurulumu

Frontend klasörüne gidin ve bağımlılıkları yükleyin:

```bash
cd frontend
npm install
npm start
```

### 4. Veritabanı Yapılandırması

- MySQL sunucusunu başlatın.
- Gerekli veritabanını ve tabloları oluşturun.
- `application.properties` dosyasındaki veritabanı bağlantı ayarlarını güncelleyin.

## Proje Yapısı

```plaintext
e-ticaret-platformu/
│
├── backend/               # Spring Boot Backend
│   ├── src/
│   └── ...
│
├── frontend/              # Angular Frontend
│   ├── src/
│   └── ...
│
├── README.md              # Proje açıklamaları
└── ...
```
