package com.gen.entity;

import java.util.List;

public class Table {

	private String name; // 名称，数据库对应的表名
	private String entityName; // 实体名
	private String varName; // 首字母小写实体名
	private String comment; // 注释
	private String packageName; // 包路径
	private String packageMark; //包位置区分 如：base / content  / platform / wxconfig
	
	private Integer parentRes;			//父级菜单id
	
	private List<Column> columns;
	private List<Column> pageShowColumns; //列表页面展示字段集合
	private List<Column> queryColumns; //查询参数集合
	private List<Column> editColumns; //添加和修改对话框中展示的元素集合
	private List<Column> hiddenColumns; //隐藏元素集合
	private Column primaryKeyColumn; //主键，唯一区分
	private Column sortColumn; //排序字段

	public boolean hasColumn(String code) {
		if (columns == null) {
			return false;
		}
		for (Column c : columns) {
			if (c.checkCode(code)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasType(String type) {
		if (columns == null) {
			return false;
		}
		for (Column c : columns) {
			if (c.checkType(type)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasQueryColumns(){
		if(getQueryColumns() != null){
			return true;
		}
		return false;
	}
	public boolean hasSortColumn(){
		if(sortColumn != null){
			return true;
		}
		return false;
	}
	

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}


	public long getParentRes() {
		return parentRes;
	}

	public void setParentRes(Integer parentRes) {
		this.parentRes = parentRes;
	}

	public String getPackageMark() {
		return packageMark;
	}

	public void setPackageMark(String packageMark) {
		this.packageMark = packageMark;
	}

	public List<Column> getPageShowColumns() {
		return pageShowColumns;
	}

	public void setPageShowColumns(List<Column> pageShowColumns) {
		this.pageShowColumns = pageShowColumns;
	}

	public Column getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(Column primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public Column getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(Column sortColumn) {
		this.sortColumn = sortColumn;
	}

	public List<Column> getQueryColumns() {
		return queryColumns;
	}

	public void setQueryColumns(List<Column> queryColumns) {
		this.queryColumns = queryColumns;
	}

	public List<Column> getEditColumns() {
		return editColumns;
	}

	public void setEditColumns(List<Column> editColumns) {
		this.editColumns = editColumns;
	}

	public List<Column> getHiddenColumns() {
		return hiddenColumns;
	}

	public void setHiddenColumns(List<Column> hiddenColumns) {
		this.hiddenColumns = hiddenColumns;
	}
	
	
	
}
