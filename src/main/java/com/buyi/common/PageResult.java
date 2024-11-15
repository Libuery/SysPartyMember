package com.buyi.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    /**
     * 总记录数
     */
    private long total;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页数据条数
     */
    private int size;
    /**
     * 当前页的数据
     */
    private List data;

    @Serial
    private static final long serialVersionUID = 1L;

}
