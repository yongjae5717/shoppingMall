<div><h1>Shopping Mall API Project</h1></div>

쇼핑몰 서비스 백앤드 API 및 데이터베이스 구축

<div><h2>💁 Business Logic</h2></div>

- 관리자: 상품을 등록, 수정, 삭제 할 수 있다.
- 유저: 상품을 주문, 조회, 취소할 수 있다.

<div><h2>📚 STACKS</h2></div>

<div> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
</div>

#### JPA, JWT, Security
<div><h2>😊 Init Domain</h2></div>

<details>
<summary> Authority </summary>

![](image/Authority.png)
</details>

<details>
<summary> User </summary>

![](image/User.png)
</details>

<details>
<summary> User_Authority </summary>

![](image/User_Authority.png)
</details>

<details>
<summary> Item </summary>

![](image/Item.png)
</details>

<details>
<summary> Top </summary>

![](image/Top.png)
</details>

<details>
<summary> Bottom </summary>

![](image/Bottom.png)
</details>

<details>
<summary> Delivery </summary>

![](image/Delivery.png)
</details>

<details>
<summary> Order_Item </summary>

![](image/Order_Item.png)
</details>

<details>
<summary> Orders </summary>

![](image/Orders.png)
</details>

<div><h2>🤚 Swagger UI</h2></div>

### 회원 관리 API
![](image/swaggerUserApi.png)

### 상품 API
![](image/swaggerItemApi.png)

### 주문 API
![](image/swaggerOrderApi.png)

### 인증 API
![](image/swaggerAuthApi.png)


<div><h2> ❌ Common Error </h2></div>

| error | message           | HttpStatus   |
|-------|-------------------|--------------|
| E000  | 이미 가입되어 있는 이메일입니다 | BAD_REQUEST  |
| E001  | 멤버를 찾을 수 없습니다     | NOT_FOUND    |
| E002  | 필수 항목이 누락되었습니다    | BAD_REQUEST  |
| E003  | 닉네임이 중복됩니다        | BAD_REQUEST  |
| E004  | 토근을 보유하고 있지 않습니다  | UNAUTHORIZED |
| E005  | 이메일을 찾을 수 없습니다    | BAD_REQUEST  |
| E006  | 재고가 모두 소진되었습니다    | BAD_REQUEST  |
| E007  | 아이템을 찾을 수 없습니다    | BAD_REQUEST  |