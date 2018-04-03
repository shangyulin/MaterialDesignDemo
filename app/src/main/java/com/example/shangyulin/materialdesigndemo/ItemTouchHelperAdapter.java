package com.example.shangyulin.materialdesigndemo;

/**
 * Created by shangyulin on 2018/4/3.
 */

public interface ItemTouchHelperAdapter {
    // 移动
    void onItemMove(int fromPosition, int toPosition);

    // 删除
    void onItemDismiss(int position);
}
