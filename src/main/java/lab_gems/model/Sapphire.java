package lab_gems.model;

import jakarta.persistence.*;
import lab_gems.types.GemType;

@Entity
@Table(name = "sapphire")
public class Sapphire extends Gem {
    private Integer hardness;
    private Double brilliance;

    public Sapphire() {}

    public Sapphire(String name, GemType type, Double weightCarat, Double pricePerCarat,
                    Double transparency, String color, Integer hardness, Double brilliance) {
        super(name, type, weightCarat, pricePerCarat, transparency, color); 
        this.hardness = hardness;
        this.brilliance = brilliance;
    }

    // getters/setters
    public Integer getHardness() { return hardness; }
    public void setHardness(Integer hardness) { this.hardness = hardness; }

    public Double getBrilliance() { return brilliance; }
    public void setBrilliance(Double brilliance) { this.brilliance = brilliance; }

    @Override
    public String toString() {
        return "Sapphire{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", weightCarat=" + getWeightCarat() +
                ", pricePerCarat=" + getPricePerCarat() +
                ", transparency=" + getTransparency() +
                ", color='" + getColor() + '\'' +
                ", hardness=" + hardness +
                ", brilliance=" + brilliance +
                '}';
    }
}
