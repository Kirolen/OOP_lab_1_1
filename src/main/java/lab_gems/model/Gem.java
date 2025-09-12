package lab_gems.model;

import lab_gems.types.GemType;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "gem")
public class Gem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GemType type;

    @Column(name = "weight_carat", nullable = false)
    private Double weightCarat;

    @Column(name = "price_per_carat", nullable = false)
    private Double pricePerCarat;

    @Column(nullable = false)
    private Double transparency;

    @Column(nullable = false)
    private String color;

    public Gem() {}

    public Gem(String name, GemType type, Double weightCarat, Double pricePerCarat, Double transparency, String color) {
        this.name = name;
        this.type = type;
        this.weightCarat = weightCarat;
        this.pricePerCarat = pricePerCarat;
        this.transparency = transparency;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GemType getType() {
        return type;
    }

    public void setType(GemType type) {
        this.type = type;
    }

    public Double getWeightCarat() {
        return weightCarat;
    }

    public void setWeightCarat(Double weightCarat) {
        this.weightCarat = weightCarat;
    }

    public Double getPricePerCarat() {
        return pricePerCarat;
    }

    public void setPricePerCarat(Double pricePerCarat) {
        this.pricePerCarat = pricePerCarat;
    }

    public Double getTransparency() {
        return transparency;
    }

    public void setTransparency(Double transparency) {
        this.transparency = transparency;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getTotalPrice() {
        return this.weightCarat * this.pricePerCarat;
    }

    @Override
    public String toString() {
        return "Gem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", weightCarat=" + weightCarat +
                ", pricePerCarat=" + pricePerCarat +
                ", transparency=" + transparency +
                ", color='" + color + '\'' +
                '}';
    }
}
