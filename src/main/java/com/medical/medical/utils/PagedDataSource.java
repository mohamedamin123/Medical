package com.medical.medical.utils;

import com.medical.medical.models.dto.res.PatientResDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class PagedDataSource<T> {
    private final ObservableList<T> allData;
    @Getter
    private final int pageSize;

    public PagedDataSource(ObservableList<T> data, int pageSize) {
        this.allData = FXCollections.observableArrayList(data);
        this.pageSize = pageSize;
    }

    public ObservableList<T> getPage(int pageIndex) {
        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, allData.size());
        return FXCollections.observableArrayList(allData.subList(start, end));
    }

    public int getPageCount() {
        int size = allData.size();
        return (size + pageSize - 1) / pageSize; // This ensures correct page count
    }
}
