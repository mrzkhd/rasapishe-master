package com.rasapishe.customer.objectmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FactorContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Factor> ITEMS = new ArrayList<Factor>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Factor> ITEM_MAP = new HashMap<String, Factor>();


    private static void addItem(Factor item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public static void makeFactors(){
        int id = 1;
        addItem(new Factor(id++ + "", "باشگاه سایه", "بدنسازی", 200, 1, Factor.PayStatus.UNPAID, "1395/11/30"));
        addItem(new Factor(id++ + "", "مهدکودک ستایش", "شهریه", 300, 12, Factor.PayStatus.UNPAID, "1395/12/05"));
        addItem(new Factor(id + "", "باشگاه سایه", "اروبیک", 150, 1, Factor.PayStatus.PAID, "1395/11/03"));
    }


}
