package ua.gaponov.datacollectionterminal.inventory;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem implements Serializable {

    private long itemId;
    private long docId;

    private String productName;
    private String code;
    private double quantity;
    private String price;
}
