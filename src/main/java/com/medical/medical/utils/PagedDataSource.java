package com.medical.medical.utils;

import com.medical.medical.models.dto.res.PatientResDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class PagedDataSource {
    private final ObservableList<PatientResDTO> allData;
    private final int pageSize;

    public PagedDataSource(ObservableList<PatientResDTO> data, int pageSize) {
        this.allData = data;
        this.pageSize = pageSize;
    }

    public ObservableList<PatientResDTO> getPage(int pageIndex) {
        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, allData.size());
        return FXCollections.observableArrayList(allData.subList(start, end));
    }

    public int getPageCount() {
        return (int) Math.ceil((double) allData.size() / pageSize);
    }
}
