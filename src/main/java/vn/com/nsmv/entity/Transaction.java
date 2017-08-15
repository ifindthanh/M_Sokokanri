package vn.com.nsmv.entity;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.i18n.LocaleContextHolder;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.TransactionTypeEnum;

@Entity
@Table(name = "transaction")
public class Transaction {
    private Long id;
    private Long userId;
    private String transactionType;
    private Integer amount;
    private Date createDate;
    private User user;
    private User trader;
    private String comment;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name = "user")
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Column(name = "type")
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    @Column(name = "amount")
    public Integer getAmount() {
        return amount;
    }
    
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Transient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void validate() throws SokokanriException {
        Locale locale = LocaleContextHolder.getLocale();
        if (this.userId == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidUser(locale));
        }
        
        if (Utils.isEmpty(this.transactionType)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorTransactionTypeCannotBeEmpty(locale));
        }
        if (TransactionTypeEnum.valueOf(this.transactionType) == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorTransactionTypeInvalid(locale));
        }
        if (this.amount == null || this.amount <=0) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorAmountCannotBeEmpty(locale));
        }
    }
    
    @Transient
    public String getFormattedId() {
        return Utils.getFormattedId(this.id, 7);
    }

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "trader", nullable = false)    
    public User getTrader() {
        return trader;
    }
    
    public void setTrader(User trader) {
        this.trader = trader;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
