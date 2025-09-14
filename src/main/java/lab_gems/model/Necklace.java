package lab_gems.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "necklace")
public class Necklace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "necklace", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<NecklaceGem> gems;

    public Necklace() {}
    public Necklace(String name) { this.name = name; }

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<NecklaceGem> getGems() { return gems; }
    public void setGems(List<NecklaceGem> gems) { this.gems = gems; }
}
