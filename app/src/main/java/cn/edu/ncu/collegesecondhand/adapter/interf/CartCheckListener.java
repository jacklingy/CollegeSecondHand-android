package cn.edu.ncu.collegesecondhand.adapter.interf;

import cn.edu.ncu.collegesecondhand.entity.CartBean;

/**
 * Created by ren lingyun on 2020/4/20 2:57
 */
public interface CartCheckListener {
    void isChecked(CartBean cartBean);
    void isNotChecked(CartBean cartBean);
}
