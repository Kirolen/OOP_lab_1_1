package lab_gems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sapphire")
public class Sapphire {

    @Id
    private Integer gemId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "gem_id")
    private Gem gem;

    private Double hardness;
    private Double brilliance;

    public Sapphire() {}
    
    public Sapphire(Gem gem, Double hardness, Double brilliance) {
        this.gem = gem;
        this.hardness = hardness;
        this.brilliance = brilliance;
    }

    // getters/setters
    public Gem getGem() { return gem; }
    public void setGem(Gem gem) { this.gem = gem; }
    public Double getHardness() { return hardness; }
    public void setHardness(Double hardness) { this.hardness = hardness; }
    public Double getBrilliance() { return brilliance; }
    public void setBrilliance(Double brilliance) { this.brilliance = brilliance; }
}
