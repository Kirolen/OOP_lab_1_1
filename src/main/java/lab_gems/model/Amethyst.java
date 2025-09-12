package lab_gems.model;

import jakarta.persistence.*;
import lab_gems.types.GemType;

@Entity
@Table(name = "amethyst")
public class Amethyst extends Gem {

    private Double clarity;
    private Double colorIntensity;

    public Amethyst() {}

    public Amethyst(String name, GemType type, Double weightCarat, Double pricePerCarat,
                    Double transparency, String color, Double clarity, Double colorIntensity) {
        super(name, type, weightCarat, pricePerCarat, transparency, color);
        this.clarity = clarity;
        this.colorIntensity = colorIntensity;
    }

    // getters/setters
    public Double getClarity() { return clarity; }
    public void setClarity(Double clarity) { this.clarity = clarity; }

    public Double getColorIntensity() { return colorIntensity; }
    public void setColorIntensity(Double colorIntensity) { this.colorIntensity = colorIntensity; }

    @Override
    public String toString() {
        return "Amethyst{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", weightCarat=" + getWeightCarat() +
                ", pricePerCarat=" + getPricePerCarat() +
                ", transparency=" + getTransparency() +
                ", color='" + getColor() + '\'' +
                ", clarity=" + clarity +
                ", colorIntensity=" + colorIntensity +
                '}';
    }
}
