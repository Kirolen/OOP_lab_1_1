package lab_gems.model;

import jakarta.persistence.*;
import lab_gems.types.GemType;

@Entity
@Table(name = "topaz")
public class Topaz extends Gem {

    private Double hardness;
    private Double lightReflectivity;

    public Topaz() {}

    public Topaz(String name, GemType type, Double weightCarat, Double pricePerCarat,
                 Double transparency, String color, Double hardness, Double lightReflectivity) {
        super(name, type, weightCarat, pricePerCarat, transparency, color); // топаз напівдорогоцінний
        this.hardness = hardness;
        this.lightReflectivity = lightReflectivity;
    }

    // getters/setters
    public Double getHardness() { return hardness; }
    public void setHardness(Double hardness) { this.hardness = hardness; }

    public Double getLightReflectivity() { return lightReflectivity; }
    public void setLightReflectivity(Double lightReflectivity) { this.lightReflectivity = lightReflectivity; }

    @Override
    public String toString() {
        return "Topaz{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", weightCarat=" + getWeightCarat() +
                ", pricePerCarat=" + getPricePerCarat() +
                ", transparency=" + getTransparency() +
                ", color='" + getColor() + '\'' +
                ", hardness=" + hardness +
                ", lightReflectivity=" + lightReflectivity +
                '}';
    }
}
