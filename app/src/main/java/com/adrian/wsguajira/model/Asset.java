package com.adrian.wsguajira.model;

public class Asset {
    private int ID, AssetID;
    private String AssetPhoto;

    public Asset() {
    }

    public Asset(int ID, int assetID, String assetPhoto) {
        this.ID = ID;
        AssetID = assetID;
        AssetPhoto = assetPhoto;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAssetID() {
        return AssetID;
    }

    public void setAssetID(int assetID) {
        AssetID = assetID;
    }

    public String getAssetPhoto() {
        return AssetPhoto;
    }

    public void setAssetPhoto(String assetPhoto) {
        AssetPhoto = assetPhoto;
    }
}
