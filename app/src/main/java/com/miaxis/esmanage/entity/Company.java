package com.miaxis.esmanage.entity;

import android.support.annotation.NonNull;

import com.miaxis.esmanage.view.custom.treeView.TreeNode;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;

@Entity
public class Company implements Serializable, TreeNode {
	private static final long serialVersionUID = 9055024621635991911L;
	private int id;
	private String compname;
	@Id
	private String compcode;
	private String compno;
	private String phoneno;
	private String opuser;
	private String opdate;
	private String compaddress;
	private String parentcode;
	private String remark;
	private String opusername;
	private int subCount;

	@Transient
	private List<TreeNode> childrenList;
	@Transient
	private boolean isExpand;




	@Generated(hash = 1712971580)
	public Company(int id, String compname, String compcode, String compno,
			String phoneno, String opuser, String opdate, String compaddress,
			String parentcode, String remark, String opusername, int subCount) {
		this.id = id;
		this.compname = compname;
		this.compcode = compcode;
		this.compno = compno;
		this.phoneno = phoneno;
		this.opuser = opuser;
		this.opdate = opdate;
		this.compaddress = compaddress;
		this.parentcode = parentcode;
		this.remark = remark;
		this.opusername = opusername;
		this.subCount = subCount;
	}
	@Generated(hash = 1096856789)
	public Company() {
	}

	@Override
	public String getParentNodeCode() {
		return parentcode;
	}

	@Override
	public String getNodeCode() {
		return compcode;
	}

	@Override
	public boolean isExpanded() {
		return isExpand;
	}

	@Override
	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

	@Override
	public boolean hasChildren() {
		return childrenList != null && childrenList.size() != 0;
	}

	@Override
	public List<TreeNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public void setChildrenList(List<TreeNode> treeNodeList) {
		this.childrenList = treeNodeList;
	}

	@Override
	public int getNodeLevel() {
		if (compno != null)
			return compno.split("\\.").length;
		return 0;
	}

	@Override
	public String getLabel() {
		return compname;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TreeNode) {
			return ((TreeNode) obj).getNodeLevel() == getNodeLevel();
		} else {
			return super.equals(obj);
		}
	}


	@Override
	public int compareTo(@NonNull Object o) {
		if (o instanceof TreeNode) {
			return getNodeLevel() - ((TreeNode) o).getNodeLevel();
		} else {
			return 0;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getCompname() {
		return this.compname;
	}
	public void setCompname(String compname) {
		this.compname = compname;
	}
	public String getCompcode() {
		return this.compcode;
	}
	public void setCompcode(String compcode) {
		this.compcode = compcode;
	}
	public String getCompno() {
		return this.compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
	}
	public String getPhoneno() {
		return this.phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getOpuser() {
		return this.opuser;
	}
	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}
	public String getOpdate() {
		return this.opdate;
	}
	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}
	public String getCompaddress() {
		return this.compaddress;
	}
	public void setCompaddress(String compaddress) {
		this.compaddress = compaddress;
	}
	public String getParentcode() {
		return this.parentcode;
	}
	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOpusername() {
		return this.opusername;
	}
	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}
	public int getSubCount() {
		return this.subCount;
	}
	public void setSubCount(int subCount) {
		this.subCount = subCount;
	}



}
