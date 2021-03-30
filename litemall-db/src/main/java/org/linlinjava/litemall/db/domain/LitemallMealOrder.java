package org.linlinjava.litemall.db.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LitemallMealOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_meal_order
     *
     * @mbg.generated
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_meal_order
     *
     * @mbg.generated
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.order_id
     *
     * @mbg.generated
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.date
     *
     * @mbg.generated
     */
    private LocalDate date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.timing_id
     *
     * @mbg.generated
     */
    private Integer timingId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.timing_name
     *
     * @mbg.generated
     */
    private String timingName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.dishes_id
     *
     * @mbg.generated
     */
    private Integer dishesId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.dishes_name
     *
     * @mbg.generated
     */
    private String dishesName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.dishes_price
     *
     * @mbg.generated
     */
    private Long dishesPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.dishes_brief
     *
     * @mbg.generated
     */
    private String dishesBrief;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.dishes_pic_url
     *
     * @mbg.generated
     */
    private String dishesPicUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.add_time
     *
     * @mbg.generated
     */
    private LocalDateTime addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.cancelled
     *
     * @mbg.generated
     */
    private Boolean cancelled;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_meal_order.quantity
     *
     * @mbg.generated
     */
    private Integer quantity;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.id
     *
     * @return the value of litemall_meal_order.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.id
     *
     * @param id the value for litemall_meal_order.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.user_id
     *
     * @return the value of litemall_meal_order.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.user_id
     *
     * @param userId the value for litemall_meal_order.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.order_id
     *
     * @return the value of litemall_meal_order.order_id
     *
     * @mbg.generated
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.order_id
     *
     * @param orderId the value for litemall_meal_order.order_id
     *
     * @mbg.generated
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.date
     *
     * @return the value of litemall_meal_order.date
     *
     * @mbg.generated
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.date
     *
     * @param date the value for litemall_meal_order.date
     *
     * @mbg.generated
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.timing_id
     *
     * @return the value of litemall_meal_order.timing_id
     *
     * @mbg.generated
     */
    public Integer getTimingId() {
        return timingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.timing_id
     *
     * @param timingId the value for litemall_meal_order.timing_id
     *
     * @mbg.generated
     */
    public void setTimingId(Integer timingId) {
        this.timingId = timingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.timing_name
     *
     * @return the value of litemall_meal_order.timing_name
     *
     * @mbg.generated
     */
    public String getTimingName() {
        return timingName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.timing_name
     *
     * @param timingName the value for litemall_meal_order.timing_name
     *
     * @mbg.generated
     */
    public void setTimingName(String timingName) {
        this.timingName = timingName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.dishes_id
     *
     * @return the value of litemall_meal_order.dishes_id
     *
     * @mbg.generated
     */
    public Integer getDishesId() {
        return dishesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.dishes_id
     *
     * @param dishesId the value for litemall_meal_order.dishes_id
     *
     * @mbg.generated
     */
    public void setDishesId(Integer dishesId) {
        this.dishesId = dishesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.dishes_name
     *
     * @return the value of litemall_meal_order.dishes_name
     *
     * @mbg.generated
     */
    public String getDishesName() {
        return dishesName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.dishes_name
     *
     * @param dishesName the value for litemall_meal_order.dishes_name
     *
     * @mbg.generated
     */
    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.dishes_price
     *
     * @return the value of litemall_meal_order.dishes_price
     *
     * @mbg.generated
     */
    public Long getDishesPrice() {
        return dishesPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.dishes_price
     *
     * @param dishesPrice the value for litemall_meal_order.dishes_price
     *
     * @mbg.generated
     */
    public void setDishesPrice(Long dishesPrice) {
        this.dishesPrice = dishesPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.dishes_brief
     *
     * @return the value of litemall_meal_order.dishes_brief
     *
     * @mbg.generated
     */
    public String getDishesBrief() {
        return dishesBrief;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.dishes_brief
     *
     * @param dishesBrief the value for litemall_meal_order.dishes_brief
     *
     * @mbg.generated
     */
    public void setDishesBrief(String dishesBrief) {
        this.dishesBrief = dishesBrief;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.dishes_pic_url
     *
     * @return the value of litemall_meal_order.dishes_pic_url
     *
     * @mbg.generated
     */
    public String getDishesPicUrl() {
        return dishesPicUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.dishes_pic_url
     *
     * @param dishesPicUrl the value for litemall_meal_order.dishes_pic_url
     *
     * @mbg.generated
     */
    public void setDishesPicUrl(String dishesPicUrl) {
        this.dishesPicUrl = dishesPicUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.add_time
     *
     * @return the value of litemall_meal_order.add_time
     *
     * @mbg.generated
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.add_time
     *
     * @param addTime the value for litemall_meal_order.add_time
     *
     * @mbg.generated
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.cancelled
     *
     * @return the value of litemall_meal_order.cancelled
     *
     * @mbg.generated
     */
    public Boolean getCancelled() {
        return cancelled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.cancelled
     *
     * @param cancelled the value for litemall_meal_order.cancelled
     *
     * @mbg.generated
     */
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_meal_order
     *
     * @mbg.generated
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.deleted
     *
     * @return the value of litemall_meal_order.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.deleted
     *
     * @param deleted the value for litemall_meal_order.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_meal_order.quantity
     *
     * @return the value of litemall_meal_order.quantity
     *
     * @mbg.generated
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_meal_order.quantity
     *
     * @param quantity the value for litemall_meal_order.quantity
     *
     * @mbg.generated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_meal_order
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
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", date=").append(date);
        sb.append(", timingId=").append(timingId);
        sb.append(", timingName=").append(timingName);
        sb.append(", dishesId=").append(dishesId);
        sb.append(", dishesName=").append(dishesName);
        sb.append(", dishesPrice=").append(dishesPrice);
        sb.append(", dishesBrief=").append(dishesBrief);
        sb.append(", dishesPicUrl=").append(dishesPicUrl);
        sb.append(", addTime=").append(addTime);
        sb.append(", cancelled=").append(cancelled);
        sb.append(", deleted=").append(deleted);
        sb.append(", quantity=").append(quantity);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_meal_order
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
        LitemallMealOrder other = (LitemallMealOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getTimingId() == null ? other.getTimingId() == null : this.getTimingId().equals(other.getTimingId()))
            && (this.getTimingName() == null ? other.getTimingName() == null : this.getTimingName().equals(other.getTimingName()))
            && (this.getDishesId() == null ? other.getDishesId() == null : this.getDishesId().equals(other.getDishesId()))
            && (this.getDishesName() == null ? other.getDishesName() == null : this.getDishesName().equals(other.getDishesName()))
            && (this.getDishesPrice() == null ? other.getDishesPrice() == null : this.getDishesPrice().equals(other.getDishesPrice()))
            && (this.getDishesBrief() == null ? other.getDishesBrief() == null : this.getDishesBrief().equals(other.getDishesBrief()))
            && (this.getDishesPicUrl() == null ? other.getDishesPicUrl() == null : this.getDishesPicUrl().equals(other.getDishesPicUrl()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getCancelled() == null ? other.getCancelled() == null : this.getCancelled().equals(other.getCancelled()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_meal_order
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getTimingId() == null) ? 0 : getTimingId().hashCode());
        result = prime * result + ((getTimingName() == null) ? 0 : getTimingName().hashCode());
        result = prime * result + ((getDishesId() == null) ? 0 : getDishesId().hashCode());
        result = prime * result + ((getDishesName() == null) ? 0 : getDishesName().hashCode());
        result = prime * result + ((getDishesPrice() == null) ? 0 : getDishesPrice().hashCode());
        result = prime * result + ((getDishesBrief() == null) ? 0 : getDishesBrief().hashCode());
        result = prime * result + ((getDishesPicUrl() == null) ? 0 : getDishesPicUrl().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getCancelled() == null) ? 0 : getCancelled().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_meal_order
     *
     * @mbg.generated
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_meal_order
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        userId("user_id", "userId", "INTEGER", false),
        orderId("order_id", "orderId", "INTEGER", false),
        date("date", "date", "DATE", true),
        timingId("timing_id", "timingId", "INTEGER", false),
        timingName("timing_name", "timingName", "VARCHAR", false),
        dishesId("dishes_id", "dishesId", "INTEGER", false),
        dishesName("dishes_name", "dishesName", "VARCHAR", false),
        dishesPrice("dishes_price", "dishesPrice", "DECIMAL", false),
        dishesBrief("dishes_brief", "dishesBrief", "VARCHAR", false),
        dishesPicUrl("dishes_pic_url", "dishesPicUrl", "VARCHAR", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        cancelled("cancelled", "cancelled", "BIT", false),
        deleted("deleted", "deleted", "BIT", false),
        quantity("quantity", "quantity", "INTEGER", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
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
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_meal_order
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
         * This method corresponds to the database table litemall_meal_order
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
         * This method corresponds to the database table litemall_meal_order
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}