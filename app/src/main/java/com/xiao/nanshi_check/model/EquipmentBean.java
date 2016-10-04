package com.xiao.nanshi_check.model;

/**
 * Created by zzzzz on 16/8/5/0005.
 */
public class EquipmentBean {
    String equipmentIp;
    String equipmentName;

    public EquipmentBean() {

    }

    public EquipmentBean(String equipmentIp, String equipmentName) {
        this.equipmentIp = equipmentIp;
        this.equipmentName = equipmentName;
    }

    public String getEquipmentIp() {
        return equipmentIp;
    }

    public void setEquipmentIp(String equipmentIp) {
        this.equipmentIp = equipmentIp;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    @Override
    public String toString() {
        return "EquipmentBean{" +
                "equipmentIp='" + equipmentIp + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                '}';
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }


}
