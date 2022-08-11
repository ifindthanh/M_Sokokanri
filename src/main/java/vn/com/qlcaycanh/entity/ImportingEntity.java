package vn.com.qlcaycanh.entity;

import vn.com.qlcaycanh.common.Utils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tree_importing")
public class ImportingEntity {
    private Long id;
    private String description;

    private TreeCategory tree;
    private Long quantity;
    private Provider provider;
    private Date importDate;

    private Double price;
    private Double total;

    private Category category;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "importDate")
    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "CATEGORYID", nullable = false)
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "TREEID", nullable = false)
    public TreeCategory getTree() {
        return tree;
    }

    public void setTree(TreeCategory tree) {
        this.tree = tree;
    }

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "PROVIDERID", nullable = false)
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Transient
    public String getFormattedId() {
        return Utils.getFormattedId(this.id, 7);
    }
}
