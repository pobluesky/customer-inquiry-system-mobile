package com.example.customer_inquiry_system_mobile.domain.inquiry.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customer_inquiry_system_mobile.R;
import com.example.customer_inquiry_system_mobile.domain.inquiry.dto.LineItemResponseDTO;

import java.util.List;

public class LineItemAdapter extends RecyclerView.Adapter<LineItemAdapter.LineItemViewHolder> {

    private List<LineItemResponseDTO> lineItems;

    public LineItemAdapter(List<LineItemResponseDTO> lineItems) {
        this.lineItems = lineItems;
    }

    @NonNull
    @Override
    public LineItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_lineitem_item, parent, false);
        return new LineItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LineItemViewHolder holder, int position) {
        LineItemResponseDTO lineItem = lineItems.get(position);

        holder.lineItemId.setText(String.valueOf(lineItem.getLineItemId()));
        holder.lab.setText(lineItem.getLab());
        holder.kind.setText(lineItem.getKind());
        holder.standardOrg.setText(lineItem.getStandardOrg());
        holder.salesVehicleName.setText(lineItem.getSalesVehicleName());
        holder.partName.setText(lineItem.getPartName());
        holder.ixPlate.setText(lineItem.getIxPlate());
        holder.thickness.setText(lineItem.getThickness());
        holder.width.setText(lineItem.getWidth());
        holder.quantity.setText(String.valueOf(lineItem.getQuantity()));
        holder.expectedDeliveryDate.setText(lineItem.getExpectedDeliveryDate());
        holder.transportationDestination.setText(lineItem.getTransportationDestination());
        holder.edge.setText(lineItem.getEdge());
        holder.tolerance.setText(lineItem.getTolerance());
        holder.annualCost.setText(lineItem.getAnnualCost());
    }

    @Override
    public int getItemCount() {
        return lineItems.size();
    }

    public static class LineItemViewHolder extends RecyclerView.ViewHolder {
        TextView lineItemId, lab, kind, standardOrg, salesVehicleName, partName, ixPlate,
                thickness, width, quantity, expectedDeliveryDate, transportationDestination,
                edge, tolerance, annualCost;

        public LineItemViewHolder(@NonNull View itemView) {
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
    }
}
