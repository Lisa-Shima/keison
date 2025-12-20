package com.keison.reservation.modules.locations.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "villages")
public class Village {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cell_id")
    private CellEntity cell;

    public UUID getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CellEntity getCell() { return cell; }
    public void setCell(CellEntity cell) { this.cell = cell; }
}
