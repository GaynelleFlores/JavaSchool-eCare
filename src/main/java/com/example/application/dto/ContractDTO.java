package com.example.application.dto;

import com.example.application.models.ClientsEntity;
import com.example.application.models.PlansEntity;
import lombok.Data;

@Data
public class ContractDTO {
    private int id;

    private String phone_number;

    ClientsEntity client;

    PlansEntity plan;

    private boolean is_blocked;

    public boolean getIs_blocked() {
        return this.is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    private int client_id; //may be delete?
}
