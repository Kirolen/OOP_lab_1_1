package lab_gems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "necklace_gem")
public class NecklaceGem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "necklace_id", nullable = false)
    private Necklace necklace;

    @ManyToOne
    @JoinColumn(name = "gem_id", nullable = false)
    private Gem gem;

    private Integer quantity;

    public NecklaceGem() {}
    public NecklaceGem(Necklace necklace, Gem gem, Integer quantity) {
        this.necklace = necklace;
        this.gem = gem;
        this.quantity = quantity;
    }

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Necklace getNecklace() { return necklace; }
    public void setNecklace(Necklace necklace) { this.necklace = necklace; }
    public Gem getGem() { return gem; }
    public void setGem(Gem gem) { this.gem = gem; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
