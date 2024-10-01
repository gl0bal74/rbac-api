INSERT INTO access_tier (allow_sudo,hierarchy,id,description,name,sudo_password) VALUES
	 (true,20,'872CFBE33F334081BB1B93B00C67A14A','Lowest level of protection','Tier 1',NULL);
	 (true,50,'30E0B666C0C84951B02C60BA78A84008','Protected resource access with password','Tier 2','Tier 2'),
	 (false,100,'86E89C5E0AA54D30A54BEA0BB976BA9F','Locked down resource access','Tier 3',NULL),
	 (false,200,'537F2FE7E174455C8A191C9BA4BC46E5','High level of protection','Tier 4',NULL);
	 (false,400,'B04D75B13B954D8898E3FF08B3972146','Highest level of protection','Tier 5',NULL);

         

