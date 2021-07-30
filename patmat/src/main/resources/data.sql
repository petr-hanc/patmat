INSERT INTO PUBLIC.DONORS (DONOR_ID,CREATED_ON,FIRST_NAME,LAST_NAME,TOWN) VALUES
	 (6,'2021-07-10','Ivo','Balenta','Uherské Hradiště'),
	 (7,'2021-07-10','','Luc Aviation Czech','Praha'),
	 (8,'2021-07-10','Hurvínek','Spejblův','Nymburk'),
	 (9,'2021-07-10','Včelí','Medvídci','Díra u Malé Čermné'),
	 (10,'2021-07-10','Jarek','Kmotr','Přerov'),
	 (11,'2021-07-10','Hugo','Hezký','Hradec Králové');
	 
INSERT INTO PUBLIC.DONATIONS (DONAT_ID,AMOUNT,DATE_DT,MESSAGE,DONOR_ID,CREATED_ON) VALUES
	 (1,500000,'2020-11-05','Nezapomeňte!',6,'2021-07-24'),
	 (2,200000,'2021-01-01','Prémie :)',6,'2021-07-24'),
	 (3,1000000,'2020-10-23','My budem tovarysici',7,'2021-07-24'),
	 (4,3000,'2020-12-22','',7,'2021-07-24'),
	 (5,5000,'2020-09-15','',7,'2021-07-24'),
	 (6,1,'2021-05-31','Pardón, šetřím na kolo!',8,'2021-07-25'),
	 (7,200000,'2021-05-12','Hele, takže kámoši, jó?',10,'2021-07-25'),
	 (8,100000,'2021-05-22','Tak teďka, jó?',10,'2021-07-25'),
	 (9,10,'2021-05-26','Brum',9,'2021-07-25');