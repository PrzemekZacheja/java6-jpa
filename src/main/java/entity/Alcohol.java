package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Alcohol {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)  //powoduje generowanie klucza tak jak autoinkrementacja w mysql
    private long id;

    private String name;

    private BigDecimal voltage;

    private BigDecimal capacity;

    private String category;    //TODO trzeba zrobić enum

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getVoltage() {
        return voltage;
    }

    public void setVoltage(BigDecimal voltage) {
        this.voltage = voltage;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Alcohol{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", voltage=" + voltage +
                ", capacity=" + capacity +
                ", category='" + category + '\'' +
                '}';
    }
}
