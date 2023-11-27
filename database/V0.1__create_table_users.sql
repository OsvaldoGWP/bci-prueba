
-- CREATE users TABLE
create TABLE users (
  id UUID NOT NULL,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   lastLogin TIMESTAMP WITHOUT TIME ZONE,
   token VARCHAR(255),
   isActive BOOLEAN NOT NULL DEFAULT false,
   createdBy VARCHAR(255),
   createdDate TIMESTAMP WITHOUT TIME ZONE,
   modifiedBy VARCHAR(255),
   modifiedDate TIMESTAMP WITHOUT TIME ZONE,

   CONSTRAINT pk_users PRIMARY KEY (id)
);
