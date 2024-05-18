
DROP TABLE IF EXISTS `access_tier`;
DROP TABLE IF EXISTS `credential`;
DROP TABLE IF EXISTS `intent`;
DROP TABLE IF EXISTS `user`;

-- rbac.access_tier definition
CREATE TABLE `access_tier` (
  `allow_sudo` bit(1) NOT NULL,
  `hierarchy` bigint DEFAULT NULL,
  `id` binary(16) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sudo_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- rbac.intent definition

CREATE TABLE `intent` (
  `access_tier_id` binary(16) NOT NULL,
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfakxaoyb6pa5ck1xeae3owchj` (`access_tier_id`),
  CONSTRAINT `FKfakxaoyb6pa5ck1xeae3owchj` FOREIGN KEY (`access_tier_id`) REFERENCES `access_tier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- rbac.`user` definition

CREATE TABLE `user` (
  `access_tier_id` binary(16) NOT NULL,
  `id` binary(16) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiua758xayl0lhsuac23jcjb9d` (`access_tier_id`),
  CONSTRAINT `FKiua758xayl0lhsuac23jcjb9d` FOREIGN KEY (`access_tier_id`) REFERENCES `access_tier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- rbac.credential definition

CREATE TABLE `credential` (
  `id` binary(16) NOT NULL,
  `intent_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `api_key` varchar(255) DEFAULT NULL,
  `serviceurl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKndx32lhqh4ncjqj7da9wn49ex` (`intent_id`),
  KEY `FKpg7bdnqxpyhrt7f8soul9y7ne` (`user_id`),
  CONSTRAINT `FKndx32lhqh4ncjqj7da9wn49ex` FOREIGN KEY (`intent_id`) REFERENCES `intent` (`id`),
  CONSTRAINT `FKpg7bdnqxpyhrt7f8soul9y7ne` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;