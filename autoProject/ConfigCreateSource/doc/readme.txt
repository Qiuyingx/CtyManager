 xml配置文件参考：
  <resultMap id="BaseResultMap" type="com.hiibox.icity.entity.SpecialCategory" tables="t_special_category" comment="专刊分类" parentRes="10">
    <result column="name" property="name" jdbcType="VARCHAR" comment="名称" notNull="true" isSort="true" isSearchName="true" maxLength="20"/>
  </resultMap>

resultMap(表属性):
		tables 表名
		comment表名对应的注释，必填
		parentRes后台资源模块的父级菜单id，为数字，不填则默认为0
result(字段属性):
		comment注释 ，可填制（字符串），（页面显示的名称），必填
		notNull是否不能为空，可填制（"true","false"）， 页面添加时会验证，默认false，选填
		maxLength输入的最大长度，可填值（大于0的数字），默认0（不验证），填了就会在新增和修改的时候验证，选填
		isSort是否排序，可填值（"true","false"），默认false不排序，选填
		isSearchName是否是关键字模糊匹配的字段，可填值（"true","false"），默认false，选填
		isPageShow 是否在页面列表显示，可填值（"true","false"），默认true，选填
		isMethodParam 是否作为service与dao方法的参数，可填值（"true","false"），默认false，选填
		inputType 页面录入类型，可填值（"0","1","2","3","4","5"），0不录入，1输入框（默认），2区域输入框，3下拉框，4图片上传，5日期选择，默认"1"，选填
		selectName	 下拉数据字典名称，可填值（字符串），默认Constant.UNIVERSAL_STATUS(通用状态)，选填
		
		
注意事项：
1 每张表必须配置的属性有String comment; // 注释 ，long parentRes;			//父级菜单id
2 

table : 
	属性：
		String name; // 名称，数据库对应的表名
		String entityName; // 实体名
		String varName; // 首字母小写实体名
		String comment; // 注释
		String packageName; // 包路径
		long parentRes;			//父级菜单id
		
		List<Column> columns		//字段列表
	方法：
		boolean hasColumn(String column)   //是否包含该字段
		boolean hasType(String type)		//是否包含该类型的字段
		boolean hasImage()						//是否有图片上传的功能 
		
column:
	属性：
		private String name; // 名称
		private String code; // 编码
		private Type type; // 类型，javal类型
		private int maxLength;					//最大长度
		private boolean checkNull;			//是否验证不能为空
		private boolean isMethodParam;	//是否作为方法参数，默认false
		private int inputType;						//页面录入类型，0不录入，1输入框（默认），2区域输入框，3下拉框，4图片上传，5日期选择
		private String selectName;				//下拉数据字典名称，默认Constant.UNIVERSAL_STATUS(通用状态)
		private boolean isQuery;				//
		private boolean isSearchName;		//是否是关键字模糊匹配的字段，默认false
		private boolean isSort;					//是否排序，默认false
		private boolean isPageShow;					//是否在页面显示，默认true
		
	方法：
		String getCodeCap()		//获取编码首字母大写的字段名
		boolean checkCode(String code)	//判断是否是code字段名
		boolean checkType(String type)	//判断是否是type类型
		
	
type:
	属性：
		String d	//数据库类型
		String j	//java类型
		