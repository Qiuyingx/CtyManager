(取消)1:表 t_activity_info 中 managerId 字段  数据类型修改为： varchar(64) NOT NULL
2:表 t_shop 中 remark 字段  数据类型修改为： text
3:表 t_shop 中 listImage 字段  数据类型修改为： text
4:表 t_activity_info 中 新增字段 
	isAllYards bit 是否面向所有社区 1（是）；0（否）
	courtyardIds text 社区IDs
	cityId int 城市ID
	courtyardNames text 活动范围（面向院子名称s）