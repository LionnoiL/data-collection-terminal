package ua.gaponov.datacollectionterminal.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory implements Serializable {

    private long docId;
    private String date;
    private String comment;
    private List<InventoryItem> items = new ArrayList<>();
}
