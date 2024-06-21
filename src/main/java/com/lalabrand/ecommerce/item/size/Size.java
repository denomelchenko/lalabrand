package com.lalabrand.ecommerce.item.size;

import com.lalabrand.ecommerce.item.enums.SizeType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SizeType sizeType;

    @Column(name = "value", nullable = false, length = 40)
    private String value;
}
