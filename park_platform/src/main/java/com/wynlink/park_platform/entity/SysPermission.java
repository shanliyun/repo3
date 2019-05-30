package com.wynlink.park_platform.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Vincent
 * @since 2019-05-21
 */
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String permName;
    private Integer pid;
    private String url;
    @TableField(exist=false)
    private boolean checked = false;
    @TableField(exist=false)
    private List<SysPermission> childPermissions = new ArrayList<SysPermission>();
    private Date createTime;
    private Date updateTime;

    public List<SysPermission> getChildPermissions() {
		return childPermissions;
	}

	public void setChildPermissions(List<SysPermission> childPermissions) {
		this.childPermissions = childPermissions;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return "SysPermission [id=" + id + ", permName=" + permName + ", pid=" + pid + ", url=" + url + ", checked="
				+ checked + ", childPermissions=" + childPermissions + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
    
    

}
