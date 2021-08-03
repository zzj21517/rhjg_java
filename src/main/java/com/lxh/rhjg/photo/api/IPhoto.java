package com.lxh.rhjg.photo.api;

import com.lxh.rhjg.circle.api.SMART_PHOTO;

import java.io.Serializable;
import java.util.List;

public interface IPhoto extends Serializable {
    public void deletePhoto(String shortcode);
}
