
DROP TABLE IF EXISTS access_tier;
DROP TABLE IF EXISTS credential;
DROP TABLE IF EXISTS intent;
DROP TABLE IF EXISTS users;

-- rbac.access_tier definition
CREATE TABLE access_tier (
  allow_sudo boolean NOT NULL,
  hierarchy bigint DEFAULT NULL,
  id uuid NOT NULL,
  description varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  sudo_password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

-- rbac.intent definition

CREATE TABLE intent (
  access_tier_id uuid NOT NULL,
  id uuid NOT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (access_tier_id) REFERENCES access_tier (id)
);

-- rbac.user definition

CREATE TABLE users (
  access_tier_id uuid NOT NULL,
  id uuid NOT NULL,
  username varchar(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (access_tier_id) REFERENCES access_tier (id)
);

-- rbac.credential definition

CREATE TABLE credential (
  id uuid NOT NULL,
  intent_id uuid NOT NULL,
  user_id uuid NOT NULL,
  api_key varchar(255) DEFAULT NULL,
  serviceurl varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (intent_id) REFERENCES intent (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);