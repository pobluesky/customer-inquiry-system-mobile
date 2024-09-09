package com.example.customer_inquiry_system_mobile.domain.inquiry;

import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.CarLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.ColdRolledLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.HotRolledLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.LineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.ThickPlateLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.WireRodLineItemResponseDTO;

import com.google.gson.*;

import java.lang.reflect.Type;

public class LineItemDeserializer implements JsonDeserializer<LineItemResponseDTO> {

    private final String productType;

    public LineItemDeserializer(String productType) {
        this.productType = productType;
    }

    @Override
    public LineItemResponseDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // If productType is provided in the constructor, use it; otherwise, try to extract from JSON
        String inferredProductType = this.productType;

        if (inferredProductType == null) {
            throw new JsonParseException("productType is missing or null in JSON data.");
        }

        // Deserialize based on the inferred or provided productType
        switch (inferredProductType) {
            case "CAR":
                return context.deserialize(jsonObject, CarLineItemResponseDTO.class);
            case "COLD_ROLLED":
                return context.deserialize(jsonObject, ColdRolledLineItemResponseDTO.class);
            case "HOT_ROLLED":
                return context.deserialize(jsonObject, HotRolledLineItemResponseDTO.class);
            case "THICK_PLATE":
                return context.deserialize(jsonObject, ThickPlateLineItemResponseDTO.class);
            case "WIRE_ROD":
                return context.deserialize(jsonObject, WireRodLineItemResponseDTO.class);
            default:
                throw new JsonParseException("Unknown product type: " + inferredProductType);
        }
    }
}
