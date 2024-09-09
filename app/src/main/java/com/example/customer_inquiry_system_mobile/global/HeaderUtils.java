package com.example.customer_inquiry_system_mobile.global;

import com.example.customer_inquiry_system_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class HeaderUtils {

    public static List<Integer> getHeaderLayouts(String productType) {
        List<Integer> headerLayouts = new ArrayList<>();

        switch (productType) {
            case "CAR":
                headerLayouts.add(R.layout.header_lineitem_car);
                break;
            case "COLD_ROLLED":
                headerLayouts.add(R.layout.header_lineitem_coldrolled);
                break;
            case "HOT_ROLLED":
                headerLayouts.add(R.layout.header_lineitem_hotrolled);
                break;
            case "THICK_PLATE":
                headerLayouts.add(R.layout.header_lineitem_thickplate);
                break;
            case "WIRE_ROD":
                headerLayouts.add(R.layout.header_lineitem_wirerod);
                break;
            default:
                break;
        }

        return headerLayouts;
    }
}
