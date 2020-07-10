package com.zs.zs_jetpack.ui.main.mine

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.wanandroid.entity.IntegralEntity
import com.zs.zs_jetpack.http.ApiService
import com.zs.zs_jetpack.http.RetrofitManager
import kotlinx.coroutines.CoroutineScope

/**
 * des 我的
 * @date 2020/7/10
 * @author zs
 */
class MineRepo(coroutineScope: CoroutineScope, errorLiveData: MutableLiveData<ApiException>) :
    BaseRepository(coroutineScope, errorLiveData) {

    fun getInternal(internalLiveData : MutableLiveData<IntegralEntity>){
        launch(
            block = {
                RetrofitManager.getApiService(ApiService::class.java)
                    .getIntegral()
                    .data()
            },
            success = {
                internalLiveData.postValue(it)
            }
        )
    }
}