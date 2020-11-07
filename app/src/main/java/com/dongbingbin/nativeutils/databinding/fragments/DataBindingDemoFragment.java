/**
 * FileName: DataBindingDemoFragment
 * Author: dongbingbin
 * Date: 2020/11/7 21:12
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.dongbingbin.nativeutils.databinding.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongbingbin.nativeutils.R;
import com.dongbingbin.nativeutils.databinding.DatabindingDemoFragmentBinding;
import com.dongbingbin.nativeutils.model.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

/**
 * @ClassName: DataBindingDemoFragment
 * @Description: java类作用描述
 * @Author: dongbingbin
 * @Date: 2020/11/7 21:12
 */
public class DataBindingDemoFragment extends Fragment {
    private DatabindingDemoFragmentBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.databinding_demo_fragment,container,false);
        User user = new User();
        user.setName("linhaojian");
        user.setSex("male");
        binding.setUser(user);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}