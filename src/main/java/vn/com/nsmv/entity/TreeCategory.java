package vn.com.nsmv.entity;

import javax.persistence.*;

@Entity
@Table(name = "tree_category")
public class TreeCategory {
    private Long id;
    private String name;

    private Tree treeGroup;

    private Integer age;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "tree_id", nullable = false)
    public Tree getTreeGroup() {
        return treeGroup;
    }

    public void setTreeGroup(Tree treeGroup) {
        this.treeGroup = treeGroup;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
