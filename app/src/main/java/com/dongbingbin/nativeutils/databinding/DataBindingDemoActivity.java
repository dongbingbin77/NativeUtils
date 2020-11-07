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

import com.dongbingbin.nativeutils.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * @ClassName: DataBindingDemoActivity
 * @Description: java类作用描述
 * @Author: dongbingbin
 * @Date: 2020/11/7 21:37
 */
public class DataBindingDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_binding_demo_activity);
    }
}