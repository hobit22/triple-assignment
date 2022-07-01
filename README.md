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
