-- H2 2.3.232; 
;              
CREATE USER IF NOT EXISTS "SA" SALT 'fd81a4bb1760163e' HASH 'c651bcd802484fc20274f94a982fae323f50c22f4bfa9f7dcb081eea1b7578f5' ADMIN;          
CREATE CACHED TABLE "PUBLIC"."CLOTHING_ITEM"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 16) NOT NULL,
    "CATEGORY" CHARACTER VARYING(255),
    "COLOR" CHARACTER VARYING(255),
    "IMAGE_PATH" CHARACTER VARYING(255),
    "NAME" CHARACTER VARYING(255),
    "SEASON" CHARACTER VARYING(255),
    "USER_ID" BIGINT
);     
ALTER TABLE "PUBLIC"."CLOTHING_ITEM" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("ID"); 
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.CLOTHING_ITEM;            
INSERT INTO "PUBLIC"."CLOTHING_ITEM" VALUES
(9, 'Tops', 'Burgandy', 'hoodie.jpg', 'Hoodie', 'Fall', NULL),
(10, 'Bottoms', 'Black', 'pants.jpg', 'Pants', 'Spring', NULL),
(11, 'Bottoms', 'White', 'short.jpg', 'Short', 'Summer', NULL),
(14, 'Tops', 'Black', 'tshirt.jpg', 'T-shirt', 'Summer', NULL);     
CREATE CACHED TABLE "PUBLIC"."OUTFIT_CLOTHING_ITEMS"(
    "OUTFIT_ID" BIGINT NOT NULL,
    "CLOTHING_ITEM_ID" BIGINT NOT NULL
);               
ALTER TABLE "PUBLIC"."OUTFIT_CLOTHING_ITEMS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" PRIMARY KEY("OUTFIT_ID", "CLOTHING_ITEM_ID");              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.OUTFIT_CLOTHING_ITEMS;    
CREATE CACHED TABLE "PUBLIC"."OUTFITS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 33) NOT NULL,
    "SEASON" CHARACTER VARYING(255),
    "USER_ID" BIGINT
);  
ALTER TABLE "PUBLIC"."OUTFITS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("ID");       
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.OUTFITS;  
CREATE CACHED TABLE "PUBLIC"."USERS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 2) NOT NULL,
    "PASSWORD" CHARACTER VARYING(255),
    "ROLE" CHARACTER VARYING(255),
    "USERNAME" CHARACTER VARYING(255)
);               
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID");         
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.USERS;    
INSERT INTO "PUBLIC"."USERS" VALUES
(1, '$2a$10$RGkVIcfyGPnvNojvAG.aZ.7h7e25zKhlYwXR8QEB3wRyklqfYkbru', 'ROLE_USER', 'fa');    
ALTER TABLE "PUBLIC"."CLOTHING_ITEM" ADD CONSTRAINT "PUBLIC"."FK80MNSEU7411978NST3AFB4G0H" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;   
ALTER TABLE "PUBLIC"."OUTFITS" ADD CONSTRAINT "PUBLIC"."FKF249BHUWJ850P7MBG5EL7A5F9" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;         
ALTER TABLE "PUBLIC"."OUTFIT_CLOTHING_ITEMS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_80" FOREIGN KEY("OUTFIT_ID") REFERENCES "PUBLIC"."OUTFITS"("ID") NOCHECK;     
ALTER TABLE "PUBLIC"."OUTFIT_CLOTHING_ITEMS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_802" FOREIGN KEY("CLOTHING_ITEM_ID") REFERENCES "PUBLIC"."CLOTHING_ITEM"("ID") NOCHECK;       