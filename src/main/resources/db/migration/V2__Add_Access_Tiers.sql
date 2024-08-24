INSERT INTO access_tier (allow_sudo,hierarchy,id,description,name,sudo_password) VALUES
	 (true,50,'30E0B666C0C84951B02C60BA78A84008','Protected resource access with password','Tier 2','Tier 2'),
	 (false,100,'86E89C5E0AA54D30A54BEA0BB976BA9F','Locked down resource access','Tier 3',NULL),
	 (true,20,'872CFBE33F334081BB1B93B00C67A14A','Lowest level of protection','Tier 1',NULL);

