package org.linlinjava.litemall.db.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LitemallBcUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.addtime
     *
     * @mbg.generated
     */
    private LocalDateTime addtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.REVISION
     *
     * @mbg.generated
     */
    private Integer revision;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.mobile
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.is_admin
     *
     * @mbg.generated
     */
    private Boolean isAdmin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.commit_user_id
     *
     * @mbg.generated
     */
    private Integer commitUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_bc_user.balance
     *
     * @mbg.generated
     */
    private BigDecimal balance;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.id
     *
     * @return the value of litemall_bc_user.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.id
     *
     * @param id the value for litemall_bc_user.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.addtime
     *
     * @return the value of litemall_bc_user.addtime
     *
     * @mbg.generated
     */
    public LocalDateTime getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.addtime
     *
     * @param addtime the value for litemall_bc_user.addtime
     *
     * @mbg.generated
     */
    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.deleted
     *
     * @return the value of litemall_bc_user.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.deleted
     *
     * @param deleted the value for litemall_bc_user.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.REVISION
     *
     * @return the value of litemall_bc_user.REVISION
     *
     * @mbg.generated
     */
    public Integer getRevision() {
        return revision;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.REVISION
     *
     * @param revision the value for litemall_bc_user.REVISION
     *
     * @mbg.generated
     */
    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.name
     *
     * @return the value of litemall_bc_user.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.name
     *
     * @param name the value for litemall_bc_user.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.mobile
     *
     * @return the value of litemall_bc_user.mobile
     *
     * @mbg.generated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.mobile
     *
     * @param mobile the value for litemall_bc_user.mobile
     *
     * @mbg.generated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.status
     *
     * @return the value of litemall_bc_user.status
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.status
     *
     * @param status the value for litemall_bc_user.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.is_admin
     *
     * @return the value of litemall_bc_user.is_admin
     *
     * @mbg.generated
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.is_admin
     *
     * @param isAdmin the value for litemall_bc_user.is_admin
     *
     * @mbg.generated
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.update_time
     *
     * @return the value of litemall_bc_user.update_time
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.update_time
     *
     * @param updateTime the value for litemall_bc_user.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.commit_user_id
     *
     * @return the value of litemall_bc_user.commit_user_id
     *
     * @mbg.generated
     */
    public Integer getCommitUserId() {
        return commitUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.commit_user_id
     *
     * @param commitUserId the value for litemall_bc_user.commit_user_id
     *
     * @mbg.generated
     */
    public void setCommitUserId(Integer commitUserId) {
        this.commitUserId = commitUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_bc_user.balance
     *
     * @return the value of litemall_bc_user.balance
     *
     * @mbg.generated
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_bc_user.balance
     *
     * @param balance the value for litemall_bc_user.balance
     *
     * @mbg.generated
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", id=").append(id);
        sb.append(", addtime=").append(addtime);
        sb.append(", deleted=").append(deleted);
        sb.append(", revision=").append(revision);
        sb.append(", name=").append(name);
        sb.append(", mobile=").append(mobile);
        sb.append(", status=").append(status);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", commitUserId=").append(commitUserId);
        sb.append(", balance=").append(balance);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LitemallBcUser other = (LitemallBcUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAddtime() == null ? other.getAddtime() == null : this.getAddtime().equals(other.getAddtime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getRevision() == null ? other.getRevision() == null : this.getRevision().equals(other.getRevision()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsAdmin() == null ? other.getIsAdmin() == null : this.getIsAdmin().equals(other.getIsAdmin()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCommitUserId() == null ? other.getCommitUserId() == null : this.getCommitUserId().equals(other.getCommitUserId()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAddtime() == null) ? 0 : getAddtime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getRevision() == null) ? 0 : getRevision().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsAdmin() == null) ? 0 : getIsAdmin().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCommitUserId() == null) ? 0 : getCommitUserId().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_bc_user
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        addtime("addtime", "addtime", "TIMESTAMP", false),
        deleted("deleted", "deleted", "BIT", false),
        revision("REVISION", "revision", "INTEGER", false),
        name("name", "name", "VARCHAR", true),
        mobile("mobile", "mobile", "VARCHAR", false),
        status("status", "status", "INTEGER", true),
        isAdmin("is_admin", "isAdmin", "BIT", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        commitUserId("commit_user_id", "commitUserId", "INTEGER", false),
        balance("balance", "balance", "DECIMAL", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_bc_user
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}