package com.yard.manager.base.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yard.manager.system.entity.SysMenuViewEntity;

/**
 * easyui树型控件数据结构
 * @author jiangbo
 *
 */
public class TreeNodeEntity extends SysMenuViewEntity implements Serializable {
	private static final long serialVersionUID = 3209039211080478651L;

	private String id; // 节点ID，对加载远程数据很重要。 
	private String text; // 显示节点文本。 
	private String state; // 节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。 
	private boolean checked; // 表示该节点是否被选中。
	private Map<String, Object> attributes; // 被添加到节点的自定义属性。
	private List<TreeNodeEntity> children; // 一个节点数组声明了若干节点。

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNodeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeEntity> children) {
		this.children = children;
	}
}
