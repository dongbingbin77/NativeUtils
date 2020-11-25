/**
 * FileName: DataBindingDemoActivity
 * Author: dongbingbin
 * Date: 2020/11/7 21:37
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.dongbingbin.nativeutils.databinding;

import android.os.Bundle;
import android.view.View;

import com.dongbingbin.nativeutils.R;
import com.dongbingbin.nativeutils.fragments.CloseSelf;
import com.dongbingbin.nativeutils.fragments.PwdFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * @ClassName: DataBindingDemoActivity
 * @Description: java类作用描述
 * @Author: dongbingbin
 * @Date: 2020/11/7 21:37
 */
public class DataBindingDemoActivity extends AppCompatActivity implements CloseSelf {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_binding_demo_activity);

    }

    @Override
    public void onCloseSelf() {
        findViewById(R.id.content_layout).setVisibility(View.GONE);
    }

    public void addFragment(View view){
        PwdFragment fragment = new PwdFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_layout,fragment,"content_layout")
                .addToBackStack("content_layout")
                .commit();
        findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
    }
}