package com.gen.entity;

import com.gen.util.StringUtil;

public class Column {

	private String name; // 名称comment 默认""
	private String code; // 编码column
	private String comment; //
	private String entityName; //实体名称
	private Type type; // 类型，javal类型jdbcType
	private int maxLength;					//最大长度maxLength 默认0
	private boolean checkNull;			//是否验证不能为空notNull 默认false
	private boolean isMethodParam;	//是否作为方法参数，默认false isMethodParam
	private String selectName;				//下拉数据字典名称，默认Constant.UNIVERSAL_STATUS(通用状态)
	private boolean isQuery;				//是否作为查询参数 默认false
	private int inputType;						
	/**
	 * //页面录入类型，
	 *    默认 1
	 *     0   不录入，
	 *     1 easyUI文本框（默认） class="easyui-textbox"
	 *     2 下拉列表框                                 class="easyui-combobox"
	 *     3 日期输入框                                 class="easyui-datebox"
	 *     4 日期时间输入框                         class="easyui-datetimebox"
	 *     5 日期时间微调框                         class="easyui-datetimespinner" 
	 *     6时间段选择（开始至结束时间） 
	 *     7 数字微调                                       class="easyui-numberspinner"
	 *     8 时间微调                                        class="easyui-timespinner"
	 *     9文件上传                                           class="easyui-filebox"
	 * 
	 */
	private boolean isSearchName;		//是否是关键字模糊匹配的字段，默认false isSearchName
	private boolean isSort;					//是否排序，默认false isSort(一列)
	private boolean isPageShow;					//是否在页面显示，默认true isPageShow selectName
	private boolean isPrimaryKey; //是否为主键  默认false（一列）
	private boolean isEdit; //是否 需要在添加和修改对话框中出现  默认true
	
	
	
	/**
	 * 是否是这个类型
	 * @param type
	 * @return
	 */
	public boolean checkType(String type) {
		try {
			return this.type.getJ().equals(type);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 是否是这个字段
	 * @param code
	 * @return
	 */
	public boolean checkCode(String code) {
		try {
			return this.code.equals(code);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 首字母变大写
	 * @return
	 */
	public String getCodeCap() {
		if (code == null) {
			return code;
		} else if (code.length() == 0) {
			return "";
		} else {
			return code.substring(0, 1).toUpperCase().concat(code.substring(1));
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}



	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isCheckNull() {
		return checkNull;
	}

	public void setCheckNull(boolean checkNull) {
		this.checkNull = checkNull;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isMethodParam() {
		return isMethodParam;
	}

	public void setMethodParam(boolean isMethodParam) {
		this.isMethodParam = isMethodParam;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public String getSelectName() {
		if(!StringUtil.isNotEmpty(selectName) || selectName.indexOf(".") != -1) {
			return selectName;
		} else {
			return "\""+selectName+"\"";
		}
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public boolean isQuery() {
		return isQuery;
	}

	public void setQuery(boolean isQuery) {
		this.isQuery = isQuery;
	}

	public boolean isSort() {
		return isSort;
	}

	public void setSort(boolean isSort) {
		this.isSort = isSort;
	}

	public boolean isSearchName() {
		return isSearchName;
	}

	public void setSearchName(boolean isSearchName) {
		this.isSearchName = isSearchName;
	}

	public boolean isPageShow() {
		return isPageShow;
	}

	public void setPageShow(boolean isPageShow) {
		this.isPageShow = isPageShow;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
   
	
	
}
