# triple-assignment
트리플 사전 과제


## ERD
https://dbdiagram.io/d/62bc3f4c69be0b672c663792
![image](https://user-images.githubusercontent.com/40729223/176868796-bab94d04-0fee-483b-b376-bd4b30b43aaa.png)


## DDL
CREATE TABLE `User` (

  `id` UUID PRIMARY KEY,
  
  `username` varchar(255) NOT NULL,
  
  `password` varchar(255) NOT NULL,
  
  `point` integer DEFAULT 0,
  
  `createdAt` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  `modifiedAt` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  
);

CREATE TABLE `Place` (

  `id` UUID PRIMARY KEY,
  
  `name` varchar(255) NOT NULL,  
  
  `createdAt` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  `modifiedAt` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
);

CREATE TABLE `Review` (

  `id` UUID,
  
  `userId` UUID,
  
  `placeId` UUID,
  
  `content` varchar(255),
  
  `createdAt` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  `modifiedAt` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  PRIMARY KEY (`id`, `userId`, `placeId`)
  
);

CREATE TABLE `Image` (

  `id` UUID,
  
  `reviewId` UUID,
  
  `createdAt` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  `modifiedAt` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  PRIMARY KEY (`id`, `reviewId`)
  
);

CREATE TABLE `PointLog` (

  `id` UUID,
  
  `userId` UUID,
  
  `reviewId` UUID,
  
  `action` enum NOT NULL,
  
  `variation` integer,
  
  `createdAt` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  `modifiedAt` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  PRIMARY KEY (`id`, `userId`, `reviewId`)
  
);

ALTER TABLE `PointLog` ADD FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

ALTER TABLE `PointLog` ADD FOREIGN KEY (`reviewId`) REFERENCES `Review` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`placeId`) REFERENCES `Place` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

ALTER TABLE `Image` ADD FOREIGN KEY (`reviewId`) REFERENCES `Review` (`id`);

CREATE INDEX `logIndex` ON `PointLog` ( `userId` );

## 실행방법

application.properties에 db 정보를 입력후 실행시켜 주세요.

## 회원가입
![image](https://user-images.githubusercontent.com/40729223/176902862-e64a69a9-4150-477a-b7f9-fe241d894316.png)

## 장소추가
![image](https://user-images.githubusercontent.com/40729223/176902910-6794dbf0-f733-4a5c-88e4-687909d9fb7f.png)

## 리뷰추가

**리뷰 추가시 userId, placeId는 db에 존재해야합니다.**

![image](https://user-images.githubusercontent.com/40729223/176903097-9e631ec7-a68c-463e-8cf4-c6fcc82b42fb.png)

## 리뷰수정

**리뷰 수정시 reviewId, userId, placeId는 db에 존재해야합니다.**

![image](https://user-images.githubusercontent.com/40729223/176903499-8befc7ad-2893-49bf-a414-7d37e9f92aa7.png)

## 리뷰삭제

**리뷰 수정시 reviewId, userId, placeId는 db에 존재해야합니다.**

![image](https://user-images.githubusercontent.com/40729223/176903624-c64e5190-a18d-4cb0-b432-008926eec5c0.png)

## 포인트 조회

**포인트 조회시 userId는 db에 존재해야합니다.**

![image](https://user-images.githubusercontent.com/40729223/176903731-f9269667-1d4e-4085-bcf1-d12930b7b575.png)
