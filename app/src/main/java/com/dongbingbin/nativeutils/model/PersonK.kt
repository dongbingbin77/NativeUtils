package com.dongbingbin.nativeutils.model

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject
@Module
@InstallIn(ActivityComponent::class)
data class PersonK(
        var name:String,
        var age:Int
){
    @Inject constructor() : this("dongbingbin",123)
}