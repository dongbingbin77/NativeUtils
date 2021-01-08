/**
 * FileName: RcAdapter
 * Author: dongbingbin
 * Date: 2020/10/27 00:21
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.dongbingbin.nativeutils;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName: RcAdapter
 * @Description: java类作用描述
 * @Author: dongbingbin
 * @Date: 2020/10/27 00:21
 */
class RcAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ClassLoader.getSystemClassLoader().loadClass(RcAdapter.class.getName())
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}