package lab_gems.model;

import jakarta.persistence.*;
import lab_gems.types.GemType;
import lab_gems.types.OpalType;

@Entity
@Table(name = "opal")
public class Opal extends Gem {

    @Enumerated(EnumType.STRING)
    private OpalType opalType;

    private Double fireIntensity;

    public Opal() {}

    public Opal(String name, GemType type, Double weightCarat, Double pricePerCarat,
                Double transparency, String color, OpalType opalType, Double fireIntensity) {
        super(name, type, weightCarat, pricePerCarat, transparency, color); // припустимо, що опали дорогоцінні
        this.opalType = opalType;
        this.fireIntensity = fireIntensity;
    }

    // getters/setters
    public OpalType getOpalType() { return opalType; }
    public void setOpalType(OpalType opalType) { this.opalType = opalType; }

    public Double getFireIntensity() { return fireIntensity; }
    public void setFireIntensity(Double fireIntensity) { this.fireIntensity = fireIntensity; }

    @Override
    public String toString() {
        return "Opal{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", weightCarat=" + getWeightCarat() +
                ", pricePerCarat=" + getPricePerCarat() +
                ", transparency=" + getTransparency() +
                ", color='" + getColor() + '\'' +
                ", opalType=" + opalType +
                ", fireIntensity=" + fireIntensity +
                '}';
    }
}
