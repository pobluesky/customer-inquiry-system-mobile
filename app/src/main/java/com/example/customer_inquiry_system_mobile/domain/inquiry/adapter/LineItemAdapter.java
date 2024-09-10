package com.example.customer_inquiry_system_mobile.domain.inquiry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.CarLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.ColdRolledLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.HotRolledLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.LineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.ThickPlateLineItemResponseDTO;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.WireRodLineItemResponseDTO;

import java.util.List;

public class LineItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<LineItemResponseDTO> lineItems;

    private final String type;

    private static final int VIEW_TYPE_CAR = 0;

    private static final int VIEW_TYPE_COLDROLLED = 1;

    private static final int VIEW_TYPE_HOTROLLED = 2;

    private static final int VIEW_TYPE_THICKPLATE = 3;

    private static final int VIEW_TYPE_WIREROD = 4;

    public LineItemAdapter(List<LineItemResponseDTO> lineItems, String type) {
        this.lineItems = lineItems;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        switch (type) {
            case "CAR":
                return VIEW_TYPE_CAR;
            case "COLD_ROLLED":
                return VIEW_TYPE_COLDROLLED;
            case "HOT_ROLLED":
                return VIEW_TYPE_HOTROLLED;
            case "THICK_PLATE":
                return VIEW_TYPE_THICKPLATE;
            case "WIRE_ROD":
                return VIEW_TYPE_WIREROD;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_CAR:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_lineitem_car,
                        parent,
                        false
                );
                return new CarLineItemViewHolder(view);
            case VIEW_TYPE_COLDROLLED:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_lineitem_coldrolled,
                        parent,
                        false
                );

                return new ColdRolledLineItemViewHolder(view);
            case VIEW_TYPE_HOTROLLED:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_lineitem_hotrolled,
                        parent,
                        false
                );

                return new HotRolledLineItemViewHolder(view);
            case VIEW_TYPE_THICKPLATE:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_lineitem_thickplate,
                        parent,
                        false
                );

                return new ThickPlateLineItemViewHolder(view);
            case VIEW_TYPE_WIREROD:
                view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_lineitem_wirerod,
                        parent,
                        false
                );

                return new WireRodLineItemViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LineItemResponseDTO lineItem = lineItems.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_CAR:
                ((CarLineItemViewHolder) holder).bind((CarLineItemResponseDTO) lineItem);
                break;
            case VIEW_TYPE_COLDROLLED:
                ((ColdRolledLineItemViewHolder) holder).bind((ColdRolledLineItemResponseDTO) lineItem);
                break;
            case VIEW_TYPE_HOTROLLED:
                ((HotRolledLineItemViewHolder) holder).bind((HotRolledLineItemResponseDTO) lineItem);
                break;
            case VIEW_TYPE_THICKPLATE:
                ((ThickPlateLineItemViewHolder) holder).bind((ThickPlateLineItemResponseDTO) lineItem);
                break;
            case VIEW_TYPE_WIREROD:
                ((WireRodLineItemViewHolder) holder).bind((WireRodLineItemResponseDTO) lineItem);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lineItems.size();
    }

    public static class CarLineItemViewHolder extends RecyclerView.ViewHolder {
        TextView
                lineItemId,
                lab,
                kind,
                standardOrg,
                salesVehicleName,
                partName,
                ixPlate,
                thickness,
                width,
                quantity,
                expectedDeliveryDate,
                transportationDestination,
                edge,
                tolerance,
                annualCost;

        public CarLineItemViewHolder(@NonNull View itemView) {
            super(itemView);
            lineItemId = itemView.findViewById(R.id.lineItemId);
            lab = itemView.findViewById(R.id.lab);
            kind = itemView.findViewById(R.id.kind);
            standardOrg = itemView.findViewById(R.id.standardOrg);
            salesVehicleName = itemView.findViewById(R.id.salesVehicleName);
            partName = itemView.findViewById(R.id.partName);
            ixPlate = itemView.findViewById(R.id.ixPlate);
            thickness = itemView.findViewById(R.id.thickness);
            width = itemView.findViewById(R.id.width);
            quantity = itemView.findViewById(R.id.quantity);
            expectedDeliveryDate = itemView.findViewById(R.id.expectedDeliveryDate);
            transportationDestination = itemView.findViewById(R.id.transportationDestination);
            edge = itemView.findViewById(R.id.edge);
            tolerance = itemView.findViewById(R.id.tolerance);
            annualCost = itemView.findViewById(R.id.annualCost);
        }

        public void bind(CarLineItemResponseDTO lineItem) {
            lineItemId.setText(String.valueOf(lineItem.getLineItemId()));
            lab.setText(lineItem.getLab());
            kind.setText(lineItem.getKind());
            salesVehicleName.setText(lineItem.getSalesVehicleName());
            partName.setText(lineItem.getPartName());
            ixPlate.setText(lineItem.getIxPlate());
            thickness.setText(lineItem.getThickness());
            width.setText(lineItem.getWidth());
            quantity.setText(String.valueOf(lineItem.getQuantity()));
            expectedDeliveryDate.setText(lineItem.getExpectedDeliveryDate());
            transportationDestination.setText(lineItem.getTransportationDestination());
            edge.setText(lineItem.getOrderEdge());
            tolerance.setText(lineItem.getTolerance());
            annualCost.setText(lineItem.getAnnualCost());
        }
    }

    public static class ColdRolledLineItemViewHolder extends RecyclerView.ViewHolder {
        TextView
                lineItemId,
                kind,
                inqName,
                orderCategory,
                thickness,
                width,
                quantity,
                expectedDeadline,
                orderEdge,
                inDiameter,
                outDiameter,
                sleeveThickness,
                tensileStrength,
                elongationRatio,
                hardness;

        public ColdRolledLineItemViewHolder(@NonNull View itemView) {
            super(itemView);
            lineItemId = itemView.findViewById(R.id.lineItemId);
            inqName = itemView.findViewById(R.id.inqName);
            kind = itemView.findViewById(R.id.kind);
            orderCategory = itemView.findViewById(R.id.orderCategory);
            thickness = itemView.findViewById(R.id.thickness);
            width = itemView.findViewById(R.id.width);
            quantity = itemView.findViewById(R.id.quantity);
            expectedDeadline = itemView.findViewById(R.id.expectedDeadline);
            orderEdge = itemView.findViewById(R.id.orderEdge);
            inDiameter = itemView.findViewById(R.id.inDiameter);
            outDiameter = itemView.findViewById(R.id.outDiameter);
            sleeveThickness = itemView.findViewById(R.id.sleeveThickness);
            tensileStrength = itemView.findViewById(R.id.tensileStrength);
            elongationRatio = itemView.findViewById(R.id.elongationRatio);
            hardness = itemView.findViewById(R.id.hardness);
        }

        public void bind(ColdRolledLineItemResponseDTO lineItem) {
            lineItemId.setText(String.valueOf(lineItem.getLineItemId()));
            inqName.setText(String.valueOf(lineItem.getInqName()));
            kind.setText(String.valueOf(lineItem.getKind()));
            orderCategory.setText(String.valueOf(lineItem.getOrderCategory()));
            thickness.setText(String.valueOf(lineItem.getThickness()));
            width.setText(String.valueOf(lineItem.getWidth()));
            quantity.setText(String.valueOf(lineItem.getQuantity()));
            expectedDeadline.setText(String.valueOf(lineItem.getExpectedDeadline()));
            orderEdge.setText(String.valueOf(lineItem.getOrderEdge()));
            inDiameter.setText(String.valueOf(lineItem.getInDiameter()));
            outDiameter.setText(String.valueOf(lineItem.getOutDiameter()));
            sleeveThickness.setText(String.valueOf(lineItem.getSleeveThickness()));
            tensileStrength.setText(String.valueOf(lineItem.getTensileStrength()));
            elongationRatio.setText(String.valueOf(lineItem.getElongationRatio()));
            hardness.setText(String.valueOf(lineItem.getHardness()));
        }
    }

    public static class HotRolledLineItemViewHolder extends RecyclerView.ViewHolder {
        TextView
                lineItemId,
                kind,
                inqName,
                orderCategory,
                thickness,
                width,
                hardness,
                flatness,
                orderEdge,
                quantity,
                yieldingPoint,
                tensileStrength,
                elongationRatio,
                camber,
                annualCost;

        public HotRolledLineItemViewHolder(@NonNull View itemView) {
            super(itemView);
            lineItemId = itemView.findViewById(R.id.lineItemId);
            inqName = itemView.findViewById(R.id.inqName);
            kind = itemView.findViewById(R.id.kind);
            orderCategory = itemView.findViewById(R.id.orderCategory);
            thickness = itemView.findViewById(R.id.thickness);
            width = itemView.findViewById(R.id.width);
            quantity = itemView.findViewById(R.id.quantity);
            flatness = itemView.findViewById(R.id.flatness);
            orderEdge = itemView.findViewById(R.id.orderEdge);
            yieldingPoint = itemView.findViewById(R.id.yieldingPoint);
            camber = itemView.findViewById(R.id.camber);
            annualCost = itemView.findViewById(R.id.annualCost);
            tensileStrength = itemView.findViewById(R.id.tensileStrength);
            elongationRatio = itemView.findViewById(R.id.elongationRatio);
            hardness = itemView.findViewById(R.id.hardness);
        }

        public void bind(HotRolledLineItemResponseDTO lineItem) {
            lineItemId.setText(String.valueOf(lineItem.getLineItemId()));
            inqName.setText(String.valueOf(lineItem.getInqName()));
            kind.setText(String.valueOf(lineItem.getKind()));
            orderCategory.setText(String.valueOf(lineItem.getOrderCategory()));
            thickness.setText(String.valueOf(lineItem.getThickness()));
            width.setText(String.valueOf(lineItem.getWidth()));
            quantity.setText(String.valueOf(lineItem.getQuantity()));
            flatness.setText(String.valueOf(lineItem.getFlatness()));
            orderEdge.setText(String.valueOf(lineItem.getOrderEdge()));
            yieldingPoint.setText(String.valueOf(lineItem.getYieldingPoint()));
            camber.setText(String.valueOf(lineItem.getCamber()));
            annualCost.setText(String.valueOf(lineItem.getAnnualCost()));
            tensileStrength.setText(String.valueOf(lineItem.getTensileStrength()));
            elongationRatio.setText(String.valueOf(lineItem.getElongationRatio()));
            hardness.setText(String.valueOf(lineItem.getHardness()));
        }
    }

    public static class ThickPlateLineItemViewHolder extends RecyclerView.ViewHolder {
        TextView
                lineItemId,
                orderPurpose,
                orderInfo,
                ladleIngredient,
                productIngredient,
                seal,
                grainSizeAnalysis,
                show,
                extraShow,
                agingShow,
                curve,
                additionalRequests,
                hardness,
                dropWeightTest,
                ultrasonicTransducer;

        public ThickPlateLineItemViewHolder(@NonNull View itemView) {
            super(itemView);
            lineItemId = itemView.findViewById(R.id.lineItemId);
            orderPurpose = itemView.findViewById(R.id.orderPurpose);
            orderInfo = itemView.findViewById(R.id.orderInfo);
            ladleIngredient = itemView.findViewById(R.id.ladleIngredient);
            productIngredient = itemView.findViewById(R.id.productIngredient);
            seal = itemView.findViewById(R.id.seal);
            grainSizeAnalysis = itemView.findViewById(R.id.grainSizeAnalysis);
            show = itemView.findViewById(R.id.show);
            extraShow = itemView.findViewById(R.id.extraShow);
            agingShow = itemView.findViewById(R.id.agingShow);
            curve = itemView.findViewById(R.id.curve);
            additionalRequests = itemView.findViewById(R.id.additionalRequests);
            hardness = itemView.findViewById(R.id.hardness);
            dropWeightTest = itemView.findViewById(R.id.dropWeightTest);
            ultrasonicTransducer = itemView.findViewById(R.id.ultrasonicTransducer);
        }

        public void bind(ThickPlateLineItemResponseDTO lineItem) {
            lineItemId.setText(String.valueOf(lineItem.getLineItemId()));
            orderPurpose.setText(String.valueOf(lineItem.getOrderPurpose()));
            orderInfo.setText(String.valueOf(lineItem.getOrderInfo()));
            ladleIngredient.setText(String.valueOf(lineItem.getLadleIngredient()));
            productIngredient.setText(String.valueOf(lineItem.getProductIngredient()));
            seal.setText(String.valueOf(lineItem.getSeal()));
            grainSizeAnalysis.setText(String.valueOf(lineItem.getGrainSizeAnalysis()));
            show.setText(String.valueOf(lineItem.getShow()));
            extraShow.setText(String.valueOf(lineItem.getExtraShow()));
            agingShow.setText(String.valueOf(lineItem.getAgingShow()));
            curve.setText(String.valueOf(lineItem.getCurve()));
            additionalRequests.setText(String.valueOf(lineItem.getAdditionalRequests()));
            hardness.setText(String.valueOf(lineItem.getHardness()));
            dropWeightTest.setText(String.valueOf(lineItem.getDropWeightTest()));
            ultrasonicTransducer.setText(String.valueOf(lineItem.getUltrasonicTransducer()));
        }
    }

    public static class WireRodLineItemViewHolder extends RecyclerView.ViewHolder {
        TextView
                lineItemId,
                kind,
                inqName,
                orderCategory,
                diameter,
                quantity,
                expectedDeadline,
                initialQuantity,
                customerProcessing,
                finalUse,
                transportationDestination,
                annualCost,
                legalRegulatoryReview,
                legalRegulatoryReviewDetail,
                finalCustomer;

        public WireRodLineItemViewHolder(@NonNull View itemView) {
            super(itemView);
            lineItemId = itemView.findViewById(R.id.lineItemId);
            kind = itemView.findViewById(R.id.kind);
            inqName = itemView.findViewById(R.id.inqName);
            orderCategory = itemView.findViewById(R.id.orderCategory);
            diameter = itemView.findViewById(R.id.diameter);
            quantity = itemView.findViewById(R.id.quantity);
            expectedDeadline = itemView.findViewById(R.id.expectedDeadline);
            initialQuantity = itemView.findViewById(R.id.initialQuantity);
            customerProcessing = itemView.findViewById(R.id.customerProcessing);
            finalUse = itemView.findViewById(R.id.finalUse);
            transportationDestination = itemView.findViewById(R.id.transportationDestination);
            annualCost = itemView.findViewById(R.id.annualCost);
            legalRegulatoryReview = itemView.findViewById(R.id.legalRegulatoryReview);
            legalRegulatoryReviewDetail = itemView.findViewById(R.id.legalRegulatoryReviewDetail);
            finalCustomer = itemView.findViewById(R.id.finalCustomer);
        }

        public void bind(WireRodLineItemResponseDTO lineItem) {
            lineItemId.setText(String.valueOf(lineItem.getLineItemId()));
            kind.setText(String.valueOf(lineItem.getKind()));
            inqName.setText(String.valueOf(lineItem.getInqName()));
            orderCategory.setText(String.valueOf(lineItem.getOrderCategory()));
            diameter.setText(String.valueOf(lineItem.getDiameter()));
            quantity.setText(String.valueOf(lineItem.getQuantity()));
            expectedDeadline.setText(String.valueOf(lineItem.getExpectedDeadline()));
            initialQuantity.setText(String.valueOf(lineItem.getInitialQuantity()));
            customerProcessing.setText(String.valueOf(lineItem.getCustomerProcessing()));
            finalUse.setText(String.valueOf(lineItem.getFinalUse()));
            transportationDestination.setText(String.valueOf(lineItem.getTransportationDestination()));
            annualCost.setText(String.valueOf(lineItem.getAnnualCost()));
            legalRegulatoryReview.setText(String.valueOf(lineItem.getLegalRegulatoryReview()));
            legalRegulatoryReviewDetail.setText(String.valueOf(lineItem.getLegalRegulatoryReviewDetail()));
            finalCustomer.setText(String.valueOf(lineItem.getFinalCustomer()));
        }
    }
}
