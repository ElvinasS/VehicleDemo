DROP TABLE IF EXISTS vehicle;

CREATE TABLE vehicle (
id bigint NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
type varchar(255) NOT NULL,
make varchar(255) NOT NULL,
model varchar(255) NOT NULL,
color varchar(255) NOT NULL,
released bigint NOT NULL,
discontinued bigint DEFAULT NULL
);

  INSERT INTO vehicle (type, make, model, color, released, discontinued) VALUES ('type1', 'make1', 'model1', 'color1', 2000, 2005);
  INSERT INTO vehicle (type, make, model, color, released, discontinued) VALUES ('type2', 'make2', 'model2', 'color2', 2000, 2005);
  INSERT INTO vehicle (type, make, model, color, released, discontinued) VALUES ('type3', 'make3', 'model3', 'color3', 2000, 2005);
  INSERT INTO vehicle (type, make, model, color, released, discontinued) VALUES ('type4', 'make4', 'model4', 'color4', 2000, 2005);