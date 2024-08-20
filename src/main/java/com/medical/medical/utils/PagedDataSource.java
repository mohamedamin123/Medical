package com.medical.medical.utils;

import com.medical.medical.models.dto.res.PatientResDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class PagedDataSource {
    private final ObservableList<PatientResDTO> allData;
    @Getter
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
        int size = allData.size();
        return (size + pageSize - 1) / pageSize; // This ensures correct page count
    }
}
